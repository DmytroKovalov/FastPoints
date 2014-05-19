package model;

public class Field11
{
    private boolean isCurrentRed;
    
    private int width;
    
    private int height;
    
    private FieldModel11 fieldModel11;
        
    public Field11(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel11 = new FieldModel11(width, height);
        clear();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public PointState getPointState(int i, int j)
    {
        return fieldModel11.getPointState(i, j);
    }
        
    public void clear()
    {        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                fieldModel11.setPointState(i, j, PointState.EMPTY);
            }
        }
    }
    
    public void changeIfNeed(int i, int j)
    {
        PointState state = fieldModel11.getPointState(i, j);
        if (state == PointState.EMPTY)
        {
            fieldModel11.setPointState(i, j, isCurrentRed ? PointState.RED : PointState.BLUE);
            isCurrentRed = !isCurrentRed;
        }
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel11 = new FieldModel11(width, height);
        clear();
    }
}
