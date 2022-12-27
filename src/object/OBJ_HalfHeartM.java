package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_HalfHeartM extends SuperObject{
	
	public OBJ_HalfHeartM() {
		name = "HalfHeartM";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Half-HeartM.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
