package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Sword extends SuperObject{
	
	public OBJ_Sword() {
		name = "Sword";
		isUnique = false;
		pickable = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/sword_normal.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
