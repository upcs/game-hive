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
    private HashMap<String, Bitmap> pieces; // creates hashmap for all the insect pieces
    private final int LENGTH = 40; // hypotenuse of right triangle
    private final float s = LENGTH; // side
    final int a = (int) (LENGTH * Math.cos(1.047198)); // 30 degrees in radians
    final int b = (int) (LENGTH * Math.sin(1.047198)); // 30 degrees in radians
    

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

        final float s = LENGTH;       //side length
        final float a = 0.5f * s;
        final float b = 0.8660254f * s;

        final float colStep = s + a;  // 1.5 * s

        float startX = 50f;
        float startY = 50f;

        // draw board
        for (int i = 0; i <= 10; i++) {
            float x0 = startX + i * (2*colStep); // skips a hexagon "space"
            drawHex(x0, startY, 0, 0, hexColor, canvas); // row 0
            drawHex(x0 + (s + a), startY + b, 0, 0, hexColor, canvas); //row offset
        }
        //sample piece
        if(pieces != null) {
            Rect srcRect = null; //new Rect(0,0, pieces.getWith(), pieces.getHeight());
            Rect dstRect = new Rect((int)(startX + s),
                    (int)(startY + b),
                    (int)(startX + s + LENGTH),
                    (int)(startY + b + 2*b));

            // Paint paint = new Paint();
            canvas.drawBitmap(pieces.get("Beetle"), srcRect, dstRect, null);
        }


        super.onDraw(canvas);
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

        // top
        canvas.drawLine(x, y, x + s, y, color);
        // top right
        canvas.drawLine(x + s, y, x + s + a, y + b, color);
        // bottom right
        canvas.drawLine(x + s + a, y + b, x + s, y + 2*b, color);
        // bottom
        canvas.drawLine(x + s, y + 2*b, x, y + 2*b, color);
        // bottom left
        canvas.drawLine(x, y + 2*b, x - a, y + b, color);
        // top left
        canvas.drawLine(x - a, y + b, x, y, color);


    }
    public Point mapPixelToHex(int x, int y){

        return new Point(0,0);
    }

}
