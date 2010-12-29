package scenic;

import java.util.Stack;
import java.util.List;
import java.util.LinkedList;
import scenic.anim.LinearAnimation;
import scenic.node.Node;
import scenic.util.Framerate;
import static android.opengl.GLES10.*;

public class Scene {

    private final List<LinearAnimation> animations = new LinkedList<LinearAnimation>();
    private Framerate framerate = new Framerate();
    private Core core;
    private Node root;
    private Camera camera;
    private long lastRefresh;

    public Scene(Core core) {
        this.core = core;
        this.lastRefresh = System.currentTimeMillis();
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void refresh() {

        updateAnimations();
        camera.update();
        root.update();

        glLoadIdentity();
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.preRender();
        camera.render();

        root.preRender();
        root.render();

        framerate.refresh();
    }

    private synchronized void updateAnimations() {
        long now = System.currentTimeMillis();
        float timeElapsed = (float) (now - lastRefresh) / 1000f;
        lastRefresh = now;

        Stack<LinearAnimation> completed = new Stack<LinearAnimation>();
        for (LinearAnimation animation : animations) {
            if (animation.update(timeElapsed)) {
                completed.push(animation);
            }
        }

        for (LinearAnimation a : completed) {
            animations.remove(a);
        }
    }

    public void printScene() {
        root.printScene(0);
    }

    public Node findNode(String name) {
        if (name.equals("camera")) {
            return camera;
        } else {
            return root.findNode(name);
        }
    }

    public void submitAnimation(LinearAnimation pa) {
        animations.add(pa);
    }

    public boolean hasOngoingAnimations() {
        return animations.size() > 0;
    }
}
