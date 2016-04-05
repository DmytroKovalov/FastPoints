package game;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Point;

import config.ConfigManager;
import game.ai.AIPlayer;
import gui.WindowManager;
import model.GameField;
import model.PointState;
import model.Surround;
import util.SurroundsFinder;

public class Game
{
    private final GameField field;

    private final WindowManager manager;

    private final SurroundsFinder finder;
    
    private final AIPlayer aiPlayer;

    private boolean isCurrentRed;
    
    private List<Point> redPoints = new ArrayList<Point>();
    private List<Point> bluePoints = new ArrayList<Point>();

    public Game(GameField field)
    {
        this.field = field;
        this.manager = new WindowManager(field, this);
        this.finder = new SurroundsFinder(field);
        this.aiPlayer = new AIPlayer(field);
    }

    public void nextStep(int i, int j)
    {
        if ((i >= field.getWidth()) || (j >= field.getHeight()))
        {
            return;
        }
                
        GameMode mod = ConfigManager.getSettings().getGameMode();
        switch (mod)
        {
            case AI_VS_AI:
                // This functionality is not implemented yet.
                // TODO: add supporting of this mode
            case HUMAN_VS_AI:
                humanAndAIStep(i, j);
                break;
            case HUMAN_VS_HUMAN:
                if (putPointIfWecan(i, j))
                {
                    manager.redraw();
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpectable mode: " + mod);
        }
    }

    private void humanAndAIStep(int i, int j)
    {
        if ((i < field.getWidth()) && (j < field.getHeight()))
        {
            if (putPointIfWecan(i, j))
            {
                manager.redraw();
                Point point = aiPlayer.nextStep(i, j);
                if (point == null)
                {
                    // end game; TODO: implement
                }
                putPoint(point.x, point.y);
                findNewSurrounds(point.x, point.y);
                manager.redraw();
            }
        }
    }

    private boolean putPointIfWecan(int i, int j)
    {
        boolean isChanged = false;
        PointState state = field.getPointState(i, j);
        //TODO: add checking - we can not add point into surround
        if (state == PointState.EMPTY)            
        {
            isChanged = true;
            putPoint(i, j);
            findNewSurrounds(i, j);
        }
        return isChanged;
    }

    private void findNewSurrounds(int i, int j)
    {
        List<Surround> s = finder.findNewSurrounds(i, j);
        field.addAllSurrounds(s);
    }

    private void putPoint(int i, int j)
    {
        field.setPoint(i, j, isCurrentRed ? PointState.RED : PointState.BLUE);
        isCurrentRed = !isCurrentRed;
    }

    /**
     * show main window and start game
     */
    public void start()
    {
        manager.showWindow();
    }

    public void newGame()
    {
        field.clear();
        redPoints.clear();
        bluePoints.clear();
        aiPlayer.newGame();
        isCurrentRed = false;
    }
}
