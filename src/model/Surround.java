package model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import util.Helper;

/**
 * This class represent surrounding of points.
 * 
 * @author DKovalov
 * 
 */
public class Surround
{
    private final List<Point> points = new LinkedList<Point>();

    private final boolean isRed;
    
    private final Set<Point> innerPoints = new HashSet<Point>();

    private static GameField field;

    public Surround(PointState pointState)
    {
        assert (pointState != PointState.EMPTY);
        this.isRed = pointState == PointState.RED ? true : false;
    }
    
    public void addPoint(Point point)
    {
        if (isCorrect(point))
        {
            points.add(point);
        }
        else
        {
            PointState pointState = field.getPointState(point.x, point.y);
            throw new IllegalArgumentException("Try to insert " + pointState + " point into "
                    + (isRed ? PointState.RED : PointState.BLUE) + " list. " + point);
        }
    }
    
    public void setInnerPoints(Set<Point> points)
    {
        innerPoints.clear();
        innerPoints.addAll(points);
    }
    
    public Collection<Point> getInnerPoints()
    {
        return Collections.unmodifiableCollection(innerPoints);
    }
     
    public Collection<Point> getPoints()
    {
        return Collections.unmodifiableCollection(points);
    }

    private boolean isCorrect(Point point)
    {
        PointState pointState = field.getPointState(point.x, point.y);
        return pointState == (isRed ? PointState.RED : PointState.BLUE);
    }

    public boolean isCorrect()
    {
        boolean result = points.size() >= 4;
        if (result)
        {
            int len = points.size() - 1;
            result = Helper.areNeighbours(points.get(0), points.get(len));
            for (int i = 0; result && (i < len); i++)
            {
                result = Helper.areNeighbours(points.get(i), points.get(i + 1));
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        return "Surround {isRed=" + isRed + ", points=" + points + "}";
    }
    
    public boolean isRed()
    {
        return isRed;
    }
    
    public static void setGameField(GameField field)
    {
        Surround.field = field;
    }
}
