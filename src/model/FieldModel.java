package model;

import java.util.Random;

public class FieldModel
{
    private int width;
    
    private int height;
    
    private Field field;
        
    public FieldModel(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.field = new Field(width, height);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Field getField()
    {
        return field;
    }
    
    public void initRandomly()
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Random rand = new Random();
                
                field.getGrid()[i][j] = PointState.getFromIntValue(rand.nextInt(3));
            }
        }      
    }
}
