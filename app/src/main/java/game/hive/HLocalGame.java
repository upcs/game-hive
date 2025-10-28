package game.hive;

import game.hive.GameFramework.LocalGame;
import game.hive.GameFramework.actionMessage.GameAction;
import game.hive.GameFramework.players.GamePlayer;
import game.hive.State.HGameState;

public class HLocalGame extends LocalGame {

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
        return false;
    }
}
