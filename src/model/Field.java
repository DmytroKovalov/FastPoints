package model;

public class Field
{
    private boolean isCurrentRed;
    
    private int width;
    
    private int height;
    
    private FieldModel fieldModel;
        
    public Field(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel = new FieldModel(width, height);
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
        return fieldModel.getPointState(i, j);
    }
        
    public void clear()
    {        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                fieldModel.setPointState(i, j, PointState.EMPTY);
            }
        }
    }
    
    public void changeIfNeed(int i, int j)
    {
        PointState state = fieldModel.getPointState(i, j);
        if (state == PointState.EMPTY)
        {
            fieldModel.setPointState(i, j, isCurrentRed ? PointState.RED : PointState.BLUE);
            isCurrentRed = !isCurrentRed;
        }
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel = new FieldModel(width, height);
        clear();
    }
}
