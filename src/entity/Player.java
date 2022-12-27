package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Key;
import object.SuperObject;
import tile.Tile;

public class Player extends Living{
	KeyHandler keyH;
	public int hasKey = 0;
	public ArrayList<SuperObject> inventaire = new ArrayList<SuperObject>();

	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.height = GamePanel.TILE_SIZE;
		solidArea.width = GamePanel.TILE_SIZE;
	}
	
	public void setDefaultValues() {
		// Starting position
		initTileX = 2;
		initTileY = 2;
		x = GamePanel.TILE_SIZE*initTileX;
		y = GamePanel.TILE_SIZE*initTileY;
		screenX = x % GamePanel.SCREEN_WIDTH;
		screenY = y % GamePanel.SCREEN_HEIGHT;
		speed =GamePanel.TILE_SIZE;
		direction="down"; // Any direction is fine.
		hpMax = 100;
		hp = 100;
		attack = 5;
		defense = 0;
		dodge = 5; // This is a percentage, so 5% chance to dodge an attack. 
	}
	
	public void getPlayerImage() {
		up1 = setupSprite("/player/boy_up_1.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		up2 = setupSprite("/player/boy_up_2.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		down1 = setupSprite("/player/boy_down_1.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		down2 = setupSprite("/player/boy_down_2.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		left1 = setupSprite("/player/boy_left_1.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		left2 = setupSprite("/player/boy_left_2.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		right1 = setupSprite("/player/boy_right_1.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		right2 = setupSprite("/player/boy_right_2.png", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setupSprite("/player/boy_attack_up_1.png", GamePanel.TILE_SIZE, 2*GamePanel.TILE_SIZE);
		attackUp2 = setupSprite("/player/boy_attack_up_2.png", GamePanel.TILE_SIZE, 2*GamePanel.TILE_SIZE);
		attackDown1 = setupSprite("/player/boy_attack_down_1.png", GamePanel.TILE_SIZE, 2*GamePanel.TILE_SIZE);
		attackDown2 = setupSprite("/player/boy_attack_down_2.png", GamePanel.TILE_SIZE, 2*GamePanel.TILE_SIZE);
		attackLeft1 = setupSprite("/player/boy_attack_left_1.png", 2*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		attackLeft2 = setupSprite("/player/boy_attack_left_2.png", 2*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		attackRight1 = setupSprite("/player/boy_attack_right_1.png", 2*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		attackRight2 = setupSprite("/player/boy_attack_right_2.png", 2*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
	}
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || 
				keyH.rightPressed == true || keyH.waitPressed == true && keyH.counter == 1) {
		
			gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE].whoIsHere = null;

			if (keyH.upPressed == true) {
				direction ="up";
			}
			else if (keyH.downPressed == true) {
				direction="down";
			}
			else if (keyH.leftPressed == true) {
				direction="left";
			}
			else if (keyH.rightPressed == true) {
				direction="right";
			}
			
			if (keyH.waitPressed == true) {
				hasMove = true;
				hasAttack = true;
				keyH.counter++;
				spriteCounter+=12;
			}
			
			// Checks next Tile collision (tile, monster, item)
			collisionOn = false;
			gp.cChecker.checkTile(this);			
			
			// Checks next Tile item status
			int objIndex2 = gp.cChecker.checkObject2(this, true); // True here means it's a player.
			pickUpObject2(objIndex2);
			
			
			if (collisionOn == false && keyH.counter == 1) {
			// Player only move when collisionOn == false.
				keyH.counter++;
				switch(direction) {
				case "up": {
					y -= speed; screenY = y % GamePanel.SCREEN_HEIGHT;
					nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)-1];
					break;
				}
				case "down":{
					y += speed; screenY = y % GamePanel.SCREEN_HEIGHT; 
					nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)+1];break;
				}
				case "left":{
					x -= speed; screenX = x % GamePanel.SCREEN_WIDTH; 
					nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)-1][(y/GamePanel.TILE_SIZE)];break;
				}
				case "right":{
					x += speed; screenX = x % GamePanel.SCREEN_WIDTH; 
					nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)+1][(y/GamePanel.TILE_SIZE)]; 	break;
				}
					}
				hasMove = true;
				}
			
			else if (collisionOn && keyH.counter == 1){
			// The player doesn't move.
				switch(direction) {
				case "up": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)-1]; break;
				case "down": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)+1]; break;
				case "left": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)-1][(y/GamePanel.TILE_SIZE)]; break;
				case "right": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)+1][(y/GamePanel.TILE_SIZE)]; break;
				}
				keyH.counter++;
				hasMove = true;
				// If the player doesn't move because a monster is on next tile, he attacks.
				if (nextCase != null && nextCase.whoIsHere != null && hasAttack == false) {
					attacking = true;
					attack(nextCase.whoIsHere);
					hasAttack = true;
				}
			}
			
			spriteCounter++;
			gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE].whoIsHere = this;
			
			if (spriteCounter > 12) {
				if (spriteNum == 2) {
					spriteNum = 1;
				}
				else if (spriteNum == 1) {
					spriteNum = 2;
				}
				spriteCounter = 0;
			}

		}
		if (attacking == true) {
			spriteAttackCounter ++;

			if (spriteAttackCounter <= 5) {spriteNum = 1;}
			if (spriteAttackCounter > 5 && spriteCounter <= 25) {spriteNum = 2;}
			if (spriteAttackCounter> 25) {
				spriteNum = 1;
				spriteAttackCounter = 0;
				this.attacking = false;
			}
		}

	}

	public void pickUpObject2(int i) {
		Tile playersTile = gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE];
		if (i != 999) {
			SuperObject objectChecked = gp.objectsOnGround.get(i);
			
			if (objectChecked.pickable == true) {
				inventaire.add(objectChecked);
				gp.objectsOnGround.remove(i);
			}
			String objectName = objectChecked.name;
			
			switch(objectName){
				case "Key":
//					gp.playSE(1); Put sound when a key gets picked up.
					hasKey++;
					playersTile.whatIsHere = null;
					// Removes checked item from the map.
					gp.ui.messageType="Key";
					gp.ui.showMessage("You got a key !");
					break;
				case "Door":
					if (hasKey > 0) {
						gp.objectsOnGround.remove(i);
						playersTile.whatIsHere = null;
						gp.playSE(2);
						hasKey--;
						OBJ_Key premiereCle = null;
						for (SuperObject item : inventaire) {
							if (item instanceof OBJ_Key) {
								premiereCle = (OBJ_Key)item;
								break;
							}
						}
						inventaire.remove(premiereCle); // Removes first key from inventory.
					}
					else {
						gp.ui.showMessage("You need a key !");
					}
					break;
				case "Chest": // finish
					gp.ui.gameFinish = true;
					//gp.stopMusic();
					//gp.playSE(1);			
					break;
				case "Portal":
					gp.newMap();	
					break;
				case "Boots":
					gp.ui.messageType="DodgeUp";
					gp.ui.showMessage("Dodge +1");
					dodge ++;
					playersTile.whatIsHere = null;
					break;
				case "Sword":
					gp.ui.messageType="AttackUp";
					gp.ui.showMessage("Attack +1");
					attack ++;
					playersTile.whatIsHere = null;
					break;
				case "Shield":
					gp.ui.messageType="DefenseUp";
					gp.ui.showMessage("Defense +1");
					defense ++;
					playersTile.whatIsHere = null;
					break;
			}	
		}
		// This next part was used before because we took example on a game which doesn't use the tile system.
		// It will be reworked completely, as will the collision checker.
		/*SuperObject itemOnChunk = playersTile.whatIsHere;
		if (itemOnChunk != null && itemOnChunk.pickable == true) {
			int indexItemPickedUp = 999;
			for (SuperObject item:gp.objectsOnGround) {
				if (itemOnChunk.equals(item)){
					indexItemPickedUp=gp.objectsOnGround.indexOf(item);
					//System.out.println(itemOnChunk+" "+item +" indexof: "+indexItemPickedUp);
				}
			}
			if (indexItemPickedUp != 999) {
			gp.objectsOnGround.remove(indexItemPickedUp);
			inventaire.add(itemOnChunk);
			itemOnChunk = null;
			gp.map.grid[this.x/GamePanel.TILE_SIZE][this.y/GamePanel.TILE_SIZE].whatIsHere = null;
			}
		}*/
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.TILE_SIZE, gp.TILE_SIZE);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if (attacking == true) {
				tempScreenY = screenY - GamePanel.TILE_SIZE;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum ==2) {image = down2;}
			}
			if (attacking == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum ==2) {image = left2;}
			}
			if (attacking == true) {
				tempScreenX = screenX - GamePanel.TILE_SIZE;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
			if (attacking == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY,null);
	}
}
