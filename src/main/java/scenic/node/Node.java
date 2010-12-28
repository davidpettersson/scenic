package scenic.node;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import scenic.Core;
import static android.opengl.GLES10.*;

public class Node {

    private final static String tag = Node.class.getSimpleName();
    private final List<Node> children = new ArrayList<Node>();
    protected final float[] properties = new float[Property.values().length];
    private final Core core;
    private String name;

    public Node(Core core) {
        this.core = core;

        properties[Property.TRANSLATE_X.ordinal()] = 0f;
        properties[Property.TRANSLATE_Y.ordinal()] = 0f;
        properties[Property.TRANSLATE_Z.ordinal()] = 0f;
        properties[Property.ROTATE_X.ordinal()] = 0f;
        properties[Property.ROTATE_Y.ordinal()] = 0f;
        properties[Property.ROTATE_Z.ordinal()] = 0f;
        properties[Property.SCALE_X.ordinal()] = 1f;
        properties[Property.SCALE_Y.ordinal()] = 1f;
        properties[Property.SCALE_Z.ordinal()] = 1f;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void removeAllChildren() {
        children.clear();
    }

    public void update() {
        for (Node child : children) {
            child.update();
        }
    }

    public void setProperty(Property property, float value) {
        properties[property.ordinal()] = value;
    }

    public float getProperty(Property property) {
        return properties[property.ordinal()];
    }

    public final void preRender() {
        if (properties[Property.TRANSLATE_X.ordinal()] != 0f
                || properties[Property.TRANSLATE_Y.ordinal()] != 0f
                || properties[Property.TRANSLATE_Z.ordinal()] != 0f) {
            glTranslatef(
                    properties[Property.TRANSLATE_X.ordinal()],
                    properties[Property.TRANSLATE_Y.ordinal()],
                    properties[Property.TRANSLATE_Z.ordinal()]);
        }

        if (properties[Property.ROTATE_X.ordinal()] != 0f) {
            glRotatef(properties[Property.ROTATE_X.ordinal()], 1f, 0f, 0f);
        }

        if (properties[Property.ROTATE_Y.ordinal()] != 0f) {
            glRotatef(properties[Property.ROTATE_Y.ordinal()], 0f, 1f, 0f);
        }

        if (properties[Property.ROTATE_Z.ordinal()] != 0f) {
            glRotatef(properties[Property.ROTATE_Z.ordinal()], 0f, 0f, 1f);
        }

        if (properties[Property.SCALE_X.ordinal()] != 1f
                || properties[Property.SCALE_Y.ordinal()] != 1f
                || properties[Property.SCALE_Z.ordinal()] != 1f) {
            glScalef(
                    properties[Property.SCALE_X.ordinal()],
                    properties[Property.SCALE_Y.ordinal()],
                    properties[Property.SCALE_Z.ordinal()]);
        }
    }

    public void render() {
        for (Node child : children) {
            glPushMatrix();
            child.preRender();
            child.render();
            glPopMatrix();
        }
    }

    protected Core getCore() {
        return core;
    }

    public void printScene(int level) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }

        if (children.isEmpty()) {
            Log.d(tag, builder.toString() + toString());
        } else {
            Log.d(tag, builder.toString() + toString() + " {");
            for (Property p : Property.values()) {
                Log.d(tag, builder.toString() + "  " + p.name() + " = " + properties[p.ordinal()]);
            }
            for (Node child : children) {
                child.printScene(level + 1);
            }

            Log.d(tag, builder.toString() + "}");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format("<%s>", name);
    }

    public Node findNode(String name) {
        if (this.name != null) {
            if (this.name.equals(name)) {
                return this;
            }
        }

        Node result = null;

        for (Node child : children) {
            result = child.findNode(name);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
