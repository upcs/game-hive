package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    int b = 2*((int) ((LENGTH) * Math.sin(0.523599)));
    

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

        for(int i = 0; i <= 10; i++) {
            drawHex(50+((2*a + LENGTH)*i), 50, 10, 10, hexColor, canvas);
            drawHex(50 + ((2*a + LENGTH)*i) + LENGTH + a, 50 + b, 10, 10, hexColor, canvas);
        }
        super.onDraw(canvas);
        /*if(pieces != null){
            canvas.drawBitmap(pieces.get("Beetle"),0,0,null);
        }*/
    }

    public void drawHex(float x, float y, float x2, float y2, Paint color, Canvas canvas){


        color.setStyle(Paint.Style.STROKE);
        color.setStrokeWidth(5);
        canvas.drawLine(x, y, x + LENGTH, y, color);// top
        canvas.drawLine(x + LENGTH, y, x + (LENGTH + a), b + y, color);//top right
        canvas.drawLine( x + (LENGTH + a), b + y, b + x , (2*b) + y ,color); // bottom right
        canvas.drawLine(x, y, x - a  , b + y, color); // top left
        canvas.drawLine(x, (2*b) + y, x - a, b + y, color); //bottom left
        canvas.drawLine(x + LENGTH,(2*b) + y, x, (2*b) + y, color); // bottom line






    }
    public Point mapPixelToHex(int x, int y){

        return new Point(0,0);
    }

}
