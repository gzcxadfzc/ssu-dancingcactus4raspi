package raspi.utils.gpio;

import org.springframework.stereotype.Component;

@Component
public interface CactusController {

    void dance();

    void stop();

    void close();

}
