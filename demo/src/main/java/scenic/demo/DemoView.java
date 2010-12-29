package scenic.demo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class DemoView extends GLSurfaceView {

    private final DemoRenderer renderer;

    public DemoView(Context context) {
        super(context);
        renderer = new DemoRenderer(context);
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        renderer.submitAnimations();
        return true;
    }


}
