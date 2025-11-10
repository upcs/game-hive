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
import game.hive.State.HGameState;
import game.hive.State.HSurfaceView;

public class HHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    
    private int layoutId;
    private HSurfaceView surfaceView;
    private boolean IsWhiteBeetleSelected = false;
    private boolean IsBlackBeetleSelected = false;
    private boolean IsWhiteGrasshopperSelected = false;
    private boolean IsBlackGrasshopperSelected = false;
    private boolean IsWhiteBeeSelected = false;
    private boolean IsBlackBeeSelected = false;
    private boolean IsWhiteAntSelected = false;
    private boolean IsBlackAntSelected = false;
    private boolean IsWhiteSpiderSelected = false;
    private boolean IsBlackSpiderSelected = false;
    private int LastX;
    private int LastY;
    private boolean IsHexSelected = false;

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
        if(info instanceof HGameState){
            HGameState state = (HGameState)info;

            surfaceView.setBoard(state.getBoard());
            surfaceView.invalidate();
        }
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
        ImageView BlackBeetle = myActivity.findViewById(R.id.BlackBeetle);
        BlackBeetle.setOnTouchListener(this);
        ImageView WhiteGrasshopper= myActivity.findViewById(R.id.WhiteGrasshopper);
        WhiteGrasshopper.setOnTouchListener(this);
        ImageView BlackGrasshopper = myActivity.findViewById(R.id.BlackGrasshopper);
        BlackGrasshopper.setOnTouchListener(this);
        ImageView WhiteBee = myActivity.findViewById(R.id.WhiteBee);
        WhiteBee.setOnTouchListener(this);
        ImageView BlackBee = myActivity.findViewById(R.id.BlackBee);
        BlackBee.setOnTouchListener(this);
        ImageView WhiteAnt = myActivity.findViewById(R.id.WhiteAnt);
        WhiteAnt.setOnTouchListener(this);
        ImageView BlackAnt = myActivity.findViewById(R.id.BlackAnt);
        BlackAnt.setOnTouchListener(this);
        ImageView WhiteSpider = myActivity.findViewById(R.id.WhiteSpider);
        WhiteSpider.setOnTouchListener(this);
        ImageView BlackSpider = myActivity.findViewById(R.id.BlackSpider);
        BlackSpider.setOnTouchListener(this);

        WhiteBeetle.setImageResource(R.drawable.beetle);
        BlackBeetle.setImageResource(R.drawable.beetle);
        WhiteGrasshopper.setImageResource(R.drawable.grasshopper);
        BlackGrasshopper.setImageResource(R.drawable.grasshopper);
        WhiteBee.setImageResource(R.drawable.queenbee);
        BlackBee.setImageResource(R.drawable.queenbee);
        WhiteAnt.setImageResource(R.drawable.soldierant);
        BlackAnt.setImageResource(R.drawable.soldierant);
        WhiteSpider.setImageResource(R.drawable.spider);
        BlackSpider.setImageResource(R.drawable.spider);
        //WhiteBeetle.invalidate();

    }

    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;

        // finds what is selected
        if(v.getId()==R.id.WhiteBeetle){
            IsWhiteBeetleSelected = true;
        } else if(v.getId()==R.id.BlackBeetle){
            IsBlackBeetleSelected = true;
        } else if(v.getId()==R.id.WhiteGrasshopper){
            IsWhiteGrasshopperSelected = true;
        } else if(v.getId()==R.id.BlackGrasshopper){
            IsBlackGrasshopperSelected = true;
        } else if(v.getId()==R.id.WhiteBee){
            IsWhiteBeeSelected = true;
        } else if(v.getId()==R.id.BlackBee){
            IsBlackBeeSelected = true;
        } else if(v.getId()==R.id.WhiteAnt){
            IsWhiteAntSelected = true;
        } else if(v.getId()==R.id.BlackAnt){
            IsBlackAntSelected = true;
        } else if(v.getId()==R.id.WhiteSpider){
            IsWhiteSpiderSelected = true;
        } else if(v.getId()==R.id.BlackSpider){
            IsBlackSpiderSelected = true;
        } else if (v.getId()==R.id.surfaceView) {


            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = surfaceView.mapPixelToHex(x, y);

            // for coord debugger
            surfaceView.setDebugTap(x, y, p);

            if(IsWhiteBeetleSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Beetle");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteBeetleSelected = false;
                return true;
            }
            else if(IsBlackBeetleSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Beetle");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackBeetleSelected = false;
                return true;
            }
            else if(IsWhiteGrasshopperSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"GrassHopper");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteGrasshopperSelected = false;
                return true;
            }
            else if(IsBlackGrasshopperSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"GrassHopper");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackGrasshopperSelected = false;
                return true;
            }
            else if(IsWhiteBeeSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Bee");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteBeeSelected = false;
                return true;
            }
            else if(IsBlackBeeSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Bee");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackBeeSelected = false;
                return true;
            }
            else if(IsWhiteAntSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Ant");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteAntSelected = false;
                return true;
            }
            else if(IsBlackAntSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Ant");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackAntSelected = false;
                return true;
            }
            else if(IsWhiteSpiderSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Spider");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteSpiderSelected = false;
                return true;
            }
            else if(IsBlackSpiderSelected){
                HPlaceAction action =  new HPlaceAction(this,p.x,p.y,"Spider");
                //Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackSpiderSelected = false;
                return true;
            }
            else if(IsHexSelected) {

                HMoveAction action = new HMoveAction(this, LastX, LastY, p.x, p.y);
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);

                IsWhiteBeetleSelected = false;
                IsBlackBeetleSelected = false;
                IsWhiteGrasshopperSelected = false;
                IsBlackGrasshopperSelected = false;
                IsWhiteBeeSelected = false;
                IsBlackBeeSelected = false;
                IsWhiteAntSelected = false;
                IsBlackAntSelected = false;
                IsWhiteSpiderSelected = false;
                IsBlackSpiderSelected = false;
                IsHexSelected = false;
            }
            else{
                LastX = p.x;
                LastY = p.y;

                IsHexSelected = true;
            }

        }
        // register that we have handled the event
        return true;

    }

}
