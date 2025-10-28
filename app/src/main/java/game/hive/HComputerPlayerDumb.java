package game.hive;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;

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


        //Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new TTTMoveAction(this, yVal, xVal));

    }
}
