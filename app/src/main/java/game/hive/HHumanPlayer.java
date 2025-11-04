package game.hive;

import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import game.hive.GameFramework.GameMainActivity;
import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameHumanPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HSurfaceView;

public class HHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    
    private int layoutId;
    private HSurfaceView surfaceView;
    private boolean IsWhiteBeetleSelected = false;
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
        myActivity = activity;
        //Set contentView here
        activity.setContentView(layoutId);
        surfaceView = myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
        ImageView WhiteBeetle = myActivity.findViewById(R.id.WhiteBeetle);
        WhiteBeetle.setOnTouchListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;

        if(v.getId()==R.id.WhiteBeetle){
            //White Beetle selected
            IsWhiteBeetleSelected = true;
        } else if (v.getId()==R.id.surfaceView) {


            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = surfaceView.mapPixelToHex(x, y);

            if(IsWhiteBeetleSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Beetle");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteBeetleSelected = false;
                return true;
            }

            HMoveAction action = new HMoveAction(this, p.x, p.y);
            Logger.log("onTouch", "Human player sending HMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
            IsWhiteBeetleSelected = false;
        }
        // register that we have handled the event
        return true;

    }

}
