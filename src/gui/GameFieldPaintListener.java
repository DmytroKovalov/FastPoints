package gui;

import model.FieldModel;
import model.PointState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * This class is responsible for drawing of game field.
 * 
 * @author DKovalov
 */
public class GameFieldPaintListener implements PaintListener
{
    private Display display;
    
    private FieldModel model;
    
    private final static int STEP = 20;

    private final static int HALF_STEP = STEP / 2;

    public GameFieldPaintListener(Display display, FieldModel model)
    {
        this.display = display;
        this.model = model;
    }

    @Override
    public void paintControl(PaintEvent event)
    {
        Image bufferImage = new Image(display, event.width, event.height);
        GC bufferGC = new GC(bufferImage);

        drawGameField(bufferGC, event);
        event.gc.drawImage(bufferImage, 0, 0);

        bufferImage.dispose();
        event.gc.dispose();
    }

    private void drawGameField(GC gc, PaintEvent event)
    {
        drawGrid(gc, event.width, event.height);
        drawPointsOnGrid(gc);            
    }

    private void drawGrid(GC gc, int width, int height)
    {
        for (int x = HALF_STEP; x < width; x += STEP)
        {
            gc.drawLine(x, 0, x, height);
        }

        for (int y = HALF_STEP; y < height; y += STEP)
        {
            gc.drawLine(0, y, width, y);
        }
    }
    
    private void drawPointsOnGrid(GC gc)
    {
        int width = STEP*model.getWidth();
        int height= STEP*model.getHeight();
        Color color;
        for (int x = HALF_STEP; x < width; x += STEP)
        {
            for (int y = HALF_STEP; y < height; y += STEP)
            {
                int i = x / STEP;
                int j = y / STEP;
                PointState pointState =  model.getField().getGrid()[i][j];
                
                switch(pointState)
                {
                    case RED:
                        color = display.getSystemColor(SWT.COLOR_RED);
                        break;
                    case BLUE:
                        color = display.getSystemColor(SWT.COLOR_BLUE);
                        break;
                    case EMPTY:
                        continue;
                    default:  
                        throw new IllegalArgumentException("Expected type PointState");   
                }
                
                Point centre = new Point(x, y);
                drawPoint(gc, centre, color);
            }          
        }       
    }

    private void drawPoint(GC gc, Point centre, Color color)
    {
        final int radius = 5;
        final int diameter = 2 * radius;

        gc.setBackground(color);
        gc.fillOval(centre.x - radius, centre.y - radius, diameter, diameter);
    }
}
