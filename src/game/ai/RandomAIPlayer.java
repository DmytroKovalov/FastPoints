package game.ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

import model.GameField;
import model.PointState;

public class RandomAIPlayer
{
    private GameField field;

    private Set<Point> emptyPoints = new HashSet<Point>();

    private Random rand = new Random(System.currentTimeMillis());

    RandomAIPlayer(GameField field)
    {
        this.field = field;
        initEmptyPointsSet();
    }

    Point nextStep(Point lastPoint)
    {
        emptyPoints.remove(lastPoint);

        if (emptyPoints.size() == 0)
        {
            return null;
        }
        int index = rand.nextInt(emptyPoints.size());
        Iterator<Point> iter = emptyPoints.iterator();
        for (int i = 0; i < index; i++)
        {
            iter.next();
        }
        Point result = iter.next();
        emptyPoints.remove(result);
        return result;
    }

    private void initEmptyPointsSet()
    {
        for (int i = 0; i < field.getWidth(); i++)
        {
            for (int j = 0; j < field.getHeight(); j++)
            {
                if (field.getPointState(i, j) == PointState.EMPTY)
                {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }
    }

    public void newGame()
    {
        emptyPoints.clear();
        initEmptyPointsSet();
    }
}
