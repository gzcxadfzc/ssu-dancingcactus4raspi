package raspi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raspi.CommandLineProvider;
import raspi.sharedResources.RunFlag;
import raspi.StateMessageListener;
import raspi.StateProvider;

@Service
public class Controller {
    private final StateProvider provider;
    private final StateMessageListener listener;
    private final RunFlag flag;

    @Autowired
    public Controller(CommandLineProvider provider, StateMessageListener listener, RunFlag flag) {
        this.provider = provider;
        this.listener = listener;
        this.flag = flag;
    }

    public void run() {
        Thread stateProvider = new Thread(provider);
        Thread stateConsumer = new Thread(listener);
        stateProvider.start();
        stateConsumer.start();
        boolean runnable = true;
        while(runnable) {
            synchronized(flag) {
                try{
                    flag.wait();
                    runnable = flag.getValue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(this + "terminated");
    }
}
