package scenic;

public class RealClock implements Clock {

    public long now() {
        return System.currentTimeMillis();
    } 
}
