package game.hive;

import game.hive.GameFramework.LocalGame;
import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;

public class HLocalGame extends LocalGame {
    protected int moveCount;

    public HLocalGame(){
        super();
        super.state = new HGameState();

    }
    public HLocalGame(HGameState hGameState){
        super();
        super.state = new HGameState(hGameState);

    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new HGameState(((HGameState) state)));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if(state == null){
            return false;
        }
        HGameState mystate = (HGameState)state;
        return mystate.getActivePlayer()==playerIdx;
    }

    @Override
    protected String checkIfGameOver() {
        HGameState myState = (HGameState) state;  // cast to your concrete state
        int winner = myState.checkWinner();

        if (winner == -1) return null;           // game still going
        if (winner == 0)  return "White wins!";
        if (winner == 1)  return "Black wins!";
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {

        // get the row and column position of the player's move

        HGameState state = (HGameState) super.state;
        Logger.log("makemove","recived an action");

        if(action instanceof HPlaceAction)
        {
            Logger.log("makemove","recived place action");
            HPlaceAction tm = (HPlaceAction) action;
            int X = tm.getX();
            int Y = tm.getY();
            String name =  tm.getName();
            // get the 0/1 id of our player
            int playerId = getPlayerIdx(tm.getPlayer());

            // if it is this player's turn, indicate an illegal move
            if (!canMove(playerId)) return false;
            Logger.log("makemove","making place action "+X+", " +Y);
            return state.placePiece(X,Y,name,playerId);


        }
        else if(action instanceof HMoveAction)
        {
            HMoveAction tm = (HMoveAction) action;
            int Xloc = tm.getXloc();
            int Yloc = tm.getYloc();
            int Xdest = tm.getXdest();
            int Ydest = tm.getYdest();
            int playerId = getPlayerIdx(tm.getPlayer());
            if(!canMove(playerId)) return false;
            return state.movePiece(Xloc,Yloc,Xdest,Ydest,playerId);
         /*   HMoveAction tm = (HMoveAction) action;
            int X = tm.getX();
            int Y = tm.getY();

            // get the 0/1 id of our player
            int playerId = getPlayerIdx(tm.getPlayer());

            // if it is this player's turn, indicate an illegal move
            if (!canMove(playerId)) return false;

            // if that space is not blank, indicate an illegal move
            if (state.getPiece(X, Y) != ' ') {
                return false;
            }

            // get the 0/1 id of the player whose move it is
            int whoseMove = state.getWhoseMove();

            // place the player's piece on the selected square
            state.setPiece(X, Y, playerId, "name");

            // make it the other player's turn
            state.setWhoseMove(1 - whoseMove);

            // bump the move count
            moveCount++;

            // return true, indicating the it was a legal move
            return true;
        */}
        return false;
    }
}
