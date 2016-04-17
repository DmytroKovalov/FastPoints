package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import model.GameField;
import model.PointState;

/**
 * Helper class
 * 
 * @author DUKE
 * 
 */
public class Helper
{
    private static GameField field;

    private Helper()
    {

    }

    public static List<Point> getAllEnemyOrEmptyNeighbours(int x, int y)
    {
        return getAllEnemyOrEmptyNeighbours(new Point(x, y));
    }

    public static List<Point> getAllEnemyOrEmptyNeighbours(Point point)
    {
        PointState current = field.getPointState(point);
        List<Point> result = new ArrayList<Point>();

        add(result, current, getTop(point));
        add(result, current, getBottom(point));
        add(result, current, getLeft(point));
        add(result, current, getRight(point));

        add(result, current, getTopLeft(point));
        add(result, current, getTopRight(point));
        add(result, current, getBottomLeft(point));
        add(result, current, getBottomRight(point));

        return result;
    }

    public static List<Point> getAllNeighbours(Point point)
    {
        List<Point> result = new ArrayList<Point>();

        addIfNotNull(result, getTop(point));
        addIfNotNull(result, getBottom(point));
        addIfNotNull(result, getLeft(point));
        addIfNotNull(result, getRight(point));

        addIfNotNull(result, getTopLeft(point));
        addIfNotNull(result, getTopRight(point));
        addIfNotNull(result, getBottomLeft(point));
        addIfNotNull(result, getBottomRight(point));

        return result;
    }

    private static void addIfNotNull(List<Point> result, Point point)
    {
        if (point != null)
        {
            result.add(point);
        }
    }

    private static void add(List<Point> result, PointState state, Point point)
    {
        if (point != null)
        {
            PointState current = field.getPointState(point);
            if ((current != state) && !isInSomeSurround(point))
            {
                result.add(point);
            }
        }
    }

    public static boolean isInSomeSurround(Point point)
    {
        return field.getAllSurroundedPoints().contains(point);
    }

    public static Point getTop(Point point)
    {
        Point result = null;
        if (point.y != 0)
        {
            result = new Point(point.x, point.y - 1);
        }
        return result;
    }

    public static Point getBottom(Point point)
    {
        Point result = null;
        if (point.y != field.getHeight() - 1)
        {
            result = new Point(point.x, point.y + 1);
        }
        return result;
    }

    public static Point getLeft(Point point)
    {
        Point result = null;
        if (point.x != 0)
        {
            result = new Point(point.x - 1, point.y);
        }
        return result;
    }

    public static Point getRight(Point point)
    {
        Point result = null;
        if (point.x != field.getWidth() - 1)
        {
            result = new Point(point.x + 1, point.y);
        }
        return result;
    }

    public static Point getTopLeft(Point point)
    {
        Point result = null;
        if ((point.x != 0) && (point.y != 0))
        {
            result = new Point(point.x - 1, point.y - 1);
        }
        return result;
    }

    public static Point getTopRight(Point point)
    {
        Point result = null;
        if ((point.x != field.getWidth() - 1) && (point.y != 0))
        {
            result = new Point(point.x + 1, point.y - 1);
        }
        return result;
    }

    public static Point getBottomLeft(Point point)
    {
        Point result = null;
        if ((point.x != 0) && (point.y != field.getHeight() - 1))
        {
            result = new Point(point.x - 1, point.y + 1);
        }
        return result;
    }

    public static Point getBottomRight(Point point)
    {
        Point result = null;
        if ((point.x != field.getWidth() - 1) && (point.y != field.getHeight() - 1))
        {
            result = new Point(point.x + 1, point.y + 1);
        }
        return result;
    }

    public static void setGameField(GameField field)
    {
        Helper.field = field;
    }

    public static boolean areNeighbours(Point first, Point second)
    {
        if (first.equals(second))
        {
            return false;
        }

        int diffX = Math.abs(first.x - second.x);
        int diffY = Math.abs(first.y - second.y);

        return (diffX <= 1) && (diffY <= 1);
    }

    public static Set<Point> getAllNotEmptyNeighbours(Point point)
    {
        Set<Point> result = new HashSet<Point>();

        addIfNotNullAndNotEmpty(result, getTop(point));
        addIfNotNullAndNotEmpty(result, getBottom(point));
        addIfNotNullAndNotEmpty(result, getLeft(point));
        addIfNotNullAndNotEmpty(result, getRight(point));

        addIfNotNullAndNotEmpty(result, getTopLeft(point));
        addIfNotNullAndNotEmpty(result, getTopRight(point));
        addIfNotNullAndNotEmpty(result, getBottomLeft(point));
        addIfNotNullAndNotEmpty(result, getBottomRight(point));

        return result;
        
    }

    private static void addIfNotNullAndNotEmpty(Set<Point> result, Point point)
    {
        if (point != null)
        {
            PointState current = field.getPointState(point);
            if (current != PointState.EMPTY)
            {
                result.add(point);
            }
        }      
    }
    
    public static boolean isOnBorder(Point point)
    {
        boolean result = point.x == 0;
        result |= point.x == field.getWidth() - 1;
        result |= point.y == 0;
        result |= point.y == field.getHeight() - 1;
        return result;
    }
}
