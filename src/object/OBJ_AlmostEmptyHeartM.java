package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_AlmostEmptyHeartM extends SuperObject{
	
	public OBJ_AlmostEmptyHeartM() {
		name = "AlmostEmptyHeartM";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/1-4-HeartM.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
