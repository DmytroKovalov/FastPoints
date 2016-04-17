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
        final int flag = -1;
        Point second = g.getAllNeighbours(start).iterator().next();

        Map<Point, VertexInfo> verticesInfo = createMap(g.getAllVertices());
        
        verticesInfo.get(start).value = flag;
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(second);
        while(!queue.isEmpty())
        {
            Point current = queue.poll();
            
            
            //A* - doesn't solve problem
        }
        Surround path = new Surround(field.getPointState(start));
        return path;
    }

    private Map<Point, VertexInfo> createMap(Set<Point> borderPoints)
    {
        Map<Point, VertexInfo> result = new HashMap<Point, VertexInfo>();
        for (Point point : borderPoints)
        {
            result.put(point, new VertexInfo());
        }
        return result;
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
    
    private static class VertexInfo
    {
        int value;

        Point prev;
    }
}
