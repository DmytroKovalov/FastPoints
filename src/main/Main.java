package main;

import config.ConfigManager;
import config.Settings;
import game.Game;
import game.ai.AIPlayer;
import model.GameField;
import model.Surround;
import util.Helper;


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
        GameField field = new GameField(settings.getFieldWidth(), settings.getFieldHeight());
        
        Surround.setGameField(field);
        AIPlayer.setGameField(field);     
        Helper.setGameField(field);
        
        Game game = new Game(field);
        game.start();        
        
        ConfigManager.saveSettings();
    }     
}
