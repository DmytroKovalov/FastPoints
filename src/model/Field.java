package model;

import java.util.Collection;
import java.util.List;

import org.eclipse.swt.graphics.Point;

import util.SurroundsFinder;

/**
 * 
 *  //TODO: rename this class to GameField 
 *  
 * @author DKovalov
 *
 */
public class Field
{
    private boolean isCurrentRed;
    
    private int width;
    
    private int height;
    
    private FieldModel fieldModel;
    
    private final SurroundsFinder finder;
        
    public Field(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel = new FieldModel(width, height);
        this.finder = new SurroundsFinder(this);
        clear();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public PointState getPointState(int i, int j)
    {
        return fieldModel.getPointState(i, j);
    }
    
    public PointState getPointState(Point point)
    {
        return fieldModel.getPointState(point.x, point.y);
    }
        
    public void clear()
    {        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                fieldModel.setPointState(i, j, PointState.EMPTY);
            }
        }
        isCurrentRed = false;
        fieldModel.getAllSurrounds().clear();
    }
    
    public boolean putPointIfWeCan(int i, int j)
    {
        boolean isChanged = false;
        PointState state = fieldModel.getPointState(i, j);
        if (state == PointState.EMPTY)
        {
            fieldModel.setPointState(i, j, isCurrentRed ? PointState.RED : PointState.BLUE);
            isCurrentRed = !isCurrentRed;
            isChanged = true;
            
            List<Surround> surrounds = finder.findNewSurrounds(i, j);
            if (!surrounds.isEmpty())
            {
                addSurrounds(surrounds);
            }    
            
        }
        return isChanged;
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.fieldModel = new FieldModel(width, height);
        clear();
    }

    public void putPoint(Point point)
    {
        putPointIfWeCan(point.x, point.y);    
    }
    
    public Collection<Surround> getAllSurrounds()
    {
        return fieldModel.getAllSurrounds();
    }
    
    private void addSurrounds(List<Surround> surrounds)
    {
        for (Surround surround : surrounds)
        {
            addSurround(surround);
        }
    }
    
    private void addSurround(Surround surround)
    {
        if (surround.isCorrect())
        {
            fieldModel.addSurround(surround);
        }
        else
        {
            throw new IllegalArgumentException("Incorrect surround  " + surround);
        }
    }


}
