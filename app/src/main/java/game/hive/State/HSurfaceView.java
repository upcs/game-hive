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

import java.util.ArrayList;
import java.util.HashMap;

import game.hive.R;


public class HSurfaceView extends SurfaceView {
    //private Bitmap myImageBitmap;
    private HashMap<String, Bitmap> pieces; // creates hashmap for all the insect pieces
    private final int LENGTH = 40; // hypotenuse of right triangle

    private final float s = LENGTH; // side
    final int a = (int) (LENGTH * Math.cos(1.047198)); // 30 degrees in radians
    final int b = (int) (LENGTH * Math.sin(1.047198)); // 30 degrees in radians

    // debug vals
    private float dbgX = -1f;
    private float dbgY = -1f;
    private Point dbgHexTile = null;

    private ArrayList<ArrayList<HexSpace>> board;

    

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
        super.onDraw(canvas);
        Paint hexColor = new Paint();
        hexColor.setColor(Color.RED);

        final float s = LENGTH;       //side length

        final float colStep = s + a;  // 1.5 * s
        final float rowStep = 2f * b;  // √3 * s

        int rows = 17;
        int cols = 18;

        float startX = 50f;
        float startY = 50f;

        // draw board
        for (int r = 0; r < rows; r++) {
            float yTop = startY + r * rowStep;
            // row 1 skips a "hex space" horizontally -> (2 * colStep)
            for (int c = 0; c < cols; c++) {
                float x0 = startX + c * (2f * colStep);
                drawHex(x0, yTop, 0, 0, hexColor, canvas);
            }
            // row 2 shift right by (s + a) and down by b
            for (int c = 0; c < cols; c++) {
                float x1 = startX + (s + a) + c * (2f * colStep);
                float y1 = yTop + b;
                drawHex(x1, y1, 0, 0, hexColor, canvas);
            }
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

        // debug overlay for coords
        Paint box = new Paint(Paint.ANTI_ALIAS_FLAG);
        box.setColor(Color.argb(180, 0, 0, 0));
        canvas.drawRect(80, 60, 680, 170, box);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setColor(Color.GREEN);
        tp.setTextSize(40f);
        // string lines
        String line1 = (dbgX >= 0f) ? String.format("Pixel: (%.1f, %.1f)", dbgX, dbgY) : "Pixel: (tap anywhere)";
        Point hexTile = (dbgHexTile != null) ? dbgHexTile : new Point(-1, -1);
        String line2 = (dbgHexTile != null) ? String.format("Board: (col=%d, row=%d)", hexTile.x, hexTile.y) : "Board: (n/a)";
        // overlay box coords
        canvas.drawText(line1, 100f, 115f, tp);
        canvas.drawText(line2, 100f, 160f, tp);

        //end of debugger

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

    // convert map (x,y) to board (row,col)
    public Point mapPixelToHex(float x, float y){

        return new Point(0,0);
    }

    public void setBoard(ArrayList<ArrayList<HexSpace>> board) {
        this.board = board;
    }

    public void setDebugTap(float x, float y, @androidx.annotation.Nullable Point hexTile) {
        this.dbgX = x;
        this.dbgY = y;
        this.dbgHexTile = hexTile;
        invalidate();
    }
}
