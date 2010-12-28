package scenic.anim;

import scenic.node.Node;
import scenic.node.Property;

public abstract class Animation {

    protected float duration;
    protected final Property name;
    protected final Node node;
    protected float startValue;
    protected float stopValue;
    protected float totalElapsedTime;

    public Animation(Node node, Property name) {
        this.node = node;
        this.name = name;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    public void setStopValue(float stopValue) {
        this.stopValue = stopValue;
    }

    public abstract boolean update(float elapsedTime);
}
