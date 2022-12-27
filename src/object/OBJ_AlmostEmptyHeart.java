package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_AlmostEmptyHeart extends SuperObject{
	
	public OBJ_AlmostEmptyHeart() {
		name = "AlmostEmptyHeart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/1-4-Heart.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
