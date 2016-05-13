package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private Set<Point> allSurroundedPoints = new HashSet<Point>();
    
    private Set<Point> emptyPoints = new HashSet<Point>();
    
    private Map<Point, PointState> realColorMap = new HashMap<Point, PointState>();

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
        emptyPoints.clear();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                grid[i][j] = PointState.EMPTY;
                emptyPoints.add(new Point(i, j));
            }
        }
        surrounds.clear();
        allSurroundedPoints.clear();
        realColorMap.clear();        
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
        grid[i][j] = state;
        emptyPoints.remove(new Point(i, j));
    }

    public Collection<Surround> getAllSurrounds()
    {
        return surrounds;
    }
    
    public int addAllSurrounds(Collection<Surround> surrounds)
    {
        this.surrounds.addAll(surrounds);
        int numberOfInnerPoints = 0;
        for (Surround surround : surrounds)
        {
            Set<Point> innerPoints = surround.getInnerPoints();
            allSurroundedPoints.addAll(innerPoints);
            emptyPoints.removeAll(innerPoints);
            PointState surroundColor = surround.isRed() ? PointState.RED : PointState.BLUE;
            for (Point point : innerPoints)
            {
                if (getPointState(point) != PointState.EMPTY)
                {
                    realColorMap.put(point, surroundColor);
                    numberOfInnerPoints++;
                }
            }
        }       
        return numberOfInnerPoints;
    }

    public Set<Point> getAllSurroundedPoints()
    {
        return Collections.unmodifiableSet(allSurroundedPoints);
    }
    
    public Set<Point> getEmptyPoints()
    {
        return Collections.unmodifiableSet(emptyPoints);
    }
    
    public Map<Point, PointState> getRealColorMap()
    {
        return Collections.unmodifiableMap(realColorMap);
    }
}
