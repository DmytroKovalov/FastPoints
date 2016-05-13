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

    public Point nextStep()
    {
        return aiPlayer.nextStep();
    }    
}
