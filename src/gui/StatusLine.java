package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

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

    // TODO: create class GameState and this class must have only
    // one public method: void utadeStatusLine(GameState gameState){...}
    public void setScore(int red, int blue)
    {
        setText(" 000 : 000");

        setStyleRange(new StyleRange(1, 3, redColor, null));

        StyleRange boldStyle = new StyleRange();
        boldStyle.start = 5;
        boldStyle.length = 1;
        boldStyle.fontStyle = SWT.BOLD;
        setStyleRange(boldStyle);

        setStyleRange(new StyleRange(7, 3, blueColor, null));
    }
    
    public void setWinnerRed()
    {
        setText(" Red win!!!", redColor);
    }
    
    public void setWinnerBlue()
    {
        setText(" Blue win!!!", blueColor);
    }
    
    public void setDraw()
    { 
        setText(" Game draw!", greenColor);
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
