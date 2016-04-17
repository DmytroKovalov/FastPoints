package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import model.GameField;
import model.PointState;
import model.Surround;

public class SurroundsFinder
{
    private final GameField field;
    
    private final SurroundBuilder builder;

    private PointState surroundType;

    private Set<Point> fillPoints = new HashSet<Point>();

    public SurroundsFinder(GameField field)
    {
        this.field = field;
        this.builder = new SurroundBuilder(field);
    }

    public List<Surround> findNewSurrounds(int x, int y)
    {
        List<Surround> result = new ArrayList<Surround>();
        surroundType = field.getPointState(x, y);

        List<Point> neighbours = Helper.getAllEnemyOrEmptyNeighbours(x, y);
        Set<Point> canSkip = new HashSet<Point>();
        for (Point neighbour : neighbours)
        {
            if (canSkip.contains(neighbour))
            {
                continue;
            }

            if (startFillingFromPoint(neighbour))
            {
                Surround surround = createSurround();
                if (surround != null)
                {
                    surround.setInnerPoints(fillPoints);
                    result.add(surround);
                }
            }
            updateSkipList(canSkip, neighbours);
            fillPoints.clear();
        }

        return result;
    }

    private void updateSkipList(Set<Point> canSkip, List<Point> neighbours)
    {
        for (Point neighbour : neighbours)
        {
            if (fillPoints.contains(neighbour))
            {
                canSkip.add(neighbour);
            }
        }
    }

    private Surround createSurround()
    {
        Set<Point> borderPoints = new HashSet<Point>();
        for (Point point : fillPoints)
        {
            borderPoints.addAll(getBorderPoints(point));
        }

        if (borderPoints.removeAll(field.getAllSurroundedPoints()))
        {
            return null;
        }
        
        return builder.createSurround(borderPoints, fillPoints.iterator().next());   
    }
   
    private List<Point> getBorderPoints(Point point)
    {
        List<Point> result = new ArrayList<Point>();

        addIfItIsBorder(result, Helper.getTop(point));
        addIfItIsBorder(result, Helper.getBottom(point));
        addIfItIsBorder(result, Helper.getLeft(point));
        addIfItIsBorder(result, Helper.getRight(point));

        return result;
    }

    private void addIfItIsBorder(List<Point> result, Point point)
    {
        if (!fillPoints.contains(point))
        {
            result.add(point);
        }
    }

    private boolean startFillingFromPoint(Point startPoint)
    {
        Queue<Point> pointsForVisit = new LinkedList<Point>();
        pointsForVisit.add(startPoint);

        boolean isClosed = !Helper.isOnBorder(startPoint);
        //TODO: add exit after many iterations(need for debug out of memory on pointsForVisit.addAll()) 
        while (isClosed && !pointsForVisit.isEmpty())
        {
            Point currentPoint = pointsForVisit.remove();
            fillPoints.add(currentPoint);

            List<Point> neighbours = getAllNotVisitedNeighbors(currentPoint);
            pointsForVisit.addAll(neighbours);

            for (Point neighbour : neighbours)
            {
                if (Helper.isOnBorder(neighbour))
                {
                    isClosed = false;
                    break;
                }
            }
        }
       
        return isClosed && isNoneEmpty();
    }

    private boolean isNoneEmpty()
    {
        boolean result = false;
        for (Point point : fillPoints)
        {
            PointState state = field.getPointState(point);
            if ((state != surroundType) && (state != PointState.EMPTY))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    private List<Point> getAllNotVisitedNeighbors(Point point)
    {
        List<Point> result = new LinkedList<Point>();

        addIfNeedVisit(result, Helper.getTop(point));
        addIfNeedVisit(result, Helper.getBottom(point));
        addIfNeedVisit(result, Helper.getLeft(point));
        addIfNeedVisit(result, Helper.getRight(point));

        return result;
    }

    private void addIfNeedVisit(List<Point> result, Point point)
    {
        if (point == null)
        {
            return;
        }

        PointState state = null;
        if (field.getAllSurroundedPoints().contains(point))
        {
            state = field.getRealColorMap().get(point);
        }
        if (state == null)
        {
            state = field.getPointState(point);
        }
        
        if (state != surroundType) // we can fill
        {
            if (!fillPoints.contains(point))
            {
                result.add(point);
            }
        }
    }
}
