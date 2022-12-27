package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_EmptyHeart extends SuperObject{
	
	public OBJ_EmptyHeart() {
		name = "EmptyHeart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Empty-Heart.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
