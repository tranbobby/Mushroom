package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_AlmostFullHeart extends SuperObject{
	
	public OBJ_AlmostFullHeart() {
		name = "AlmostFullHeart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/3-4-Heart.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
