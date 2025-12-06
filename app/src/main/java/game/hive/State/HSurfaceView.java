package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;

import game.hive.R;


public class HSurfaceView extends SurfaceView {
    //private Bitmap myImageBitmap;
    private HashMap<String, Bitmap> pieces; // creates hashmap for all the insect pieces
    private HashMap<String, Bitmap> blackPieces;
    private final int LENGTH = 40; // hypotenuse of right triangle

    private final float s = LENGTH; // side length

    private static final float startX = 50f;
    private static final float startY = 50f;

    private final float a = 0.5f * s; // horizontal offset
    private final float b = 0.8660254f * s; // vertical offset (≈ √3/2 * s)

    private final float colStep = s + a;
    private static final int TOTAL_ROWS = 34;
    private static final int TOTAL_COLS = 17;

    // debug vals
    private float dbgX = -1f;
    private float dbgY = -1f;
    private Point dbgHexTile = null;

    private ArrayList<ArrayList<HexSpace>> board;
    Paint highlightColor = new Paint();
    private boolean hexSelected;
    private Point tappedHex;
    private int activePlayer = -1;




    public HSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setFocusable(true);
        setClickable(true);
        highlightColor.setColor(Color.RED);
        hexSelected = false;
        pieces = new HashMap<>();
        pieces.put("Beetle", BitmapFactory.decodeResource(getResources(), R.drawable.whtbeetle));
        pieces.put("Grasshopper", BitmapFactory.decodeResource(getResources(), R.drawable.whtgrasshopper));
        pieces.put("Delete", BitmapFactory.decodeResource(getResources(), R.drawable.purple_delete_button));
        pieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.whtqueenbee));
        pieces.put("Ant", BitmapFactory.decodeResource(getResources(), R.drawable.whtant));
        pieces.put("Spider", BitmapFactory.decodeResource(getResources(), R.drawable.whtspider));

        blackPieces = new HashMap<>();
        blackPieces.put("Beetle", BitmapFactory.decodeResource(getResources(), R.drawable.blkbeetle));
        blackPieces.put("Grasshopper", BitmapFactory.decodeResource(getResources(), R.drawable.blkgrasshopper));
        blackPieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.blkqueenbee));
        blackPieces.put("Delete", BitmapFactory.decodeResource(getResources(), R.drawable.purple_delete_button));
        blackPieces.put("Ant", BitmapFactory.decodeResource(getResources(), R.drawable.blkant));
        blackPieces.put("Spider", BitmapFactory.decodeResource(getResources(), R.drawable.blkspider));

    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint hexColor = new Paint();
        hexColor.setColor(Color.YELLOW);

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
                //
                drawHex(x0, y0, hexColor, canvas, r, col);
            }
        }

        // draws pieces
        if(board!=null) {
            for (int r = 0; r < board.size(); r++) {
                for (int c = 0; c < board.size(); c++) {
                    if (board.get(r).get(c).getHex() != null) {
                        drawPieceAtHex(canvas, board.get(r).get(c).getHex().getName(), r, c, board.get(r).get(c).getHex().getColor());

                    }
                }
            }
        }


        // display whos turn it is
        if (activePlayer != -1) {
            Paint turnPaint = new Paint();
            turnPaint.setColor(Color.BLUE);
            turnPaint.setTextSize(40f);
            turnPaint.setTextAlign(Paint.Align.LEFT);

            String turnText = "Turn: " + (activePlayer == 0 ? "WHITE" : "BLACK");
            canvas.drawText(turnText, 50f, 35f, turnPaint);
        }


        // debug overlay for coords
        //Paint box = new Paint(Paint.ANTI_ALIAS_FLAG);
        //box.setColor(Color.argb(180, 0, 0, 0));
        //canvas.drawRect(80, 60, 680, 170, box);

