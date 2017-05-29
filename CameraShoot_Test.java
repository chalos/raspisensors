import java.nio.ByteBuffer;
import java.util.Properties;

import picam.CameraShoot;

public class CameraShoot_Test {
	public static void main(String[] args) {
		CameraShoot cam = new CameraShoot();

		Properties prop = new Properties();

		prop.setProperty(CameraShoot.PATH, "/tmp/picam");
		prop.setProperty(CameraShoot.WIDTH, "800");
		prop.setProperty(CameraShoot.HEIGHT, "600");

		cam.setup(prop);

		ByteBuffer temp = cam.read();

		System.out.println(String.format( "Size of bytebuffer: %d", temp.array().length ));
	}
}