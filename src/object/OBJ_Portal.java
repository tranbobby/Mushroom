package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Portal extends SuperObject{
	public OBJ_Portal() {
		name = "Portal";
		isUnique = true;
		pickable = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Portal.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = false;
	}
}