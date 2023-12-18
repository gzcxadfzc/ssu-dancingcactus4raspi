package raspi.sharedResources;

import org.springframework.stereotype.Component;

@Component
public class RunFlag {
    private boolean run = true;

    public void setFalse() {
        run = false;
    }

    public void setTrue() {
        run = true;
    }

    public boolean getValue() {
        return run;
    }
}
