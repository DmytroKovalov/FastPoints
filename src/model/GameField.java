package model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.graphics.Point;

/**
 * 
 * 
 * 
 * @author DKovalov
 *
 */
public class GameField
{
    private int width;

    private int height;

    private PointState[][] grid;

    private Collection<Surround> surrounds = new ArrayList<Surround>();

    public GameField(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.grid = new PointState[width][height];
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
        return grid[i][j];
    }

    public PointState getPointState(Point point)
    {
        return getPointState(point.x, point.y);
    }

    public void clear()
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                grid[i][j] = PointState.EMPTY;
            }
        }
        surrounds.clear();
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.grid = new PointState[width][height];
        clear();
    }

    public void setPoint(int i, int j, PointState state)
    {
        grid[i][j]=state;
    }

    public Collection<Surround> getAllSurrounds()
    {
        return surrounds;
    }
    
    public void addAllSurrounds(Collection<Surround> surrounds)
    {
        this.surrounds.addAll(surrounds);
    }
}
