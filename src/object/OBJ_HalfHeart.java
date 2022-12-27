package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_HalfHeart extends SuperObject{
	
	public OBJ_HalfHeart() {
		name = "HalfHeart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/HalfHeart.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
