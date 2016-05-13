package game.ai;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.swt.graphics.Point;

import model.GameField;

public class RandomAIPlayer
{
    private GameField field;

    private Random rand = new Random(System.currentTimeMillis());

    RandomAIPlayer(GameField field)
    {
        this.field = field;
    }

    Point nextStep()
    {    
        int index = rand.nextInt(field.getEmptyPoints().size());
        Iterator<Point> iter = field.getEmptyPoints().iterator();
        for (int i = 0; i < index; i++)
        {
            iter.next();
        }
        Point result = iter.next();
        return result;
    }  
}
