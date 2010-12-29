package scenic.texture;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES10.*;

public class TextureLoader {

    private final static String tag = TextureLoader.class.getSimpleName();
    private final Map<Integer, Texture> textures = new HashMap<Integer, Texture>();
    private final Context context;

    public TextureLoader(Context context) {
        this.context = context;
    }

    public Texture loadTextureRepeat(int res) {
        return loadTexture(res, true, false);
    }

    public Texture loadTexture(int res) {
        return loadTexture(res, false, false);
    }

    private Texture loadTexture(int res, boolean repeat, boolean isBitmap) {
        if (!textures.containsKey(res)) {
            final ByteBuffer textureBuffer;
            final int textureSize;

            if (isBitmap) {
                Bitmap bitmap = loadBitmap(res);
                textureSize = bitmap.getHeight();
                textureBuffer = ByteBuffer.allocateDirect(textureSize * textureSize * 4);
                textureBuffer.order(ByteOrder.nativeOrder());
                bitmap.copyPixelsToBuffer(textureBuffer);
                textureBuffer.position(0);
            } else {
                // Load and prepare
                byte[] texture = loadPixmap(res);
                textureSize = (int) Math.sqrt(texture.length / 4);
                textureBuffer = ByteBuffer.allocateDirect(texture.length);
                textureBuffer.order(ByteOrder.nativeOrder());
                textureBuffer.put(texture);
                textureBuffer.position(0);
            }

            // Send to texture memory
            int[] ids = new int[1];
            glGenTextures(ids.length, ids, 0);
            int id = ids[0];

            glBindTexture(GL_TEXTURE_2D, id);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureSize, textureSize, 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            if (repeat) {
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            } else {
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            }
            textures.put(res, new Texture(id, textureSize, res));
        }

        return textures.get(res);
    }

    private Bitmap loadBitmap(int res) {
	return BitmapFactory.decodeResource(context.getResources(), res);
    }

    private byte[] loadPixmap(int res) {
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        try {
            is = context.getResources().openRawResource(res);
            while (is.available() > 0) {
                int n = is.read(buf);
                bos.write(buf, 0, n);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            Log.e(tag, "Caught unexpected exception", e);
            return null;
        }
    }
}
