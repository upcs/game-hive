package game.hive;

import android.view.View;

import game.hive.GameFramework.GameMainActivity;
import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameHumanPlayer;

public class HHumanPlayer extends GameHumanPlayer {

    
    private int layoutId;
    public HHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }
    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //This is basically the onCreate method
        //Set contentView here
        activity.setContentView(layoutId);
    }
}
