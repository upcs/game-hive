package game.hive;

import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;

public class HMoveAction extends GameAction {
    private int Xloc;
    private int Yloc;
    private int Xdest;
    private int Ydest;
    public HMoveAction(GamePlayer player, int Xloc, int Yloc, int Xdest, int Ydest)
    {
        // invoke superclass constructor to set the player
        super(player);

        // set the X and Y as passed to us
        this.Xloc=Xloc;
        this.Yloc=Yloc;
        this.Xdest=Xdest;
        this.Ydest=Ydest;
    }
    public int getXloc() { return Xloc; }
    public int getYloc() { return Yloc; }

    public int getXdest() {
        return Xdest;
    }

    public int getYdest() {
        return Ydest;
    }

    @Override
    public GamePlayer getPlayer() {
        return super.getPlayer();
    }
}
