package main;

import game.AIPlayer;
import gui.WindowManager;
import model.Field;
import model.Surround;
import config.ConfigManager;
import config.Settings;


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
        
        Surround.setGameField(field);
        AIPlayer.setGameField(field);     
        WindowManager manager = new WindowManager(field);
        manager.showWindow();
        
        ConfigManager.saveSettings();
    }     
}
