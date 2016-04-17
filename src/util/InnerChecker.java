package util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

/**
 * Helper class which checks: the checkPoint is inner for path or not
 * 
 * @author DKovalov
 */
class InnerChecker
{
    private Set<Point> fillPoints = new HashSet<Point>();

    private Set<Point> path = new HashSet<Point>();

    private boolean isClosed;

    public boolean check(Point checkPoint, List<Point> path)
    {
        this.path.clear();
        this.path.addAll(path);

        fillPoints.clear();

        Queue<Point> pointsForVisit = new LinkedList<Point>();
        pointsForVisit.add(checkPoint);
        isClosed = !Helper.isOnBorder(checkPoint);
        while (isClosed && !pointsForVisit.isEmpty())
        {
            Point currentPoint = pointsForVisit.remove();
            fillPoints.add(currentPoint);

            List<Point> neighbours = getAllNotVisitedNeighbors(currentPoint);
            pointsForVisit.addAll(neighbours);
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

    private void addIfNeedVisit(List<Point> result, Point point)
    {
        if (point == null)
        {
            return;
        }

        if (!fillPoints.contains(point) && !path.contains(point))
        {
            isClosed &= !Helper.isOnBorder(point);
            result.add(point);
        }
    }
}
