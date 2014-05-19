package main;

import model.Field;
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
        Field field = new Field(20, 20);
               
        WindowManager manager = new WindowManager(field);
        manager.showMainWindow();
    }
     
}
