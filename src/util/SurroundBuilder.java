package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import model.GameField;
import model.Surround;

class SurroundBuilder
{
    private final GameField field;

    SurroundBuilder(GameField field)
    {
        this.field = field;
    }

    public Surround createSurround(Set<Point> borderPoints, Point checkPoint) 
    {      
        Graph g = Graph.build(borderPoints);

        Surround path = null;
        Set<Point> wrongSurrounds = new HashSet<Point>();
        InnerChecker innerChecker = new InnerChecker();
        do
        {
            Point start = findNextStartPoint(g, wrongSurrounds);
            path = buildPath(g, start);
            wrongSurrounds.addAll(path.getPoints());
        } while (!innerChecker.check(checkPoint, path.getPoints()));

        return path;
    }

    private Surround buildPath(Graph g, Point start)
    {
        Point second = g.getAllNeighbours(start).iterator().next();
        Map<Point, Point> pointToPrevious = new HashMap<Point, Point>();
        pointToPrevious.put(second, start);

        Queue<Point> queue = new LinkedList<Point>();
        for (Point point : g.getAllNeighbours(second))
        {
            if (!point.equals(start))
            {
                pointToPrevious.put(point, second);
                queue.add(point);
            }
        }
        
        boolean startFound = false;
        while (!queue.isEmpty() && !startFound)
        {
            Point current = queue.poll();
            for (Point point : g.getAllNeighbours(current))
            {
                Point prev = pointToPrevious.get(point);
                if (prev == null)
                {
                    pointToPrevious.put(point, current);
                    queue.add(point);
                    
                    if (point.equals(start))
                    {
                        startFound = true;
                    }
                }
            }    
        }
        
        Surround path = new Surround(field.getPointState(start));
        path.addPoint(start);
        
        if (startFound)
        {
            buildPath(pointToPrevious, start, path);
        }
        
        return path;
    }

    private void buildPath(Map<Point, Point> pointToPrevious, Point start,
            Surround path)
    {
        Point current = pointToPrevious.get(start);
        path.addPoint(current);
        while (true)
        {
            current = pointToPrevious.get(current);
            if (current.equals(start))
            {
                break;
            }
            path.addPoint(current);
        }
    }

    private Point findNextStartPoint(Graph g, Set<Point> excluded) 
    {
        Point result = null;
        for (Point vertex : g.getAllVertices())
        {
            if (excluded.contains(vertex))
            {
                continue;
            }
            int size = g.getAllNeighbours(vertex).size();
            if (size == 2)
            {
                Point[] arr = g.getAllNeighbours(vertex).toArray(new Point[size]);
                if (!Helper.areNeighbours(arr[0], arr[1]))
                {
                    result = vertex;
                    break;
                }
            }
        }

        if (result == null)
        {
            throw new IllegalArgumentException("Can't find start point in Graph");
        }
        return result;
    }
}
