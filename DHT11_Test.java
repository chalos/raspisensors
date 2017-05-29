
import java.util.Properties;
import dht11.DHT11;

public class DHT11_Test {
	public static void main(String[] args) {
		DHT11 sensors = new DHT11();

		Properties prop = new Properties();

		prop.setProperty(DHT11.GPIO_PIN, "0");
		prop.setProperty(DHT11.CHOICE, DHT11.TEMP);

		sensors.setup(prop);
		Float temp = sensors.read();

		prop.setProperty(DHT11.CHOICE, DHT11.HUMI);
		sensors.setup(prop);
		Float humi = sensors.read();

		System.out.println(String.format("Temp: %f, Humi: %f", temp, humi));
	}
}