package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_FullHeart extends SuperObject{
	
	public OBJ_FullHeart() {
		name = "FullHeart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/FullHeart.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
