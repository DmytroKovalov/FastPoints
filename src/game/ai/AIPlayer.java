package game.ai;

import org.eclipse.swt.graphics.Point;

import model.GameField;

public class AIPlayer
{
    private static RandomAIPlayer aiPlayer;

    public static Point nextStep(int i, int j)
    {
        return aiPlayer.nextStep(new Point(i, j));
    }

    public static void setGameField(GameField field)
    {
        aiPlayer = new RandomAIPlayer(field);
    }
}
