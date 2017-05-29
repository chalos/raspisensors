package picam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Properties;
import javax.imageio.ImageIO;

import com.hopding.jrpicam.enums.AWB;
import com.hopding.jrpicam.enums.DRC;
import com.hopding.jrpicam.enums.Encoding;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import com.hopding.jrpicam.RPiCamera;

import raspberrypi.Sensor;

public class CameraShoot implements Sensor<ByteBuffer> {
	final public static String PATH = "out";
	final public static String WIDTH = "w";
	final public static String HEIGHT = "h";

	private String outpath = null;
	private RPiCamera cam = null;
	private int width = 800;
	private int height = 600;

	public ByteBuffer read() {
		if(this.cam == null)
			return null;

		try {
			File f = this.cam.takeStill("cache.png", this.width, this.height);

			BufferedImage buffImg = this.cam.takeBufferedStill(this.width, this.height);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( buffImg, "png", baos );
			baos.flush();
			byte[] byteArray = baos.toByteArray();
			baos.close();

			System.out.println(String.format("File path: %s", f.getAbsolutePath()));
			return ByteBuffer.wrap(byteArray);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void write(ByteBuffer value) {
		return;
	}

	public void setup(Properties prop) {
		try {
			this.outpath = prop.getProperty(PATH, "/tmp/rpicamera");
			this.cam = new RPiCamera(this.outpath);
		} catch(FailedToRunRaspistillException e) {
			e.printStackTrace();
		}

		this.cam.setAWB(AWB.AUTO)
			.setDRC(DRC.OFF)
			.setContrast(100)
			.setSharpness(100)
			.setQuality(100)
			.setTimeout(1000)
			.turnOnPreview()
			.setEncoding(Encoding.PNG); // Change encoding of images to PNG

		this.width = Integer.valueOf(prop.getProperty(WIDTH, "800")).intValue();
		this.height = Integer.valueOf(prop.getProperty(HEIGHT, "600")).intValue();
	}
}