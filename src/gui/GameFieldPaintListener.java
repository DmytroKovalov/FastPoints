package gui;

import java.util.List;

import model.Field;
import model.PointState;
import model.Surround;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * This class responsible for drawing of game field.
 * 
 * @author DKovalov
 */
//TODO: move to package gui.listeners
class GameFieldPaintListener implements PaintListener
{
    private Display display;

    private Field field;

    private GC gc;
    
    private final Color red;
    
    private final Color blue;
    
    private final int POINT_RADIUS = 5;
    
    private final int POINT_DIAMETER = 2 * POINT_RADIUS;
    
    public final static int STEP = 20;

    public final static int HALF_STEP = STEP / 2;

    public GameFieldPaintListener(Display display, Field field)
    {
        this.display = display;
        this.field = field;
        this.red = display.getSystemColor(SWT.COLOR_RED);
        this.blue = display.getSystemColor(SWT.COLOR_BLUE);
        
    }

    public Field getField()
    {
        return field;
    }

    @Override
    public void paintControl(PaintEvent event)
    {
        // double buffering
        Image bufferImage = new Image(display, event.width, event.height);
        gc = new GC(bufferImage);

        drawGameField(event);
        event.gc.drawImage(bufferImage, 0, 0);

        bufferImage.dispose();
        event.gc.dispose();
    }

    private void drawGameField(PaintEvent event)
    {
        drawGrid(event.width, event.height);
        drawPointsOnGrid();
        drawSurrounds();
    }

    private void drawGrid(int width, int height)
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

    private void drawPointsOnGrid()
    {
        int width = STEP * field.getWidth();
        int height = STEP * field.getHeight();
        Color color;
        // TODO: walk on i,j and calculate x,y only if they need
        for (int x = HALF_STEP; x < width; x += STEP)
        {
            for (int y = HALF_STEP; y < height; y += STEP)
            {
                int i = x / STEP;
                int j = y / STEP;
                //TODO: add methods getBluePoints and getRedPoints into Field class 
                PointState pointState = field.getPointState(i, j);

                switch (pointState)
                {
                    case RED:
                        color = red;
                        break;
                    case BLUE:
                        color = blue;
                        break;
                    case EMPTY:
                        continue;
                    default:
                        throw new IllegalArgumentException("Expected type PointState");
                }

                Point centre = new Point(x, y);
                drawPoint(centre, color);
            }
        }
    }

    private void drawPoint(Point centre, Color color)
    {
        gc.setBackground(color);
        gc.fillOval(centre.x - POINT_RADIUS, centre.y - POINT_RADIUS, POINT_DIAMETER, POINT_DIAMETER);
    }

    private void drawSurrounds()
    {
        gc.setLineWidth(2);
        for (Surround surround : field.getAllSurrounds())
        {
            Color color = surround.isRed() ? red : blue;
            gc.setForeground(color);
            int[] points = getPointsLikeArray(surround.getPoints());
            gc.drawPolygon(points);
        }
    }

    //TODO:rename or add comment
    private int[] getPointsLikeArray(List<Point> points)
    {
        int[] result = new int[points.size() * 2];
        int index = 0;
        for (Point point : points)
        {
            result[index] = HALF_STEP + STEP * point.x;
            result[index + 1] = HALF_STEP + STEP * point.y;
            index += 2;
        }
        return result;
    }
}
