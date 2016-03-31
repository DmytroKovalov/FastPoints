package model;

import java.util.Collection;
import java.util.HashSet;

//TODO: remove this class, fields "grid" and "surrounds" move to Field class 
public class FieldModel
{
    private PointState[][] grid;
    
    private Collection<Surround> surrounds = new HashSet<Surround>();

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
    
    public Collection<Surround> getAllSurrounds()
    {
        return surrounds;
    }
}
