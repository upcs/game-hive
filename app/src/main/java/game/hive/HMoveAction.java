package game.hive;

import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;

public class HMoveAction extends GameAction {
    private int X;
    private int Y;
    public HMoveAction(GamePlayer player, int X, int Y)
    {
        // invoke superclass constructor to set the player
        super(player);

        // set the X and Y as passed to us
        this.X=X;
        this.Y=Y;
    }
    public int getX() { return X; }
    public int getY() { return Y; }
}
