package game.hive;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;

public class HComputerPlayerSmart extends GameComputerPlayer {

    /*
     * Constructor for the TTTComputerPlayer1 class
     */
    public HComputerPlayerSmart(String name) {
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
            HPlaceAction action =  new HPlaceAction(this,6,6,"Beetle");
            Logger.log("computer", "computer player sending HMA ...");
            game.sendAction(action);

            // if there is a piece that can be placed, place the piece by the opponent to "block"
            //if there is no piece that can be placed, move a piece to "block"


        }
        //Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new TTTMoveAction(this, yVal, xVal));

    }

}
