package game.hive.State;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class HSurfaceView extends SurfaceView {

    public HSurfaceView(Context context) {
        super(context);
        setWillNotDraw(false);
    }
    @Override
    public void onDraw(Canvas canvas) {

    }

}
