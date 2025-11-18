package game.hive;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;
import game.hive.State.HSurfaceView;

public class HComputerPlayerDumb extends GameComputerPlayer {

    /*
     * Constructor for the TTTComputerPlayer1 class
     */
    public HComputerPlayerDumb(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof HGameState){
            HPlaceAction action =  new HPlaceAction(this,6,6,"Spider");
            Logger.log("computer", "computer player sending place spider ...");
            game.sendAction(action);

        }


        //Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new TTTMoveAction(this, yVal, xVal));

    }
}
