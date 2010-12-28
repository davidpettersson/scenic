package scenic;

import scenic.node.Property;
import javax.microedition.khronos.opengles.GL10;
import scenic.node.Node;
import static android.opengl.GLES10.*;
import static android.opengl.GLU.*;

public class Camera extends Node {
    private final GL10 gl;
    private final int width;
    private final int height;

    public Camera(Core core, GL10 gl, int width, int height) {
        super(core);
        this.gl = gl;
        this.width = width;
        this.height = height;

        properties[Property.POS_X.ordinal()] = 0f;
        properties[Property.POS_Y.ordinal()] = 0f;
        properties[Property.POS_Z.ordinal()] = 9.5f;
        properties[Property.LOOK_AT_X.ordinal()] = 0f;
        properties[Property.LOOK_AT_Y.ordinal()] = 0f;
        properties[Property.LOOK_AT_Z.ordinal()] = 0f;
        properties[Property.UP_X.ordinal()] = 0f;
        properties[Property.UP_Y.ordinal()] = 1f;
        properties[Property.UP_Z.ordinal()] = 0f;
    }

    @Override
    public void render() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glViewport(0, 0, width, height);
        gluPerspective(gl, 45.0f, 1.0f * width / height, 1f, 1000f);
        gluLookAt(gl,
                properties[Property.POS_X.ordinal()],
                properties[Property.POS_Y.ordinal()],
                properties[Property.POS_Z.ordinal()], // 9 works great!
                properties[Property.LOOK_AT_X.ordinal()],
                properties[Property.LOOK_AT_Y.ordinal()],
                properties[Property.LOOK_AT_Z.ordinal()],
                properties[Property.UP_X.ordinal()],
                properties[Property.UP_Y.ordinal()],
                properties[Property.UP_Z.ordinal()]);

        glMatrixMode(GL_MODELVIEW);
    }
}
