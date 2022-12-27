package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Shield extends SuperObject{
	
	public OBJ_Shield() {
		name = "Shield";
		isUnique = false;
		pickable = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/shield_wood.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
