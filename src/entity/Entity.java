package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import tile.Tile;

// player, monster, NPC and items
public abstract class Entity {
	public GamePanel gp;

	public int x,y;
	public int initTileX, initTileY;
	public int speed;
	public int screenX;
	public int screenY;
	public int hpMax;
	public int hp;
	public int attack;
	public int defense;
	public int dodge = 3;
	public boolean hasMove=false;
	public boolean hasAttack=false;
	public boolean attacking=false;
	public Tile nextCase;
	
	//describing an image with a accessible buffer of image data (store our img files)
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteAttackCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea; //x et y pour positionner le rectangle, width et height pour le dimensionner
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public abstract void draw(Graphics2D g2);
	
	public BufferedImage setupSprite(String imagePath, int width, int height) {
		UtilityTool tool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath));
			image = tool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public BufferedImage setupSprite(String imagePath) {
		UtilityTool tool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath));
			image = tool.scaleImage(image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}

}
