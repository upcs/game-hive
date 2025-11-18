package game.hive;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import game.hive.GameFramework.GameMainActivity;
import game.hive.GameFramework.LocalGame;
import game.hive.GameFramework.gameConfiguration.GameConfig;
import game.hive.GameFramework.gameConfiguration.GamePlayerType;
import game.hive.GameFramework.infoMessage.GameState;
import game.hive.GameFramework.players.GamePlayer;
import game.hive.State.HGameState;

public class HMainActivity extends GameMainActivity {
    private static final String TAG = "HMainActivity";
    public static final int PORT_NUMBER = 5213;

    /**
     *
     */
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
        // yellow-on-blue GUI
        playerTypes.add(new GamePlayerType("Local Human Player (blue-yellow)") {
            public GamePlayer createPlayer(String name) {
                return new HHumanPlayer(name, R.layout.activity_main);
            }
        });
        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new HComputerPlayerDumb(name);
            }
        });
        // smart computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new HComputerPlayerSmart(name);
            }
        });
        // Create a game configuration class for Hive
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Hive", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // local human
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1);

        //done!
        return defaultConfig;
    }
    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame(GameState gameState){
        if(gameState == null)
            return new HLocalGame();
        return new HLocalGame((HGameState) gameState);
    }

}