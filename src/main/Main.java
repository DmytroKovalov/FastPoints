package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The main class of application. 
 * 
 * @author DKovalov
 *
 */
public class Main
{

    /**
     * The main method of application.
     * 
     * @param args
     *            arguments from console
     */
    public static void main(String[] args)
    {
        Main m = new Main();
        m.showFirstSWTWindow();
    }
    
    private void showFirstSWTWindow()
    {
        Display display = new Display();

        Shell shell = new Shell(display);
        shell.setText("SWT Hello");
        shell.setSize(600, 500);
        shell.open();

        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }

        display.dispose();
        
    }
}
