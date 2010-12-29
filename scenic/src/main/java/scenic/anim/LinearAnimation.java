package scenic.anim;

import scenic.node.Node;
import scenic.node.Property;

public class LinearAnimation extends Animation {

    private boolean ease;

    public LinearAnimation(Node node, Property name) {
        super(node, name);
    }

    public void setEase(boolean ease) {
        this.ease = ease;
    }

    public boolean update(float timeElapsed) {
        totalElapsedTime += timeElapsed;
        float valueDelta = stopValue - startValue;

        if (totalElapsedTime >= 0) {
            float t = Math.min(totalElapsedTime / duration, 1f);

            float value;

            if (ease) {
                value = ease(t) * valueDelta + startValue;
            } else {
                value = linear(t) * valueDelta + startValue;
            }

            node.setProperty(name, value);
            return t >= 1.0f;
        } else {
            return false;
        }
    }

    private float ease(float t) {
        return (float) (0.5 * (Math.sin(t * Math.PI - 0.5 * Math.PI) + 1));
    }

    private float linear(float t) {
        return t;
    }

    public void setStartTime(float startTime) {
        totalElapsedTime -= startTime;
        duration += startTime;
    }
}
