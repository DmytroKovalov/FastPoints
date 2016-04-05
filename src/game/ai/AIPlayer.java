package game.ai;

import org.eclipse.swt.graphics.Point;

import model.GameField;

public class AIPlayer
{
    private final RandomAIPlayer aiPlayer;

    public AIPlayer(GameField field)
    {
        aiPlayer = new RandomAIPlayer(field);
    }

    public Point nextStep(int i, int j)
    {
        return aiPlayer.nextStep(new Point(i, j));
    }

    public void newGame()
    {
        aiPlayer.newGame();
    }
}
