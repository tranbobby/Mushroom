package main;

import entity.Living;
import entity.Player;
import object.OBJ_Door;
import object.OBJ_DoorEnd;
import object.OBJ_Key;
import object.SuperObject;

public class CollisionChecker {
	public GamePanel gp;
	
	// This class is prone to change a lot to match our tile system. It was inspired 
	// by a game which hasn't tiles, so it's not optimal for us.

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	// Checks next tile destination, and put entity.collisionOn at true depending 
	// that tile's collision, monster presence or item collision.
	public void checkTile(Living entity) {
		
		int entityLeftCol = entity.x/GamePanel.TILE_SIZE;
		int entityRightCol = entity.x/GamePanel.TILE_SIZE;
		int entityTopRow = entity.y/GamePanel.TILE_SIZE;
		int entityBottomRow = entity.y/GamePanel.TILE_SIZE;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entity.y - entity.speed)/GamePanel.TILE_SIZE;
			if(gp.map.grid[entityLeftCol][entityTopRow].collision == true || gp.map.grid[entityLeftCol][entityTopRow].whoIsHere != null) { 
				entity.collisionOn = true;
			}else if (gp.map.grid[entityLeftCol][entityTopRow].whatIsHere != null 
					&& gp.map.grid[entityLeftCol][entityTopRow].whatIsHere instanceof OBJ_Door){
				if (entity instanceof Player) {
					OBJ_Key premiereCle = null;
					for (SuperObject item : gp.player.inventaire) {
						if (item instanceof OBJ_Key) {
							premiereCle = (OBJ_Key)item;
							break;
						}			
					}
					if (premiereCle != null) {
						entity.collisionOn = false;
					}
					else {
						entity.collisionOn = true;
						gp.ui.messageType = "Key";
						gp.ui.showMessage("You need a key !");
					}
				}
				else {
					entity.collisionOn = true;
				}
			} break;		
				
		case "down":
			entityBottomRow = (entity.y + entity.speed)/GamePanel.TILE_SIZE;
			if(gp.map.grid[entityLeftCol][entityBottomRow].collision == true || gp.map.grid[entityLeftCol][entityBottomRow].whoIsHere != null) {
				entity.collisionOn = true;
				}else if (gp.map.grid[entityLeftCol][entityBottomRow].whatIsHere != null 
						&& gp.map.grid[entityLeftCol][entityBottomRow].whatIsHere instanceof OBJ_Door){
					if (entity instanceof Player) {
						OBJ_Key premiereCle = null;
						for (SuperObject item : gp.player.inventaire) {
							if (item instanceof OBJ_Key) {
								premiereCle = (OBJ_Key)item;
								break;
							}			
						}
						if (premiereCle != null) {
							entity.collisionOn = false;
						}
						else {
							entity.collisionOn = true;
							gp.ui.messageType = "Key";
							gp.ui.showMessage("You need a key !");
						}
					}
					else {
						entity.collisionOn = true;
					}
				} break;
				
		case "left":
			entityLeftCol = (entity.x - entity.speed)/GamePanel.TILE_SIZE;
			if(gp.map.grid[entityLeftCol][entityTopRow].collision == true || gp.map.grid[entityLeftCol][entityTopRow].whoIsHere != null) {
				entity.collisionOn = true;
				}else if (gp.map.grid[entityLeftCol][entityTopRow].whatIsHere != null 
						&& gp.map.grid[entityLeftCol][entityTopRow].whatIsHere instanceof OBJ_Door){
					if (entity instanceof Player) {
						OBJ_Key premiereCle = null;
						for (SuperObject item : gp.player.inventaire) {
							if (item instanceof OBJ_Key) {
								premiereCle = (OBJ_Key)item;
								break;
							}			
						}
						if (premiereCle != null) {
							entity.collisionOn = false;
						}
						else {
							entity.collisionOn = true;
							gp.ui.messageType = "Key";
							gp.ui.showMessage("You need a key !");
						}
					}
					else {
						entity.collisionOn = true;
					}
				} break;
				
