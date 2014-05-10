package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * WindowManager is a manager for main window of application.
 * 
 * @author DUKE
 *
 */
public class WindowManager
{
    /**
     * global display
     */
    private final Display display = new Display();
    
    /**
     *  main window
     */
    private Shell window;
    
    
    /**
     * default constructor
     */
    public WindowManager()
    {
        initShell();
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
        //window.setMaximized(true); 
    }

}
