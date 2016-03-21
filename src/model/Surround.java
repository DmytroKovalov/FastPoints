package model;

import java.util.LinkedList;
import java.util.List;

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

    private static Field field;

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
    
    public List<Point> getPoints()
    {
        return points;
    }

    private boolean isCorrect(Point point)
    {
        PointState pointState = field.getPointState(point.x, point.y);
        return pointState == (isRed ? PointState.RED : PointState.BLUE);
    }

    public boolean isCorrect()
    {
        boolean result = true;
        result &= points.size() >= 4;
        if (result)
        {
            int len = points.size() - 1;
            result &= Helper.areNeighbours(points.get(0), points.get(len));
            for (int i = 0; (i < len) && result; i++)
            {
                result &= Helper.areNeighbours(points.get(i), points.get(i + 1));
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
    
    public static void setGameField(Field field)
    {
        Surround.field = field;
    }
}