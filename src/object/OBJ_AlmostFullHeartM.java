package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_AlmostFullHeartM extends SuperObject{
	
	public OBJ_AlmostFullHeartM() {
		name = "AlmostFullHeartM";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/3-4-HeartM.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
