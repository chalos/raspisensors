package dht11;

import java.util.Properties;
import raspberrypi.Sensor;

public class DHT11 implements Sensor<Float> {
	final public static String GPIO_PIN = "gpio_pin";
	final public static String CHOICE = "choice";

	private int pin = 0;

	final public static String TEMP = "0";
	final public static String HUMI = "1";

	private int choice = 0;

	static {
		System.loadLibrary("DHT11");
	}

	public Float read() {
		float[] values = readData(this.pin);

		int stopCounter = 0;

		while (!isValid(values)) {
			stopCounter++;
			if (stopCounter > 10) {
				throw new RuntimeException("Sensor return invalid data 10 times:" + values[0] + ", " + values[1]);
			}
			values = readData(this.pin);
		}

		return values[this.choice];
	}

	public void write(Float value) {
		return;
	}

	public void setup(Properties prop) {
		this.pin = Integer.valueOf(prop.getProperty(GPIO_PIN)).intValue();

		this.choice = Integer.valueOf(prop.getProperty(CHOICE)).intValue();
		switch(this.choice) {
			case 0:
			this.choice = 0;
			break;
			case 1:
			this.choice = 1;
			break;
			default:
			this.choice = 0;
		}
	}

	private boolean isValid(float[] data) {
		return data[0] > 0 && data[0] < 100 && data[1] > 0 && data[1] < 100;
	}

	public native float[] readData(int pin);
}