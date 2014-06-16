package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Field;
import model.PointState;

import org.eclipse.swt.graphics.Point;

public class AIPlayer
{
    public static Point nextRandomStep(Field field)
    {
        List<Point> emptyPoints = getAllEmptyPoints(field);
        
        Random rand = new Random(System.currentTimeMillis());
        int index =  rand.nextInt(emptyPoints.size());
        //TODO: add validation - list is not empty  
        return emptyPoints.get(index);
    }

    private static List<Point> getAllEmptyPoints(Field field)
    {
        List<Point> result = new ArrayList<Point>();
        for (int i = 0; i < field.getWidth(); i++)
        {
            for (int j = 0; j < field.getHeight(); j++)
            {
                if (field.getPointState(i, j) == PointState.EMPTY)
                {
                    result.add(new Point(i, j));
                }
            }
        }
        return result; 
    }
}
