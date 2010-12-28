package scenic.node;

import scenic.Core;
import scenic.texture.Texture;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES10.*;

public class Geometry extends Node {

    private final static float[] vertices = new float[]{
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f,
        0.5f, -0.5f, 0.0f
    };

    private final static float[] colors = new float[]{
        0.0f, 0.0f, 1.0f, 1.0f,
        0.0f, 1.0f, 1.0f, 1.0f,
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
    };

    private final static float[] originalTexCoords = new float[]{
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 0.0f,
        1.0f, 1.0f,
        1.0f, 0.0f,
        0.0f, 1.0f,
    };

    private final static short[] indices = new short[]{
        0, 1, 2,
        3, 2, 1
    };

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer indexBuffer;
    private final FloatBuffer colorBuffer;
    private FloatBuffer texCoordBuffer;
    private Texture texture;
    private float[] texCoords;
    private boolean enableColor;

    public Geometry(Core core) {
        super(core);
        
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

    }

    public void setTexture(Texture texture, float count) {
        this.texture = texture;
        makeNewTexCoord(count);
    }

    public void setTexture(Texture texture) {
        setTexture(texture, 1f);
    }

    @Override
    public void render() {

        if (texture != null) {
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);

        if (enableColor) {
            glEnableClientState(GL_COLOR_ARRAY);
            glColorPointer(4, GL_FLOAT, 0, colorBuffer);
        }

        if (texture != null) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glTexCoordPointer(2, GL_FLOAT, 0, texCoordBuffer);
        }

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_SHORT, indexBuffer);

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        if (enableColor) {
            glDisableClientState(GL_COLOR_ARRAY);
        }

        glDisableClientState(GL_VERTEX_ARRAY);

        super.render();
    }

    private void makeNewTexCoord(float k) {
        texCoords = new float[originalTexCoords.length];

        for (int i = 0; i < texCoords.length; i++) {
            texCoords[i] = originalTexCoords[i] * k;
        }

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texCoordBuffer = tbb.asFloatBuffer();
        texCoordBuffer.put(texCoords);
        texCoordBuffer.position(0);
    }
}
