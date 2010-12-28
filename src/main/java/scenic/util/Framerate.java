package scenic.util;

import android.util.Log;

public class Framerate {
    private final static String tag = Framerate.class.getSimpleName();
    private final static int interval = 50;
    private int frames;
    private long last;

    public void refresh() {
        frames++;

        if (frames % interval == 0) {
            long now = System.currentTimeMillis();
            float delta = now - last;
            last = now;
            float fps = ((float) interval) / (delta / 1000.f);
            Log.d(tag, "fps = " + fps);
        }
    }
}
