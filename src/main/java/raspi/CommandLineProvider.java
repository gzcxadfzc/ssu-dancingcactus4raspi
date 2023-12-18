package raspi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raspi.sharedResources.StateMessageQueue;
import raspi.sharedResources.RunFlag;

import java.util.Scanner;

@Service
public class CommandLineProvider implements StateProvider {
    private static final String BLANK = " ";
    private static final String NULL_VALUE = "";
    private final Scanner scanner = new Scanner(System.in);
    private final StateMessageQueue queue;
    private final RunFlag flag;

    @Autowired
    public CommandLineProvider(StateMessageQueue queue, RunFlag flag) {
        this.queue = queue;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (checkRunnable()) {
            String input = scanner.nextLine();
            StateMessage message = getStateMessageFrom(input);
            queue.offer(message);
            synchronized (queue) {
                queue.notifyAll();
            }
            synchronized (flag) {
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(this.toString() + "terminated");
    }

    private boolean checkRunnable() {
        synchronized (flag) {
            return flag.getValue();
        }
    }

    private StateMessage getStateMessageFrom(String input) {
        String stateDescription = input.split(BLANK)[0];
        String argument = input.replace(stateDescription + BLANK, NULL_VALUE);
        State inputState = State.find(stateDescription);
        String value = NULL_VALUE;
        if (inputState.isValueRequired()) {
            value = argument;
        }
        return new StateMessageImpl(inputState, value);
    }
}
