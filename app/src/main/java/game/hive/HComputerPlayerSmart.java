package game.hive;

import java.util.ArrayList;
import java.util.Random;

import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameComputerPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;
import game.hive.State.Hex;
import game.hive.State.HexSpace;
import kotlin.Pair;

public class HComputerPlayerSmart extends GameComputerPlayer {

    /*
     * Constructor for the TTTComputerPlayer1 class
     */
    public HComputerPlayerSmart(String name) {
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

            // getting player hand
            ArrayList<Hex> hand = state.getBlackHand();

            if(hand.size()>0) {

                // place queen by turn 4
                boolean myQueenPlaced = state.getIsBeePlaced()[playerNum];
                int currentTurn = state.getTurnNumber();
                int maxAttempt =  50; // random number picked that worked

                //place bee before turn 3
                if (!myQueenPlaced && currentTurn > 3) {
                    // find queen in hand
                    for (Hex piece : hand) {
                        if ("QueenBee".equals(piece.getName())) {

                            // if its the bee, then try and find a spot randomly
                            Random rand = new Random();
                            for (int attempt = 0; attempt < maxAttempt; attempt++) {

                                int randX = 2 + rand.nextInt(25);
                                int randY = 2 + rand.nextInt(15);
                                if (state.isValidPlacement(randX, randY, "QueenBee", playerNum)) {

                                    HPlaceAction action = new HPlaceAction(this, randX, randY, "QueenBee");
                                    Logger.log("computer", "computer player placing QueenBee at (" + randX + "," + randY + ")");
                                    game.sendAction(action);
                                    return;

                                }
                            }
                        }
                    }
                }

                // once bee is placed, random place logic
                Random rand = new Random();
                int rndNum = rand.nextInt(hand.size());
                int randX = 2 + rand.nextInt(25);
                int randY = 2 + rand.nextInt(15);
                String rndPiece = hand.get(rndNum).getName();

                HPlaceAction action = new HPlaceAction(this, randX, randY, rndPiece);
                Logger.log("computer", "computer player sending piece: " + rndPiece);
                game.sendAction(action);

            }

            else {
                //once all pieces are placed, start moving pieces smartly

                // get black pieces
                ArrayList <Pair <Hex, Pair<Integer,Integer> > > comPiece = new ArrayList<>();
                ArrayList <ArrayList <HexSpace> > board = state.getBoard();

                for (int r = 0; r < board.size(); r++) {
                    for (int c = 0; c < board.get(r).size(); c++) {
                        if (board.get(r).get(c).getHex() != null) {
                            //if piece is black, add to list
                            if(board.get(r).get(c).getHex().getColor() == Hex.Color.BLACK)
                            {
                                comPiece.add(new Pair<>(board.get(r).get(c).getHex(), new Pair<>(r,c)));
                            }
                        }
                    }
                }

                //if no pieces to move
                if (comPiece.size() == 0) {
                    Logger.log("computer", "no black pieces to move");
                    return;
                }

                // bot prioritizes blocking enemy bee in
                int[] enemyQueenPos = null;

                // loop through board and find enemy queen and save it's coordinates
                for (int r = 0; r < board.size(); r++) {
                    for (int c = 0; c < board.get(r).size(); c++) {
                        if (board.get(r).get(c).getHex() != null) {
                            Hex piece = board.get(r).get(c).getHex();
                            if ("QueenBee".equals(piece.getName()) && piece.getColor() == Hex.Color.WHITE) {
                                enemyQueenPos = new int[]{r, c};
                                break;
                            }
                        }
                    }
                }

                if (enemyQueenPos != null) {
                    // find empty spaces around enemy queen
                    ArrayList<int[]> emptySpaces = new ArrayList<>();
                    int enemyX = enemyQueenPos[0];
                    int enemyY = enemyQueenPos[1];

                    int[][] neighborOffsetsEven = {
                            {-2, 0}, {-1, 0}, {-1, -1}, {2, 0}, {1, 0}, {1, -1}
                    };
                    int[][] neighborOffsetsOdd = {
                            {-2, 0}, {-1, 1}, {-1, 0}, {2, 0}, {1, 1}, {1, 0}
                    };

                    int[][] offsets;
                    if (enemyX % 2 == 0) {
                        offsets = neighborOffsetsEven;
                    }
                    else {
                        offsets = neighborOffsetsOdd;
                    }

                    // find empty spaces around enemy queen
                    for (int[] offset : offsets) {
                        int neighborX = enemyX + offset[0];
                        int neighborY = enemyY + offset[1];

                        //if these coordinates are in bounds
                        if (neighborX >= 0 && neighborX < board.size() && neighborY >= 0 && neighborY < board.get(neighborX).size()) {
                            if (board.get(neighborX).get(neighborY).getHex() == null) {
                                emptySpaces.add(new int[]{neighborX, neighborY});
                            }
                        }
                    }

                    // try to move any piece to block enemy queen
                    for (Pair <Hex, Pair <Integer,Integer> > pieceInfo : comPiece) {
                        Hex piece = pieceInfo.getFirst();

                        // learning using this : https://stackoverflow.com/questions/54894413/targeting-the-first-and-second-values-in-a-classint-int
                        int fromX = pieceInfo.getSecond().getFirst(); // get the x coordinates from the pair
                        int fromY = pieceInfo.getSecond().getSecond(); // get y coordinates from the pair

                        // logic for if piece is next to queen already, don't move it
                        boolean alreadyAdjacent = false;

                        // check neighbor positions around this piece
                        int[][] pieceNeighborOffsets;
                        if (fromX % 2 == 0) {
                            pieceNeighborOffsets = neighborOffsetsEven;
                        } else {
                            pieceNeighborOffsets = neighborOffsetsOdd;
                        }

                        for (int[] offset : pieceNeighborOffsets) {
                            int neighborX = fromX + offset[0];
                            int neighborY = fromY + offset[1];

                            // check if this neighbor is within bounds
                            if (neighborX >= 0 && neighborX < board.size() && neighborY >= 0 && neighborY < board.get(neighborX).size()) {

                                // Check if this neighbor is the enemy queen
                                Hex neighborHex = board.get(neighborX).get(neighborY).getHex();
                                if (neighborHex != null && "QueenBee".equals(neighborHex.getName()) && neighborHex.getColor() == Hex.Color.WHITE) {
                                    alreadyAdjacent = true;
                                    break;
                                }
                            }
                        }

                        //don't move pieces that are already adjacent to the queen
                        if (alreadyAdjacent) {
                            continue; // try a different piece
                        }

                        //for each of the empty positions around the queen, check if bot can move into that spot
                        for (int[] emptyPos : emptySpaces) {
                            if (state.isValidMove(fromX, fromY, emptyPos[0], emptyPos[1], playerNum)) {
                                HMoveAction actionMove = new HMoveAction(this, fromX, fromY, emptyPos[0], emptyPos[1]);
                                Logger.log("computer", "Blocking enemy queen with " + piece.getName());
                                game.sendAction(actionMove);
                                return;
                            }
                        }
                    }
                }

                // if an issue comes up with smart logic, default back to random moves
                Random rand = new Random();
                int rndNum = rand.nextInt(comPiece.size());

                Pair <Hex, Pair <Integer,Integer> > selRndPiece = comPiece.get(rndNum);

                Hex pieceToMove = selRndPiece.getFirst();
                int fromX = selRndPiece.getSecond().getFirst();
                int fromY = selRndPiece.getSecond().getSecond();

                Logger.log("computer",
                        "chose piece " + pieceToMove.getName() +
                                " at (" + fromX + "," + fromY + ")");

                // try random moves, max value picked randomly but works
                for (int attempt = 0; attempt < 50; attempt++) {
                    int toX = rand.nextInt(board.size());
                    int toY = rand.nextInt(board.get(toX).size());

                    if (state.isValidMove(fromX, fromY, toX, toY, playerNum)) {
                        HMoveAction actionMove = new HMoveAction(this, fromX, fromY, toX, toY);
                        Logger.log("computer", "computer player sending move from (" +
                                fromX + "," + fromY + ") to (" + toX + "," + toY + ")");
                        game.sendAction(actionMove);
                        return;
                    }
                }

                //if everything fails send out this log
                Logger.log("computer", "No valid moves found");
            }
        }
    }
}