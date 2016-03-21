package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import model.Field;
import model.PointState;
import model.Surround;

import org.eclipse.swt.graphics.Point;

public class SurroundsFinder
{
    private final Field field;
    
    private PointState surroundType;
    
    private Set<Point> fillPoints = new HashSet<Point>();
    
    public SurroundsFinder(Field field)
    {
        this.field = field;
    }

    public List<Surround> findNewSurrounds(int x, int y)
    {
        List<Surround> result = new ArrayList<Surround>();
        surroundType = field.getPointState(x, y);

        List<Point> neighbours = Helper.getAllEnemyOrEmptyNeighbours(x, y);
        List<Point> canSkip = new ArrayList<Point>();        
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
                    result.add(surround);
                }
                updateSkipList(canSkip, neighbours);
                fillPoints.clear();
            }
        }

        return result;
    }

    private void updateSkipList(List<Point> canSkip, List<Point> neighbours)
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

        Point start = findStartPoint(borderPoints);
        

        
        //TODO: create new task for this bug: 
        //1)delete inner points
        //unimplemented - maybe we can use Convex Hull(http://en.wikipedia.org/wiki/Convex_hull_algorithms)
        
        
        //TODO: implement
        //2)
        
        Surround path = new Surround(field.getPointState(start.x, start.y));
        path.addPoint(start);
        Point current = start;
        Point end = start;
        
        int iteration = 0;
        final int maxIteration = field.getHeight()*field.getWidth();
        
        while (iteration < maxIteration)
        {
            iteration++;
            Set<Point> neighbors = getNeighborsFrom(current, borderPoints);
            neighbors.removeAll(path.getPoints());
            neighbors.remove(end);

            if (neighbors.isEmpty())
            {
                break;
            }

            Map<Integer, Point> distanceToPoint = new HashMap<Integer, Point>();
            for (Point point : neighbors)
            {
                Integer distance = calculateDistances(current, point);
                distanceToPoint.put(distance, point);
            }
            Integer min = Collections.min(distanceToPoint.keySet());
            end = current;
            current = distanceToPoint.get(min);
            path.addPoint(current);     
        }
        
        if(iteration >= maxIteration)
        {
            throw new IllegalArgumentException("Iteration limit  " + path);
        }
        
        if (!path.isCorrect())
        {            
            throw new IllegalArgumentException("Incorrect surround  " + path);
        }    
        
        return path;
    }

    private Integer calculateDistances(Point first, Point second)
    {
        return Math.abs(first.x - second.x) + Math.abs(first.y - second.y);
    }

    private Set<Point> getNeighborsFrom(Point point, Collection<Point> pointsForRetain)
    {
        Set<Point> resultPoints = new HashSet<Point>();
        resultPoints.addAll(Helper.getAllNeighbours(point));
        resultPoints.retainAll(pointsForRetain);
        return resultPoints;
    }

    private Point findStartPoint(Set<Point> borderPoints)
    {
        Point result = null;
        for (Point point : borderPoints)
        {
            List<Point> neighbours = getNeigboursOnBorder(point, borderPoints);
            if (neighbours.size() == 2)
            {
                result = point;
                break;
            }
        }
        
        if (result == null)
        {
            throw new IllegalArgumentException("Can't find start point in borderPoints" + borderPoints);
        }
        return result;        
    }

    private List<Point> getNeigboursOnBorder(Point mainPoint, Set<Point> borderPoints)
    {
        List<Point> result = new ArrayList<Point>();
        for (Point point : borderPoints)
        {
            if (Helper.areNeighbours(point, mainPoint))
            {
                result.add(point);
            }
        }        
        return result;
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
        //TODO: check for non empty surrounding
        Queue<Point> pointsForVisit = new LinkedList<Point>();
        pointsForVisit.add(startPoint);
        boolean isClosed = !isOnBorder(startPoint);
        while (isClosed && !pointsForVisit.isEmpty())
        {
            Point currentPoint = pointsForVisit.remove();
            fillPoints.add(currentPoint);

            List<Point> neighbours = getAllNotVisitedNeighbors(currentPoint);
            pointsForVisit.addAll(neighbours);

            for (Point neighbour : neighbours)
            {
                if (isOnBorder(neighbour))
                {
                    isClosed = false;
                    break;
                }
            }
        }
        
        if (!isClosed)
        {
            fillPoints.clear();
        }
        
        return isClosed;
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
    
    private void addIfNeedVisit(List<Point> result, Point neighbour)
    {
        if (neighbour == null)
        {
            return;
        }
        
        PointState neighbourState = field.getPointState(neighbour);
        if (neighbourState != surroundType) // we can fill
        {
            if (!fillPoints.contains(neighbour))
            {
                result.add(neighbour);
            }
        }
    }

    private boolean isOnBorder(Point point)
    {
        boolean result = point.x == 0;
        result |= point.x == field.getWidth() - 1;
        result |= point.y == 0;
        result |= point.y == field.getHeight() - 1;
        return result;
    }
    /*
    private static class PointComparator implements Comparator<Point>
    {
        @Override
        public int compare(Point firstPoint, Point secondPoint)
        {
            int result =  firstPoint.y - secondPoint.y;
            if (result == 0)
            {
                result = firstPoint.x - secondPoint.x;
            }
            return result;
        }       
    }*/      
}
