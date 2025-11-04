package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class HSurfaceView extends SurfaceView {

    public HSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint hexColor = new Paint();
        hexColor.setColor(Color.RED);
        drawHex(1,2,500,500,hexColor, canvas);
    }

    public void drawHex(float x, float y, float x2, float y2, Paint color, Canvas canvas){

        canvas.drawLine(x, y, x2, y2, color);
    }
    public Point mapPixelToHex(int x, int y){

        return new Point(0,0);
    }

}
