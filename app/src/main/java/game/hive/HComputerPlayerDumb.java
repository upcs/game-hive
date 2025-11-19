package game.hive;

import java.util.ArrayList;
import java.util.Random;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;
import game.hive.State.HSurfaceView;
import game.hive.State.Hex;
import game.hive.State.HexSpace;
import kotlin.Pair;

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
                Random rand = new Random();
                int rndNum = rand.nextInt(hand.size());
                int randX = 2+ rand.nextInt(25); //state.getBoard().size()
                int randY = 2+ rand.nextInt(15);
                String rndPiece = hand.get(rndNum).getName() ;

                //TODO: pick valid location
                //decrease handsize
                HPlaceAction action =  new HPlaceAction(this,randX,randY,rndPiece);
                Logger.log("computer", "computer player sending place "+rndPiece);
                game.sendAction(action);

            }

            else {
                //TODO: pick rnd piece to move

                // make array list of hexes
                ArrayList< Pair<Hex, Pair<Integer,Integer> > > comPiece = new ArrayList<>();
                ArrayList<ArrayList<HexSpace>> board = state.getBoard();

                for (int r = 0; r < board.size(); r++) {
                    for (int c = 0; c < board.get(r).size(); c++) {
                        if (board.get(r).get(c).getHex() != null) {
                            //if my piece add to list
                            if(board.get(r).get(c).getHex().getColor() == Hex.Color.BLACK)
                            {
                                comPiece.add(new Pair<>(board.get(r).get(c).getHex(), new Pair<>(r,c)));
                            }
                        }
                    }

                }//done

                // nothing to move
                if (comPiece.size() == 0) {
                    Logger.log("computer", "no black pieces to move");
                    return;
                }

                //rnd pick the array again
                Random rand = new Random();
                int rndNum = rand.nextInt(comPiece.size());


                Pair<Hex, Pair<Integer,Integer>> selRndPiece = comPiece.get(rndNum);

                Hex pieceToMove = selRndPiece.getFirst();
                int fromX = selRndPiece.getSecond().getFirst();
                int fromY = selRndPiece.getSecond().getSecond();

                Logger.log("computer",
                        "selected piece " + pieceToMove.getName() +
                                " at (" + fromX + "," + fromY + ")");

                //TODO: RANDOM SPACE ON BOARD( CHANGE TO VALID)
                int maxR = board.size();
                int maxC = board.get(0).size();

                int toX = (int)(Math.random() * maxR);
                int toY = (int)(Math.random() * maxC);

                Logger.log("computer",
                        "random destination chosen (" + toX + "," + toY + ")");

                HMoveAction actionMove = new HMoveAction(this, fromX, fromY, toX, toY);

                Logger.log("computer", "computer player sending move from (" +
                        fromX + "," + fromY + ") to (" + toX + "," + toY + ")");

                game.sendAction(actionMove);

                // rndmly pick location that valid
                //



            }
            //HPlaceAction action =  new HPlaceAction(this,6,6,"Spider");
            //Logger.log("computer", "computer player sending HMA ...");
            //game.sendAction(action);

        }


        //Logger.log("TTTComputer", "Sending move");
        //game.sendAction(new TTTMoveAction(this, yVal, xVal));

    }
}
