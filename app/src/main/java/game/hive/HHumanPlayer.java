package game.hive;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;

import game.hive.GameFramework.GameMainActivity;
import game.hive.GameFramework.infoMessage.GameInfo;
import game.hive.GameFramework.players.GameHumanPlayer;
import game.hive.GameFramework.utilities.Logger;
import game.hive.State.HGameState;
import game.hive.State.HSurfaceView;

public class HHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {


    private int layoutId;
    private HSurfaceView surfaceView;
private GameInfo hexInfo;
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
    private ImageView[] whiteHand;
    private ImageView[] blackHand;

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
        if (info instanceof HGameState) {
            HGameState state = (HGameState) info;
            Logger.log("recievedInfo", "humanplayer has new state");
            setInfo(info);
            surfaceView.setBoard(state.getBoard());
            surfaceView.setActivePlayer(state.getActivePlayer());
            surfaceView.invalidate();
        }
    }
    public void setInfo(GameInfo info){
        hexInfo = info;
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //This is basically the onCreate method
        myActivity = activity;
        //Set contentView here
        activity.setContentView(layoutId);
        surfaceView = myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener", "OnTouch");
        surfaceView.setOnTouchListener(this);
        ImageView WhiteBeetle = myActivity.findViewById(R.id.WhiteBeetle);
        WhiteBeetle.setOnTouchListener(this);
        ImageView BlackBeetle = myActivity.findViewById(R.id.BlackBeetle);
        BlackBeetle.setOnTouchListener(this);
        ImageView WhiteGrasshopper = myActivity.findViewById(R.id.WhiteGrasshopper);
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

        WhiteBeetle.setImageResource(R.drawable.whtbeetle);
        BlackBeetle.setImageResource(R.drawable.blkbeetle);
        WhiteGrasshopper.setImageResource(R.drawable.whtgrasshopper);
        BlackGrasshopper.setImageResource(R.drawable.blkgrasshopper);
        WhiteBee.setImageResource(R.drawable.whtqueenbee);
        BlackBee.setImageResource(R.drawable.blkqueenbee);
        WhiteAnt.setImageResource(R.drawable.whtant);
        BlackAnt.setImageResource(R.drawable.blkant);
        WhiteSpider.setImageResource(R.drawable.whtspider);
        BlackSpider.setImageResource(R.drawable.blkspider);
        //WhiteBeetle.invalidate();

        whiteHand = new ImageView[]{WhiteBeetle, WhiteGrasshopper, WhiteBee, WhiteAnt, WhiteSpider};
        blackHand = new ImageView[]{BlackBeetle, BlackGrasshopper, BlackBee, BlackAnt, BlackSpider};

        for (ImageView hand : whiteHand) {
            hand.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    highlightOnly((ImageView) view);
                }
            });
        }

        for (ImageView hand : blackHand) {
            hand.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    highlightOnly((ImageView) view);
                }
            });
        }

        for (ImageView wHand : whiteHand) {
            wHand.setOnClickListener(view -> highlightOnly((ImageView) view));
        }

        for (ImageView bHand : blackHand) {
            bHand.setOnClickListener(view -> highlightOnly((ImageView) view));
        }
        surfaceView.setOnTouchListener(this);

    }

    private void highlightOnly(ImageView selected) {
        boolean isWhiteHand = Arrays.asList(whiteHand).contains(selected);
        if (isWhiteHand) {
            for (ImageView hand : whiteHand) {
                if (hand == selected) {
                    hand.setBackgroundResource(R.drawable.highlight_border);
                } else {
                    hand.setBackground(null);
                }
            }
            for (ImageView blkHand : blackHand) {
                blkHand.setBackground(null);
            }
        } else {

            for (ImageView blkHand : blackHand) {
                if (blkHand == selected) {
                    blkHand.setBackgroundResource(R.drawable.highlight_border);
                } else {
                    blkHand.setBackground(null);
                }
            }
            for (ImageView hand : whiteHand) {
                hand.setBackground(null);
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return false;


        // finds what is selected
        if (v.getId() == R.id.WhiteBeetle) {
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
            IsWhiteBeetleSelected = true;
        } else if (v.getId() == R.id.BlackBeetle) {
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
            IsBlackBeetleSelected = true;
        } else if (v.getId() == R.id.WhiteGrasshopper) {
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
            IsWhiteGrasshopperSelected = true;
            Logger.log("onTouch()", "WhiteGrasshopperselected");
        } else if (v.getId() == R.id.BlackGrasshopper) {
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
            IsBlackGrasshopperSelected = true;
        } else if (v.getId() == R.id.WhiteBee) {
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
            IsWhiteBeeSelected = true;
        } else if (v.getId() == R.id.BlackBee) {
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
            IsBlackBeeSelected = true;
        } else if (v.getId() == R.id.WhiteAnt) {
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
            IsWhiteAntSelected = true;
        } else if (v.getId() == R.id.BlackAnt) {
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
            IsBlackAntSelected = true;
        } else if (v.getId() == R.id.WhiteSpider) {
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
            IsWhiteSpiderSelected = true;
        } else if (v.getId() == R.id.BlackSpider) {
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
            IsBlackSpiderSelected = true;
        } else if (v.getId() == R.id.surfaceView) {


            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = surfaceView.mapPixelToHex(x, y);

            // for coord debugger
            surfaceView.setDebugTap(x, y, p);

            // prevent crash if off-board tap
            if (p == null) {
                return true;
            }

            int col = p.x;
            int row = p.y;

            if (IsWhiteBeetleSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Beetle");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteBeetleSelected = false;
                return true;
            } else if (IsBlackBeetleSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Beetle");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackBeetleSelected = false;
                return true;
            } else if (IsWhiteGrasshopperSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Grasshopper");
                Logger.log("onTouch", "Human player sending Place White GrassHopper...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteGrasshopperSelected = false;
                return true;
            } else if (IsBlackGrasshopperSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Grasshopper");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackGrasshopperSelected = false;
                return true;
            } else if (IsWhiteBeeSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "QueenBee");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteBeeSelected = false;
                return true;
            } else if (IsBlackBeeSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "QueenBee");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackBeeSelected = false;
                return true;
            } else if (IsWhiteAntSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Ant");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteAntSelected = false;
                return true;
            } else if (IsBlackAntSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Ant");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackAntSelected = false;
                return true;
            } else if (IsWhiteSpiderSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Spider");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsWhiteSpiderSelected = false;
                return true;
            } else if (IsBlackSpiderSelected) {
                HPlaceAction action = new HPlaceAction(this, col, row, "Spider");
                Logger.log("onTouch", "Human player sending HMA ...");
                game.sendAction(action);
                surfaceView.invalidate();
                IsBlackSpiderSelected = false;
                return true;
            } else if (IsHexSelected) {

                // LastX = col, LastY = row, but HGameState is (row, col)
                HMoveAction action = new HMoveAction(this, LastY, LastX, row, col);
                Logger.log("onTouch",
                        "Human player sending move from (" + LastY + "," + LastX + ") to (" + row + "," + col + ")");
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

                surfaceView.invalidate();
                return true;
            } else {
                LastX = col;
                LastY = row;
                Logger.log("ishexSelected",
                        "human player ishexSelected true at (" + col + "," + row + ")");
                IsHexSelected = true;
            }


        }
        // register that we have handled the event
        return false;

    }

}
