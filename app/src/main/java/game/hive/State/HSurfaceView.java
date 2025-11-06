package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import game.hive.R;


public class HSurfaceView extends SurfaceView {
    //private Bitmap myImageBitmap;
    private HashMap<String, Bitmap> pieces;
    private final int LENGTH = 40;
    final int a = (int) (LENGTH * Math.cos(0.523599));
    final int b = (int) (LENGTH * Math.sin(0.523599));
    

    public HSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        pieces = new HashMap<>();
        pieces.put("Beetle", BitmapFactory.decodeResource(getResources(), R.drawable.beetle));
        pieces.put("Grasshopper", BitmapFactory.decodeResource(getResources(), R.drawable.grasshopper));
        pieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.hive_black_queen_logo_hd));
        pieces.put("Delete", BitmapFactory.decodeResource(getResources(), R.drawable.purple_delete_button));
        pieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.queenbee));
        pieces.put("Ant", BitmapFactory.decodeResource(getResources(), R.drawable.soldierant));
        pieces.put("Spider", BitmapFactory.decodeResource(getResources(), R.drawable.spider));
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint hexColor = new Paint();
        hexColor.setColor(Color.RED);
        drawHex(1,2,10,10,hexColor, canvas);
        super.onDraw(canvas);
        if(pieces != null){
            Rect srcRect = null;//new Rect(0,0, pieces.getWith(), pieces.getHeight());
            Rect dstRect = new Rect(0,0,LENGTH,2*b);
            //Paint paint = new Paint();
            canvas.drawBitmap(pieces.get("Beetle"),srcRect,dstRect,null);


        }
        /*if(pieces != null){
            canvas.drawBitmap(pieces.get("Beetle"),0,0,null);
        }*/
    }
    public void drawPiece(Canvas canvas, String namePiece){
        Rect srcRect = null;//new Rect(0,0, pieces.getWith(), pieces.getHeight());
        Rect dstRect = new Rect(0,0,LENGTH,2*b);
        //Paint paint = new Paint();
        canvas.drawBitmap(pieces.get(namePiece),srcRect,dstRect,null);
    }

    public void drawHex(float x, float y, float x2, float y2, Paint color, Canvas canvas){


        color.setStyle(Paint.Style.STROKE);
        color.setStrokeWidth(5);
        canvas.drawLine(0, 0, LENGTH, 0, color);
        canvas.drawLine(LENGTH, 0, (LENGTH + a), b, color);
        canvas.drawLine( (int) (LENGTH + a),(int) b, LENGTH , 2*b ,color);
        canvas.drawLine(0, 0, -(LENGTH + a), b, color);
        canvas.drawLine(0, 2*b, -(LENGTH + a), b, color);





    }
    public Point mapPixelToHex(int x, int y){

        return new Point(0,0);
    }

}
