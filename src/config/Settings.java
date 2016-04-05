package config;

import game.GameMode;

/**
 * This class represent all settings of application.
 * 
 * @author DUKE
 */
public class Settings
{
    private int fieldWidth;
    
    private int fieldHeight;
    
    private GameMode gameMode;
    
    protected Settings()
    {
        setDefaultValues();
    }
    
    private void setDefaultValues()
    {
        fieldWidth = 25;
        fieldHeight = 20;
        gameMode = GameMode.HUMAN_VS_HUMAN;
    }
    
    public int getFieldWidth()
    {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth)
    {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight()
    {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight)
    {
        this.fieldHeight = fieldHeight;
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }    
}
