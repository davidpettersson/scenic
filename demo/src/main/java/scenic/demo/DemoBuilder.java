package scenic.demo;

import android.content.Context;
import java.util.Random;
import scenic.texture.TextureLoader;
import scenic.Core;
import scenic.node.Geometry;
import scenic.node.Node;
import scenic.node.Property;

public class DemoBuilder {
    private final TextureLoader textureLoader;
    private final Core core;
    private final int width;
    private final int height;
    
    public DemoBuilder(Context context, Core core, int width, int height) {
        this.textureLoader = new TextureLoader(context);
        this.core = core;
	this.width = width;
	this.height = height;
    }

    public Node build() {
        Node root = new Node(core);

        root.addChild(buildLogo());

        return root;
    }

    private Geometry buildSide() {
        Geometry side = new Geometry(core);
        side.setTexture(textureLoader.loadTexture(R.drawable.icon));
        return side;
    }

    private Node buildLogo() {

        Node cube = new Node(core);

        Geometry a = buildSide();
        a.setProperty(Property.TRANSLATE_Z, 0.5f);
        cube.addChild(a);

        Geometry b = buildSide();
        b.setProperty(Property.TRANSLATE_Z, -0.5f);
        b.setProperty(Property.ROTATE_Y, 180f);
        cube.addChild(b);

        Geometry c = buildSide();
        c.setProperty(Property.TRANSLATE_X, -0.5f);
        c.setProperty(Property.ROTATE_Y, -90f);
        cube.addChild(c);

        Geometry d = buildSide();
        d.setProperty(Property.TRANSLATE_X, 0.5f);
        d.setProperty(Property.ROTATE_Y, 90f);
        cube.addChild(d);

        Geometry e = buildSide();
        e.setProperty(Property.TRANSLATE_Y, 0.5f);
        e.setProperty(Property.ROTATE_X, -90f);
        cube.addChild(e);

        Geometry f = buildSide();
        f.setProperty(Property.TRANSLATE_Y, -0.5f);
        f.setProperty(Property.ROTATE_X, 90f);
        cube.addChild(f);

        cube.setName("logo");
        return cube;
    }
}
