package scenic;

import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Core {
    private final static String tag = Core.class.getSimpleName();
    private Clock clock;

    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    public Clock getClock() {
        return clock;
    }

    public PrintStream getDebug() {
        final StringBuffer buffer = new StringBuffer();
        return new PrintStream(new OutputStream() {
            
            @Override
            public void write(int b) throws IOException {
                buffer.append((char) b);
                
                if (b == '\n') {
                    Log.d(tag, buffer.toString());
                    buffer.delete(0, buffer.toString().length());
                }
            }
        }, true);
    }
}
