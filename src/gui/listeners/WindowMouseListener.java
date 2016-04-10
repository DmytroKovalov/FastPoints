package gui.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import config.ConfigManager;
import game.Game;
import game.GameMode;

public class WindowMouseListener implements MouseListener
{
    private final Shell window;

    private final Game game;

    private final int step;

    public WindowMouseListener(Shell window, Game game, int step)
    {
        this.window = window;
        this.game = game;
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

        //TODO: add supporting of this mode and remove this message
        GameMode mod = ConfigManager.getSettings().getGameMode();
        if (mod == GameMode.AI_VS_AI)
        {
            MessageBox dialog = new MessageBox(window, SWT.ICON_INFORMATION | SWT.OK);
            dialog.setText("Information...");
            dialog.setMessage("This functionality is not implemented yet.");
            dialog.open();
            return;
        }
        
        game.nextStep(i, j);
    }

    @Override
    public void mouseUp(MouseEvent e)
    {
        // not supported
    }
}
