package raspi.utils.gpio;

import org.springframework.stereotype.Component;
import raspi.utils.gpio.CactusController;
import com.pi4j.io.gpio.*;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;

@Component
public class CactusGpio implements CactusController {

	private final GpioController gpio;
	private final GpioPinDigitalOutput pinOut;
	private final Logger logger;

	public CactusGpio() {
		this.gpio = GpioFactory.getInstance();
		this.pinOut = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
		logger = Logger.getLogger(CactusGpio.class.getName());
	}

	@Override
	public void dance() {
		logger.info("start dancing..");
		pinOut.high();
	}

	@Override
	public void stop() {
		logger.info("stop dancing...");
		pinOut.low();
	}

	@Override
	@PreDestroy
	public void close() {
		logger.info("gpio closed..");
		gpio.shutdown();
	}
	
}
