package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import game.GameState;

/**
 * describe me
 * 
 * @author DKovalov
 *
 */
public class StatusLine extends StyledText
{    
    private final Color blueColor;
    
    private final Color redColor;
    
    private final Color greenColor;
     
    public StatusLine(Composite parent, Display display, int style)
    {
        super(parent, style);
        blueColor = display.getSystemColor(SWT.COLOR_BLUE);
        redColor = display.getSystemColor(SWT.COLOR_RED);
        greenColor = display.getSystemColor(SWT.COLOR_GREEN);
        setEditable(false);
        setEnabled(false);
    }

    public void utadeStatusLine(GameState state)
    {
        switch(state)
        {
            case BLUE_WIN:
                setText(" Blue win!!!", blueColor);
                break;
            case GAME_DRAW:
                setText(" Game draw!", greenColor);
                break;
            case IN_PROGRESS:
                setScore(GameState.red, GameState.blue);
                break;
            case RED_WIN:
                setText(" Red win!!!", redColor);
                break;
            default:
                throw new IllegalArgumentException("Wrong game state");            
        }   
    }
    
    private void setScore(int red, int blue)
    {
        assert(red >= 0);
        assert(red < 1000);
        assert(blue >= 0);
        assert(blue < 1000);
        
        setText(String.format(" %03d : %03d", red, blue));

        setStyleRange(new StyleRange(1, 3, redColor, null));

        StyleRange boldStyle = new StyleRange();
        boldStyle.start = 5;
        boldStyle.length = 1;
        boldStyle.fontStyle = SWT.BOLD;
        setStyleRange(boldStyle);

        setStyleRange(new StyleRange(7, 3, blueColor, null));
    }
    
    private void setText(String text, Color color)
    {
        setText(text);
        
        StyleRange style = new StyleRange();
        style.foreground = color;
        style.start = 0;
        style.length = text.length();
        style.fontStyle = SWT.BOLD;
               
        setStyleRange(style);       
    }
}
