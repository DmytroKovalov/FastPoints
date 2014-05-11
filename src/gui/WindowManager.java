package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

    /**
     * default constructor
     */
    public WindowManager()
    {
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
        window = new Shell(display);

        window.setText("FastPoints");
        window.setSize(1200, 900);
        // window.setMaximized(true);
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

        newGameItem.addSelectionListener(new newGameItemListener());
        exitItem.addSelectionListener(new gameExitItemListener());

        getHelpItem.addSelectionListener(new getHelpItemListener());
        aboutItem.addSelectionListener(new aboutItemListener());

        window.setMenuBar(menuBar);
    }

    class newGameItemListener implements SelectionListener
    {
        public void widgetSelected(SelectionEvent event)
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {
            // not supported
        }
    }

    class gameExitItemListener implements SelectionListener
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

    class getHelpItemListener implements SelectionListener
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

    class aboutItemListener implements SelectionListener
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
}
