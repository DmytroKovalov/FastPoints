package config;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class ConfigManager
{
    private final static Settings INSTANCE = new Settings();
    
    private final static String CONFIG_FILE_NAME = "config.ini";
    
    public static void loadSettings()
    {
        Wini ini;
        try
        {
            ini = new Wini(new File(CONFIG_FILE_NAME));
            INSTANCE.setFieldWidth(ini.get("field", "width", int.class));
            INSTANCE.setFieldHeight(ini.get("field", "height", int.class));
        }
        catch (InvalidFileFormatException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace();
        }
        catch (IOException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace();
        }
    }
    
    public static void saveSettings()
    {
        Wini ini;
        try
        {
            ini = new Wini(new File(CONFIG_FILE_NAME));
            ini.put("field", "width", INSTANCE.getFieldWidth());
            ini.put("field", "height", INSTANCE.getFieldHeight());
            ini.store();
        }
        catch (InvalidFileFormatException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace();
        }
        catch (IOException exp)
        {
            System.out.println(exp.getMessage());
            exp.printStackTrace();
        }
    }
    
    public static Settings getSettings()
    {
        return INSTANCE;
    }
}
