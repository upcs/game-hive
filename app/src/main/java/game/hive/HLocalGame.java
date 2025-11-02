package game.hive;

import game.hive.GameFramework.LocalGame;
import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;
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

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return "";
    }

    @Override
    protected boolean makeMove(GameAction action) {

        // get the row and column position of the player's move
        HMoveAction tm = (HMoveAction) action;
        HGameState state = (HGameState) super.state;

        int X = tm.getX();
        int Y = tm.getY();

        // get the 0/1 id of our player
        int playerId = getPlayerIdx(tm.getPlayer());

        // if it is this player's turn, indicate an illegal move
        if(!canMove(playerId)) return false;

        // if that space is not blank, indicate an illegal move
        if (state.getPiece(X, Y) != ' ') {
            return false;
        }

        // get the 0/1 id of the player whose move it is
        int whoseMove = state.getWhoseMove();

        // place the player's piece on the selected square
        state.setPiece(X, Y, mark[playerId]);

        // make it the other player's turn
        state.setWhoseMove(1 - whoseMove);

        // bump the move count
        moveCount++;

        // return true, indicating the it was a legal move
        return true;
    }
}