		case "right":
			entityRightCol = (entity.x + entity.speed)/GamePanel.TILE_SIZE;
			if(gp.map.grid[entityRightCol][entityTopRow].collision == true || gp.map.grid[entityRightCol][entityTopRow].whoIsHere != null) {
				entity.collisionOn = true;
				}else if (gp.map.grid[entityRightCol][entityTopRow].whatIsHere != null 
						&& gp.map.grid[entityRightCol][entityTopRow].whatIsHere instanceof OBJ_Door){
					if (entity instanceof Player) {
						OBJ_Key premiereCle = null;
						for (SuperObject item : gp.player.inventaire) {
							if (item instanceof OBJ_Key) {
								premiereCle = (OBJ_Key)item;
								break;
							}			
						}
						if (premiereCle != null) {
							entity.collisionOn = false;
						}
						else {
							entity.collisionOn = true;
							gp.ui.messageType = "Key";
							gp.ui.showMessage("You need a key !");
						}
					}
					else {
						entity.collisionOn = true;
					}
				} break;
		}
	}
	
	//Checks objects on entity tile (differentiate player from monster)
	public int checkObject2(Living entity, boolean player) {
		// This took example of another game which doesn't use tiles. It will be changed
		// to match our model and will be far better optimized.
		int index = 999;

		for (SuperObject objectChecked : gp.objectsOnGround) {
			if (objectChecked != null) {
				objectChecked.objScreenX = objectChecked.x-(gp.player.x/GamePanel.SCREEN_WIDTH)*GamePanel.MAX_SCREEN_COL*GamePanel.TILE_SIZE;
				objectChecked.objScreenY = objectChecked.y-(gp.player.y/GamePanel.SCREEN_HEIGHT)*GamePanel.MAX_SCREEN_ROW*GamePanel.TILE_SIZE;
				// get entity solidArea position
				entity.solidArea.x = entity.screenX;
				entity.solidArea.y = entity.screenY;
				
				// get item solidArea position
				objectChecked.solidArea.x = objectChecked.objScreenX;
				objectChecked.solidArea.y = objectChecked.objScreenY;
				
				switch(entity.direction) {
				case "up":
					if (entity.solidArea.intersects(objectChecked.solidArea)) {//Checks if both solidArea intersects
						if (objectChecked.collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = gp.objectsOnGround.indexOf(objectChecked);
						}
					}
					break;
				case "down":
					if (entity.solidArea.intersects(objectChecked.solidArea)) {
						if (objectChecked.collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = gp.objectsOnGround.indexOf(objectChecked);
						}
					}
					break;
				case "left":
					if (entity.solidArea.intersects(objectChecked.solidArea)) {
						if (objectChecked.collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = gp.objectsOnGround.indexOf(objectChecked);
						}
					}
					break;
				case "right":
					if (entity.solidArea.intersects(objectChecked.solidArea)) {
						if (objectChecked.collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = gp.objectsOnGround.indexOf(objectChecked);
						}
					}
					break;
				}
				//Reset entity and item solidArea
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				objectChecked.solidArea.x = objectChecked.solidAreaDefaultX;
				objectChecked.solidArea.y = objectChecked.solidAreaDefaultY;
			}
		}

		return index;
	}
	
	//NOT USED CURRENTLY
	
//	//Checks if next tile Object is a solid item (like a door) and if player reply to item requirement to move
//	public void checkSolidItem(Living entity, boolean player, SuperObject o) {
//		if (o instanceof SuperObject) {
//			if (o.equals(gp.listOfAvailableObjects.get(3))) {
//				if (player == true) {
//					if (gp.player.inventaire.indexOf(gp.listOfAvailableObjects.get(1)) != -1){
//						gp.player.collisionOn = false;
//						}
//					else {
//						gp.player.collisionOn = true;
//						}
//					}
//				else {
//					entity.collisionOn = true;
//					}
//				}
//			}
//		}
//	
	}
