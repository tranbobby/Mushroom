package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class SuperObject extends Entity{
	// This is the superclass of all items. Some UI element have been put here temporarily
	// but may switch to another superclass (and package) later.
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public Rectangle solidArea = new Rectangle(0,0,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public int objScreenX, objScreenY;
	public boolean isUnique;
	public boolean pickable;

	public void draw(Graphics2D g2) {
		/*objScreenX = worldX-(gp.player.x/gp.SCREEN_WIDTH)*gp.MAX_SCREEN_COL*gp.TILE_SIZE;
		objScreenY = worldY-(gp.player.y/gp.SCREEN_HEIGHT)*gp.MAX_SCREEN_ROW*gp.TILE_SIZE;
		solidArea = new Rectangle(0,0,gp.TILE_SIZE,gp.TILE_SIZE);*/

		
		g2.drawImage(image, x % GamePanel.SCREEN_WIDTH, y % GamePanel.SCREEN_HEIGHT, 
				GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
//		System.out.println("worldX clé:"+(worldX-gp.player.x) +" worldY clé:"
//				+(worldY-gp.player.y));
	}
	public void draw(Graphics2D g2, int x, int y) {
		g2.drawImage(image, x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
	}
	
	public String getName() {
		return name;
	}
	public boolean equals(Object o) {
		if (o instanceof SuperObject) {
			return ((SuperObject) o) == this;
		}
		else return false;
	}
}
