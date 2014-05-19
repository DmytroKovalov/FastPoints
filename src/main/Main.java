package main;

import model.Field11;
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
        Field11 field11 = new Field11(20, 20);
               
        WindowManager manager = new WindowManager(field11);
        manager.showMainWindow();
    }
     
}
