package config;

/**
 * This class represent all settings of application.
 * 
 * @author DUKE
 */
public class Settings
{
    private int fieldWidth;
    
    private int fieldHeight;
    
    protected Settings()
    {
        setDefaultValues();
    }
    
    private void setDefaultValues()
    {
        fieldWidth = 15;
        fieldHeight = 10;    
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
}
