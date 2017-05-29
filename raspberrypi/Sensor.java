package raspberrypi;

import java.util.Properties;

public interface Sensor <T> {
	public T read();
	public void write(T value);
	public void setup(Properties prop);
}