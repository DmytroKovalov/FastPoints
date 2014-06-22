package model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.graphics.Point;

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

    public Surround(boolean isRed)
    {      
        this.isRed = isRed;
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
            int lenght = points.size() - 1;
            result &= areNeighbours(points.get(0), points.get(lenght));
            for (int i = 0; (i < lenght) && result; i++)
            {
                result &= areNeighbours(points.get(i), points.get(i + 1));
            }
        }
        return result;
    }

    private boolean areNeighbours(Point first, Point second)
    {
        int diffX = Math.abs(first.x - second.x);
        int diffY = Math.abs(first.y - second.y);

        return (diffX <= 1) && (diffY <= 1);
    }

    @Override
    public String toString()
    {
        return "Surround {points=" + points + ", isRed=" + isRed + "}";
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
