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
    
    Settings()
    {
        setDefaultValues();
    }
    
    void setDefaultValues()
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
        this.fieldWidth = getCorrectValue(fieldWidth, 15, 60);
    }

    private int getCorrectValue(int value, int min, int max)
    {
        if (value < min)
        {
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    public int getFieldHeight()
    {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight)
    {
        this.fieldHeight = getCorrectValue(fieldHeight, 15, 40);
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode)
    {
        if (gameMode != null)
        {
            this.gameMode = gameMode;
        }
        else
        {
            this.gameMode = GameMode.HUMAN_VS_HUMAN;
        }
    }
}
