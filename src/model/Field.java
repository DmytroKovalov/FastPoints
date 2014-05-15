package model;

public class Field
{
    private PointState[][] grid; 

    public Field(int width, int height)
    {
        this.grid = new PointState[width][height];
    }

    public PointState[][] getGrid()
    {
        return grid;
    }    
}
