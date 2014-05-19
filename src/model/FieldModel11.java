package model;

public class FieldModel11
{
    private PointState[][] grid; 

    public FieldModel11(int width, int height)
    {
        this.grid = new PointState[width][height];
    }

    public PointState getPointState(int i, int j)
    {
        return grid[i][j];
    }
    
    public void setPointState(int i, int j, PointState pointState)
    {
        grid[i][j] = pointState;
    } 
}
