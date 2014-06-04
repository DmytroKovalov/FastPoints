package gui;

import model.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import config.ConfigManager;
import config.Settings;

/**
 * WindowManager is a manager for main window of application.
 * 
 * @author DKovalov
 * 
 */
public class WindowManager
{
    /**
     * global display
     */
    private final Display display = new Display();

    /**
     * main window
     */
    private Shell window;
    
    private final Field field;
    
    /**
     * default constructor
     * @param field 
     */
    public WindowManager(Field field)
    {
        this.field = field;
        
        initShell();
        initMenuBar();
    }

    /**
     * show the main window
     */
    public void showWindow()
    {
        window.open();
        while (!window.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void initShell()
    {
        window = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        window.setText("FastPoints");
        
        window.addPaintListener(new GameFieldPaintListener(display, field));        
        window.addShellListener(new WindowShellListener());           
        window.addMouseListener(new WindowMouseListener());
    }

    private void initMenuBar()
    {
        Menu menuBar = new Menu(window, SWT.BAR);

        // Game
        MenuItem gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        gameMenuHeader.setText("Game");
        Menu gameMenu = new Menu(window, SWT.DROP_DOWN);
        gameMenuHeader.setMenu(gameMenu);

        MenuItem newGameItem, exitItem;
        {
            // -New Game
            newGameItem = new MenuItem(gameMenu, SWT.PUSH);
            newGameItem.setText("New Game");
            // -Exit
            exitItem = new MenuItem(gameMenu, SWT.PUSH);
            exitItem.setText("Exit");
        }
        
        // Settings
        MenuItem gameSettingsHeader = new MenuItem(menuBar, SWT.CASCADE);
        gameSettingsHeader.setText("Settings");
        Menu settingsMenu = new Menu(window, SWT.DROP_DOWN);
        gameSettingsHeader.setMenu(settingsMenu);

        MenuItem propertiesItem;
        {
            // -Properties
            propertiesItem = new MenuItem(settingsMenu, SWT.PUSH);
            propertiesItem.setText("Properties");
        }       

        // Help
        MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("Help");
        Menu helpMenu = new Menu(window, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        MenuItem getHelpItem, aboutItem;
        {
            // -Get help
            getHelpItem = new MenuItem(helpMenu, SWT.PUSH);
            getHelpItem.setText("Get help");
            // -About...
            aboutItem = new MenuItem(helpMenu, SWT.PUSH);
            aboutItem.setText("About...");
        }

        newGameItem.addSelectionListener(new NewGameItemListener());
        exitItem.addSelectionListener(new GameExitItemListener());
        
        propertiesItem.addSelectionListener(new PropertiesItemListener());

        getHelpItem.addSelectionListener(new GetHelpItemListener());
        aboutItem.addSelectionListener(new AboutItemListener());

        window.setMenuBar(menuBar);
    }

    private class NewGameItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {            
            field.clear();
            window.redraw();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }

    private class GameExitItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            window.close();
            display.dispose();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }

    private class GetHelpItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            MessageBox dialog = new MessageBox(window, SWT.ICON_INFORMATION | SWT.OK);
            dialog.setText("Help");
            dialog.setMessage("TODO: write help");
            dialog.open();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }
    
    private class PropertiesItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            SettingsWindow settingsWindow = new SettingsWindow(display);
            if (settingsWindow.showWindow(getShiftedLocation()))
            {
                Settings settings = ConfigManager.getSettings();
                field.resize(settings.getFieldWidth(), settings.getFieldHeight());
                window.redraw();
            }
        }

        private Point getShiftedLocation()
        {
            Point location = window.getLocation();
            final int shift = 30;
            location.x += shift;
            location.y += shift;
            return location;
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }
    
    private class AboutItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            MessageBox dialog = new MessageBox(window, SWT.ICON_INFORMATION | SWT.OK);
            dialog.setText("About...");
            dialog.setMessage("Game FastPoints " 
                    + "\n @author: Dmytro Kovalov " 
                    + "\n @mail: dmytro.kovalov.64@gmail.com "
                    + "\n @year: 2014");
            dialog.open();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }
   
    private class WindowShellListener implements ShellListener
    {
        @Override
        public void shellActivated(ShellEvent e)
        {
            int frameX = window.getSize().x - window.getClientArea().width;
            int frameY = window.getSize().y - window.getClientArea().height;
            
            Settings settings = ConfigManager.getSettings();
            int width = settings.getFieldWidth() * GameFieldPaintListener.STEP;
            int height = settings.getFieldHeight() * GameFieldPaintListener.STEP;
            
            window.setSize(width + frameX, height + frameY);        
        }

        @Override
        public void shellClosed(ShellEvent e)
        {
            // not supported
        }

        @Override
        public void shellDeactivated(ShellEvent e)
        {
            // not supported
        }

        @Override
        public void shellDeiconified(ShellEvent e)
        {
            // not supported
        }

        @Override
        public void shellIconified(ShellEvent e)
        {
            // not supported
        }
    }
    
    private class WindowMouseListener implements MouseListener 
    {
        @Override
        public void mouseDoubleClick(MouseEvent e)
        {
             // not supported           
        }

        @Override
        public void mouseDown(MouseEvent e)
        {
            int i = e.x/GameFieldPaintListener.STEP;
            int j = e.y/GameFieldPaintListener.STEP;
            field.changeIfNeed(i, j);
            window.redraw();
        }

        @Override
        public void mouseUp(MouseEvent e)
        {
             // not supported       
        }
    }
}
