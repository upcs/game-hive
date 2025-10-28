package game.hive;

import game.hive.GameFramework.LocalGame;
import game.hive.State.HGameState;

public class HLocalGame extends LocalGame {
    private HGameState theGameState;
    public HLocalGame(){
        theGameState = new HGameState();
    }
}
