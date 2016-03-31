package gui.listeners;

import game.GameMode;
import game.AIPlayer;
import model.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import config.ConfigManager;

public class WindowMouseListener implements MouseListener 
{
    private final Shell window;
    
    private final Field field;
    
    public final int step;
    
    public WindowMouseListener(Shell window, Field field, int step)
    {
        this.window = window;
        this.field = field;
        this.step = step;
    }
    
    @Override
    public void mouseDoubleClick(MouseEvent e)
    {
         // not supported           
    }

    @Override
    public void mouseDown(MouseEvent e)
    {   
        
        int i = e.x / step;
        int j = e.y / step;
        //TODO: create class game.Game and move this implementation to method game.Game.nextStep()[or doStep()]
        GameMode mod = ConfigManager.getSettings().getGameMode();       
        switch (mod)
        {         
            case AI_VS_AI:              
                //TODO:add supporting of this mode and remove this message
                MessageBox dialog = new MessageBox(window, SWT.ICON_INFORMATION | SWT.OK);
                dialog.setText("Information...");
                dialog.setMessage("This fanctionality is not implemented yet.");
                dialog.open();
                break;           
            case HUMAN_VS_AI:
                humanAndAIStep(i, j);
                break;
            case HUMAN_VS_HUMAN:
                humanStep(i, j);
                break;
            default:
                throw new IllegalArgumentException("Unexpectable mode: " + mod);
        }
    }

    //TODO: create class game.Game and move this method to it
    private void humanAndAIStep(int i, int j)
    {
        if ((i < field.getWidth()) && (j < field.getHeight()))
        {
            if (field.putPointIfWeCan(i, j))
            {
                window.redraw();
                Point point = AIPlayer.nextStep();
                field.putPoint(point);
                window.redraw();
            }
        }        
    }

    //TODO: create class game.Game and move this method to it
    private void humanStep(int i, int j)
    {
        if ((i < field.getWidth()) && (j < field.getHeight()))
        {
            if (field.putPointIfWeCan(i, j))
            {
                window.redraw();
            }
        }           
    }

    @Override
    public void mouseUp(MouseEvent e)
    {
         // not supported       
    }
}
