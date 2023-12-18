package raspi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.sharedResources.StateMessageQueue;
import raspi.sharedResources.RunFlag;

import java.util.concurrent.TimeUnit;

@Component
public class StateMessageListener implements Runnable {
    private final StateMessageQueue queue;
    private final StateMessageHandler handler;
    private final RunFlag flag;
    private boolean runnable = true;

    @Autowired
    public StateMessageListener(StateMessageQueue queue, StateMessageHandler handler, RunFlag flag) {
        this.queue = queue;
        this.handler = handler;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (runnable) {
            synchronized (queue) {
                try {
                    queue.wait();
                    StateMessage message = queue.poll(3, TimeUnit.SECONDS);
                    runnable = handler.handle(message);
                    synchronized (flag) {
                        if (!runnable) {
                            flag.setFalse();
                        }
                        flag.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(this.toString() + "terminated");
    }
}
