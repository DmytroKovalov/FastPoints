package model;

import java.util.LinkedList;
import java.util.List;

public class FieldModel
{
    private PointState[][] grid;
    
    private List<Surround> surrounds = new LinkedList<Surround>();

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
    
    public void addSurround(Surround surround)
    {
        surrounds.add(surround);       
    }    
    
    public List<Surround> getAllSurrounds()
    {
        return surrounds;
    }
}