//        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
//        tp.setColor(Color.GREEN);
//        tp.setTextSize(40f);
//
//        // string lines
//        String line1 = String.format("Pixel: (%.1f, %.1f)", dbgX, dbgY);
//
//        Point hexTile;
//        if (dbgHexTile != null) {
//            // If dbgHexTile exists set to hex tile
//            hexTile = dbgHexTile;
//        } else {
//            //if null set to point 0,0
//            hexTile = new Point(0, 0);
//        }
//        String line2 = String.format("Board: (col=%d, row=%d)", hexTile.x, hexTile.y);
//        // overlay box coords
//        canvas.drawText(line1, 100f, 115f, tp);
//        canvas.drawText(line2, 100f, 160f, tp);
        //end of debugger

        //drawing highlight
        if(hexSelected) {
            drawHighlight(canvas);
        }
    }

    public void drawPieceAtHex(Canvas canvas, String namePiece, int row, int col, Hex.Color color) {
        Bitmap piece;
        if (color == Hex.Color.WHITE) {
            piece = pieces.get(namePiece);
        } else{
            piece = blackPieces.get(namePiece);
        }

        // find hex pos
        boolean isOdd = (row & 1) == 1;
        float y0 = startY + row * b;
        float xRowOffset = isOdd ? (s + a) : 0f;
        float x0 = startX + xRowOffset + col * (2f * colStep);

        // find center
        float centerX = x0 + s / 2f;
        float centerY = y0 + b;

        // scaling
        float scale = s * 1.5f / piece.getWidth();
        int newWidth = (int)(piece.getWidth() * scale);
        int newHeight = (int)(piece.getHeight() * scale);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(piece, newWidth, newHeight, true);

        // draw on hex
        float left = centerX - scaledBitmap.getWidth() / 2f;
        float top = centerY - scaledBitmap.getHeight() / 2f;
        canvas.drawBitmap(scaledBitmap, left, top, null);


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            tappedHex = mapPixelToHex(x, y);
            if(tappedHex == null){
                hexSelected = false;
                return true;
            }
            setDebugTap(x, y, tappedHex);
            hexSelected = true;
            invalidate();


        }
        return true;
    }

    public void drawHex(float x, float y, Paint color, Canvas canvas, int row, int col){

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


//        //draws coordinates at hex when it is drawn
//        Paint textPaint = new Paint();
//        textPaint.setColor(Color.GREEN);
//        textPaint.setTextSize(28f);
//        textPaint.setTextAlign(Paint.Align.CENTER);
//
//        // calculate center of hex
//        float centerX = x + s / 2f;
//        float centerY = y + b;
//
//        String text = col + "," + row;
//
//        canvas.drawText(text, centerX, centerY, textPaint);

    }
    public void drawHighlight(Canvas canvas){
            int y = tappedHex.x;
            int x = tappedHex.y;
        float y0 = startY + x * b;

        // odd TOTAL_ROWS shift right by (s + a)
        boolean isOdd = (x & 1) == 1;

        float xRowOffset = isOdd ? (s + a) : 0f;


            float x0 = startX + xRowOffset + y * (2f * colStep);

            drawHex(x0, y0, highlightColor, canvas, x, y);/*
            highlightColor.setStyle(Paint.Style.STROKE);
            // top
            canvas.drawLine(x, y, x + s, y, highlightColor);
            // top right
            canvas.drawLine(x + s, y, x + s + a, y + b, highlightColor);
            // bottom right
            canvas.drawLine(x + s + a, y + b, x + s, y + 2*b, highlightColor);
            // bottom
            canvas.drawLine(x + s, y + 2*b, x, y + 2*b, highlightColor);
            // bottom left
            canvas.drawLine(x, y + 2*b, x - a, y + b, highlightColor);
            // top left
            canvas.drawLine(x - a, y + b, x, y, highlightColor);


*/

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

    public ArrayList<ArrayList<HexSpace>> getBoard() {
        return board;
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

    public void setActivePlayer(int player) {
        this.activePlayer = player;
    }


}
