package gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * This class is responsible for drawing of game field.
 * 
 * @author DKovalov
 */
public class GameFieldPaintListener implements PaintListener
{
    private Display display;
    
    public GameFieldPaintListener(Display display)
    {
        this.display = display;
    }

    @Override
    public void paintControl(PaintEvent event)
    {
        Image bufferImage = new Image(display, event.width, event.height);
        GC bufferGC = new GC(bufferImage); 
        
        drawGameField(bufferGC, event);
        event.gc.drawImage(bufferImage,0,0);        
        
        bufferImage.dispose(); 
        event.gc.dispose();
    }

    private void drawGameField(GC gc, PaintEvent event)
    {
        drawGrid(gc, event.width, event.height);
    }
    
    private void drawGrid(GC gc, int width, int height)
    {
        final int step = 20;
        
        for (int x = step / 2; x < width; x += step)
        {
            gc.drawLine(x, 0, x, height);
        }

        for (int y = step / 2; y < height; y += step)
        {
            gc.drawLine(0, y, width, y);
        }    
    }
}
