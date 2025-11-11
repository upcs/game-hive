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

    private final float s = LENGTH; // side length

    private static final float startX = 50f;
    private static final float startY = 50f;

    private final float a = 0.5f * s; // horizontal offset
    private final float b = 0.8660254f * s; // vertical offset (≈ √3/2 * s)

    private final float colStep = s + a;
    private static final int TOTAL_ROWS = 34;
    private static final int TOTAL_COLS = 18;


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

        final float s = LENGTH; //side length

        // draw board
        for (int r = 0; r < TOTAL_ROWS; r++) {
            boolean isOdd = (r & 1) == 1;

            // top left Y (startY + row * b)
            float y0 = startY + r * b;

            // odd TOTAL_ROWS shift right by (s + a)
            float xRowOffset = isOdd ? (s + a) : 0f;

            for (int col = 0; col < TOTAL_COLS; col++) {
                float x0 = startX + xRowOffset + col * (2f * colStep);
                drawHex(x0, y0, hexColor, canvas);
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
        Rect dstRect = new Rect(0,0,LENGTH, (int) (2*b));
        //Paint paint = new Paint();
        canvas.drawBitmap(pieces.get(namePiece),srcRect,dstRect,null);
    }

    public void drawHex(float x, float y, Paint color, Canvas canvas){

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

        // vertical boundaries
        if (y < startY) return null;

        // row guess by rounding
        int row0 = (int)Math.floor((y - startY) / b);

        // test current row and neighbors
        int[] rowsToTest = new int[] { row0, row0 - 1, row0 + 1 };

        for (int row : rowsToTest) {
            if (row < 0 || row >= TOTAL_ROWS) continue;

            boolean isOdd = (row & 1) == 1;
            float y0 = startY + row * b;

            float baseX = startX + (isOdd ? (s + a) : 0f);
            // column guess for this row
            int col0 = (int)Math.floor((x - baseX) / (2f * colStep));

            // check neighbor columns
            for (int col = col0 - 1; col <= col0 + 1; col++) {
                if (col < 0 || col >= TOTAL_COLS) continue;

                float x0 = baseX + col * (2f * colStep);
                if (isInsideHex(x, y, x0, y0)) {
                    return new Point(col, row); // (col, row)
                }
            }
        }
        return null; // if outside board or in gaps
        //return new Point(0,0);
    }

    public void setBoard(ArrayList<ArrayList<HexSpace>> board) {
        this.board = board;
    }

    public void setDebugTap(float x, float y, Point hexTile) {
        this.dbgX = x;
        this.dbgY = y;
        this.dbgHexTile = hexTile;
        invalidate();
    }

    // click inside hex?
    private boolean isInsideHex(float px, float py, float x0, float y0) {
        float dy = py - y0;
        // if the y-distance from the top is negative or greater than twice the triangle height... the point cant be inside the hex
        if (dy < 0f || dy > 2f * b) return false;

        float dx = px - x0;
        if (dy <= b) {
            // top half hex
            // dy increases from 0 to b (vert height of triangle)
            float left  = - (a / b) * dy;  // 0 to -a
            float right = s + (a / b) * dy; // s to s+a
            return dx >= left && dx <= right;
        } else {
            // bottom half of hex
            float t = dy - b;
            float left  = -a + (a / b) * t; // -a to 0
            float right =  s + a - (a / b) * t;// s+a to s
            return dx >= left && dx <= right;
        }
    }

}
