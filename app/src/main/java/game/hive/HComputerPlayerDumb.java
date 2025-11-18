package game.hive;

import java.util.ArrayList;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;
import game.hive.State.HSurfaceView;
import game.hive.State.Hex;

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

            HGameState state = (HGameState)info;
            //place rnd piece until no more pieces
            ArrayList<Hex> hand = state.getBlackHand();



            if(hand.size()>0) {
                int rndNum = (int) Math.random() * (hand.size()-1);
                String rndPiece = hand.get(rndNum).getName() ;

                //TODO: pick valid location

                HPlaceAction action =  new HPlaceAction(this,6,6,rndPiece);
                Logger.log("computer", "computer player sending place "+rndPiece);
                game.sendAction(action);

            }
            else {
                //TODO: pick rnd piece to move


            }
            //HPlaceAction action =  new HPlaceAction(this,6,6,"Spider");
            //Logger.log("computer", "computer player sending HMA ...");
            //game.sendAction(action);

        }


        //Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new TTTMoveAction(this, yVal, xVal));

    }
}
