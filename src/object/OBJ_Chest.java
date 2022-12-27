package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
	public OBJ_Chest() {
		name = "Chest";
		isUnique = true;
		pickable = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = false;
	}
}