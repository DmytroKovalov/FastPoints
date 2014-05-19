package gui;

import model.Field11;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

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
    
    private GameFieldPaintListener painter;
    
    //TODO: remove this variable
    private WindowShellListener windowListner;

    /**
     * default constructor
     * @param field11 
     */
    public WindowManager(Field11 field11)
    {
        this.painter = new GameFieldPaintListener(display, field11);
        
        //TODO: use Seettings.fieldWidth instead magic number 20 
        int width = 20 * GameFieldPaintListener.STEP;
        int height = 20 * GameFieldPaintListener.STEP;
        this.windowListner = new WindowShellListener(width, height);
        
        initShell();
        initMenuBar();
    }

    /**
     * show the main window
     */
    public void showMainWindow()
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
        
        window.addPaintListener(painter);
        
        window.addShellListener(windowListner);   
        
        MouseListener mouseListener = new WindowMouseListener();
        window.addMouseListener(mouseListener);
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
            // New Game
            newGameItem = new MenuItem(gameMenu, SWT.PUSH);
            newGameItem.setText("New Game");
            // Exit
            exitItem = new MenuItem(gameMenu, SWT.PUSH);
            exitItem.setText("Exit");
        }

        // Help
        MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("Help");
        Menu helpMenu = new Menu(window, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        MenuItem getHelpItem, aboutItem;
        {
            // Get help
            getHelpItem = new MenuItem(helpMenu, SWT.PUSH);
            getHelpItem.setText("Get help");
            // About...
            aboutItem = new MenuItem(helpMenu, SWT.PUSH);
            aboutItem.setText("About...");
        }

        newGameItem.addSelectionListener(new NewGameItemListener());
        exitItem.addSelectionListener(new GameExitItemListener());

        getHelpItem.addSelectionListener(new GetHelpItemListener());
        aboutItem.addSelectionListener(new AboutItemListener());

        window.setMenuBar(menuBar);
    }

    private class NewGameItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {            
            //TODO: move into resize() method
            int width = 50 * GameFieldPaintListener.STEP;
            int height = 40 * GameFieldPaintListener.STEP;
            painter.getField().resize(50, 40);
            
            int frameX = window.getSize().x - window.getClientArea().width;
            int frameY = window.getSize().y - window.getClientArea().height;
            window.setSize(width + frameX, height + frameY);
                        
            painter.getField().clear();           
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

    private class AboutItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            MessageBox dialog = new MessageBox(window, SWT.ICON_INFORMATION | SWT.OK);
            dialog.setText("About...");
            dialog.setMessage("Game FastPoints " 
                    + "\n @author Dmytro Kovalov " 
                    + "\n @mail dmytro.kovalov.64@gmail.com "
                    + "\n @year 2014");
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
        private int width;
        
        private int height;

        public WindowShellListener(int width, int height)
        {
            this.width = width;   //TODO: use Seettings.fieldWidth  
            this.height = height; //TODO: use Seettings.fieldHeight      
        }

        @Override
        public void shellActivated(ShellEvent e)
        {
            int frameX = window.getSize().x - window.getClientArea().width;
            int frameY = window.getSize().y - window.getClientArea().height;
            
            //TODO: use Seettings.fieldWidth  
            //TODO: use Seettings.fieldHeight 
            window.setSize(width + frameX, height + frameY);        
        }

        @Override
        public void shellClosed(ShellEvent e)
        {
            System.out.println("Client Area: " + window.getClientArea());
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
            painter.getField().changeIfNeed(i, j);
            window.redraw();
        }

        @Override
        public void mouseUp(MouseEvent e)
        {
             // not supported       
        }
    }
}
