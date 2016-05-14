package config;

import game.GameMode;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class ConfigManager
{    
    private final static Settings INSTANCE = new Settings();
    
    private final static String CONFIG_FILE_NAME = "config.ini";
    
    private ConfigManager()
    {
        
    }
    
    public static void loadSettings()
    {
        Wini ini;
        try
        {
            ini = new Wini(new File(CONFIG_FILE_NAME));
            INSTANCE.setFieldWidth(ini.get("field", "width", int.class));
            INSTANCE.setFieldHeight(ini.get("field", "height", int.class));
            INSTANCE.setGameMode(ini.get("game", "mod", GameMode.class));
        }
        catch (InvalidFileFormatException exp)
        {
            System.err.println(exp.getMessage());
            exp.printStackTrace(System.err);
            INSTANCE.setDefaultValues();
        }
        catch (IOException exp)
        {
            System.err.println(exp.getMessage());
            exp.printStackTrace(System.err);
            INSTANCE.setDefaultValues();
        }
        catch (IllegalArgumentException exp)
        {
            INSTANCE.setDefaultValues();
        }
    }
    
    public static void saveSettings()
    {
        Wini ini;
        try
        {
            ini = new Wini(new File(CONFIG_FILE_NAME));
            //ini.putComment("field", " width in [15, 60]; height in [15, 40] ");
            ini.put("field", "width", INSTANCE.getFieldWidth());
            ini.put("field", "height", INSTANCE.getFieldHeight());
            ini.put("game", "mod", INSTANCE.getGameMode());
            ini.putComment("game", " mods:" + GameMode.HUMAN_VS_HUMAN + ", " + GameMode.HUMAN_VS_AI + ", "
                    + GameMode.AI_VS_AI);
            
            ini.store();
        }
        catch (InvalidFileFormatException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace(System.err);
        }
        catch (IOException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace(System.err);
        }
    }
    
    public static Settings getSettings()
    {
        return INSTANCE;
    }
}
