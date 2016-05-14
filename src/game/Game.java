package game;

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

    private GameState state;

    private boolean isCurrentRed;

    public Game(GameField field)
    {
        this.field = field;
        this.manager = new WindowManager(field, this);
        this.finder = new SurroundsFinder(field);
        this.aiPlayer = new AIPlayer(field);
        initGameState();
    }

    private void initGameState()
    {
        state = GameState.IN_PROGRESS;
        GameState.red = 0;
        GameState.blue = 0;
    }

    public void nextStep(int i, int j)
    {
        if ((i >= field.getWidth()) || (j >= field.getHeight()))
        {
            return;
        }

        GameMode mode = ConfigManager.getSettings().getGameMode();
        switch (mode)
        {
            case AI_VS_AI:
                // This functionality is not implemented yet.
                // TODO: add supporting of this mode
            case HUMAN_VS_AI:
                humanAndAIStep(i, j);
                break;
            case HUMAN_VS_HUMAN:
                if (putPointIfWeCan(i, j))
                {
                    manager.redraw();
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpectable mode: " + mode);
        }
    }

    private void humanAndAIStep(int i, int j)
    {
        if ((i < field.getWidth()) && (j < field.getHeight()))
        {
            if (putPointIfWeCan(i, j))
            {
                manager.redraw();
                if (state == GameState.IN_PROGRESS)
                {
                    Point point = aiPlayer.nextStep();
                    putPoint(point.x, point.y);
                    manager.redraw();
                }
            }
        }
    }

    private boolean putPointIfWeCan(int i, int j)
    {
        boolean isChanged = false;
        if (state != GameState.IN_PROGRESS)
        {
            return isChanged;
        }
        if (field.getPointState(i, j) == PointState.EMPTY)
        {
            if (!field.getAllSurroundedPoints().contains(new Point(i, j)))
            {
                isChanged = true;
                putPoint(i, j);
            }
        }
        return isChanged;
    }

    private void putPoint(int i, int j)
    {
        field.setPoint(i, j, isCurrentRed ? PointState.RED : PointState.BLUE);

        if (isCurrentRed)
        {
            GameState.red++;
        }
        else
        {
            GameState.blue++;
        }

        List<Surround> s = finder.findNewSurrounds(i, j);
        int numberOfInnerPoints = field.addAllSurrounds(s);
        int sign = isCurrentRed ? 1 : -1;
        GameState.red += sign * numberOfInnerPoints;
        GameState.blue -= sign * numberOfInnerPoints;

        isCurrentRed = !isCurrentRed;

        if (field.getEmptyPoints().isEmpty())
        {
            setEndGameState();
        }
    }

    private void setEndGameState()
    {
        if (GameState.red == GameState.blue)
        {
            state = GameState.GAME_DRAW;
        }
        else
        {
            state = (GameState.red > GameState.blue) ? GameState.RED_WIN : GameState.BLUE_WIN;
        }
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
        isCurrentRed = false;
        initGameState();
    }

    public GameState getGameState()
    {
        return state;
    }
}
