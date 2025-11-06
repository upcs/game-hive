package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import game.hive.R;


public class HSurfaceView extends SurfaceView {
    //private Bitmap myImageBitmap;
    private HashMap<String, Bitmap> pieces;
    public HSurfaceView(Context context) {
        super(context);
        setWillNotDraw(false);
        pieces = new HashMap<>();
        pieces.put("Beetle", BitmapFactory.decodeResource(getResources(), R.drawable.beetle));
        pieces.put("Grasshopper", BitmapFactory.decodeResource(getResources(), R.drawable.grasshopper));
        pieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.hive_black_queen_logo_hd);
        pieces.put("Delete", BitmapFactory.decodeResource(getResources(), R.drawable.purple_delete_button);
        pieces.put("QueenBee", BitmapFactory.decodeResource(getResources(), R.drawable.queenbee);
        pieces.put("Ant", BitmapFactory.decodeResource(getResources(), R.drawable.soldierant));
        pieces.put("Spider", BitmapFactory.decodeResource(getResources(), R.drawable.spider));
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint hexColor = new Paint();
        hexColor.setColor(Color.RED);
        drawHex(1,2,10,10,hexColor, canvas);
        super.onDraw(canvas);
        if(myImageBitmap != null){
            canvas.drawBitmap(pieces.get("Beetle"),0,0,null);
        }
    }

    public void drawHex(float x, float y, float x2, float y2, Paint color, Canvas canvas){
        canvas.drawLine(x, y, x2, y2, color);
    }

}
