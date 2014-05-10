package main;

import gui.WindowManager;

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
        WindowManager manager = new WindowManager();
        manager.showMainWindow();
    }
     
}
