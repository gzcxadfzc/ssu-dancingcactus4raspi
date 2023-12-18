package raspi.sharedResources;

import org.springframework.stereotype.Component;
import raspi.StateMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class StateMessageQueue {
    private BlockingQueue<StateMessage> queue;
    public StateMessageQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public boolean offer(StateMessage message) {
        return queue.offer(message);
    }

    public StateMessage poll(int time, TimeUnit timeunit) throws InterruptedException {
        return queue.poll(time, timeunit);
    }
}
