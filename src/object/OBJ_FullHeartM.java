package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_FullHeartM extends SuperObject{
	
	public OBJ_FullHeartM() {
		name = "FullHeartM";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/FullHeartM.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
