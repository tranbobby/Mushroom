package tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.Living;
import object.SuperObject;

public class Tile {
	public int num, tileX, tileY;
	public BufferedImage image;
	public boolean collision = false;
	public Living whoIsHere;
	public /*ArrayList<SuperObject>*/ SuperObject whatIsHere;
	
	public Tile() {}
	
	public Tile (int n, int x, int y, BufferedImage img, boolean col) {
		num = n;
		this.tileX = x;
		this.tileY =y;
		image = img;
		collision = col;
		whoIsHere = null;
		whatIsHere = null; //new ArrayList<SuperObject>(0);
	}
	
	public String toString() {
		return String.valueOf(((Object) this).hashCode());
		//return (String.valueOf(num) + " ");
	}
}
