package model;

public class FieldModel
{
    private PointState[][] grid; 

    public FieldModel(int width, int height)
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
