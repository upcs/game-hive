package game.hive;

import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;

public class HPlaceAction extends GameAction {
    private int X;
    private int Y;
    private String Name;
    public HPlaceAction(GamePlayer player, int Y, int X, String name){
        // invoke superclass constructor to set the player
        super(player);

        // set the X and Y as passed to us
        this.X=X;
        this.Y=Y;
        this.Name = name;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getName() {
        return Name;
    }
}
