package main;

import config.ConfigManager;
import config.Settings;
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
        ConfigManager.loadSettings();        
        
        Settings settings = ConfigManager.getSettings();
        Field field = new Field(settings.getFieldWidth(), settings.getFieldHeight());
               
        WindowManager manager = new WindowManager(field);
        manager.showWindow();
        
        ConfigManager.saveSettings();
    }     
}
