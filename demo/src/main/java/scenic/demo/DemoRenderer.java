package scenic.demo;

import scenic.node.Property;
import scenic.node.Node;
import scenic.Scene;
import scenic.Camera;
import scenic.Core;
import scenic.RealClock;
import scenic.anim.LinearAnimation;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import static android.opengl.GLES10.*;

public class DemoRenderer implements Renderer {

    private final static String tag = DemoRenderer.class.getSimpleName();
    private final Context context;
    private Scene scene;

    public DemoRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        scene.refresh();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Global Open GL settings (TODO: move to graph?)
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        // Scene graph
        Core core = new Core();
        core.setClock(new RealClock());

        DemoBuilder builder =
                new DemoBuilder(context, core, width, height);

        Camera camera = new Camera(core, gl, width, height);
        camera.setProperty(Property.POS_Z, 4f);
        
        scene = new Scene(core);
        scene.setRoot(builder.build());
        scene.setCamera(camera);
    }

    public void submitAnimations() {
        
        if (scene.hasOngoingAnimations()) {
            return;
        }
        
        Node logo = scene.findNode("logo");

        LinearAnimation spin = new LinearAnimation(logo, Property.ROTATE_X);
        spin.setDuration(10);
        spin.setStartValue(0f);
        spin.setStopValue(360f);
        spin.setEase(true);
        scene.submitAnimation(spin);

        spin = new LinearAnimation(logo, Property.ROTATE_Y);
        spin.setDuration(10);
        spin.setStartValue(0f);
        spin.setStopValue(180f);
        spin.setEase(true);
        scene.submitAnimation(spin);

        spin = new LinearAnimation(logo, Property.ROTATE_Z);
        spin.setDuration(10);
        spin.setStartValue(0f);
        spin.setStopValue(360f);
        spin.setEase(true);
        scene.submitAnimation(spin);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }
}
