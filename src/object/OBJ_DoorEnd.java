package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_DoorEnd extends SuperObject{
	public OBJ_DoorEnd() {
		name = "DoorEnd";
		isUnique = false;
		pickable = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
