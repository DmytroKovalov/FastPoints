package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

public class Graph
{
    private final Set<Point> vertices = new HashSet<Point>();

    private final Map<Point, Set<Point>> edges = new HashMap<Point, Set<Point>>();

    private Graph(Set<Point> vertices)
    {
        this.vertices.addAll(vertices);
        for (Point vertix : vertices)
        {
            edges.put(vertix, new HashSet<Point>());
        }
    }

    private void addEdge(Point start, Point end)
    {
        edges.get(start).add(end);
    }

    public static Graph build(Set<Point> vertices)
    {
        Graph g = new Graph(vertices);
        for (Point point : vertices)
        {
            Set<Point> neigbours = Helper.getAllNotEmptyNeighbours(point);
            neigbours.retainAll(vertices);
            for (Point vertex : neigbours)
            {
                g.addEdge(point, vertex);
            }
        }
        return g;
    }

    public int getVertexCount()
    {
        return vertices.size();
    }

    public Set<Point> getAllNeighbours(Point v)
    {
        return Collections.unmodifiableSet(edges.get(v));
    }

    public Set<Point> getAllVertices()
    {
        return Collections.unmodifiableSet(vertices);
    }
}
