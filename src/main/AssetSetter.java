package main;

import java.util.ArrayList;

import entity.Champ;
import entity.Crabe;
import entity.NewMonster;
import entity.Slime;
import entity.Spider;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.SuperObject;

public class AssetSetter {
	GamePanel gp;
	
	// This class is prone to disappear now that the generation of items and monsters 
	// has been proceduralized in Map. It now only serves to initialize the lists of
	// available items and monsters. It will be transformed into a method of GamePanel,
	// or maybe of Map, since it's the only class using those.
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.listOfAvailableObjects = new ArrayList<SuperObject>();
		gp.listOfAvailableObjects.add(new OBJ_Boots()); //0
		//gp.listOfAvailableObjects.add(new OBJ_Key()); //1
		//gp.listOfAvailableObjects.add(new OBJ_Chest()); //2
		//gp.listOfAvailableObjects.add(new OBJ_Door()); //3
		gp.listOfAvailableObjects.add(new OBJ_Sword()); //4
		gp.listOfAvailableObjects.add(new OBJ_Shield()); //5

		
		gp.listOfAvailableMonsters = new ArrayList<NewMonster>();
		gp.listOfAvailableMonsters.add(new Slime(gp));
		gp.listOfAvailableMonsters.add(new Spider(gp));
		gp.listOfAvailableMonsters.add(new Crabe(gp));
		gp.listOfAvailableMonsters.add(new Champ(gp));
		
		/*gp.obj[0] = new OBJ_Key();
		gp.obj[0].x = 2 * GamePanel.TILE_SIZE;
		gp.obj[0].y = 1 * GamePanel.TILE_SIZE;
		
		
		gp.obj[2] = new OBJ_Sword();
		gp.obj[2].x = 4 * GamePanel.TILE_SIZE;
		gp.obj[2].y = 1 * GamePanel.TILE_SIZE;*/
		
		/*gp.obj[3] = new OBJ_Chest();
		gp.obj[3].x = 2 * GamePanel.TILE_SIZE;
		gp.obj[3].y = 1 * GamePanel.TILE_SIZE;*/
		
		
		/*gp.obj[4] = new OBJ_Boots();
		gp.obj[4].x = 3 * GamePanel.TILE_SIZE;
		gp.obj[4].y = 1 * GamePanel.TILE_SIZE;*/
		
		/*gp.obj[1] = new OBJ_Door();
		gp.obj[1].x = 13 * GamePanel.TILE_SIZE;
		gp.obj[1].y = 2 * GamePanel.TILE_SIZE;*/
		
//		
//		gp.obj[3] = new OBJ_Boots();
//		gp.obj[3].x = 5 * GamePanel.TILE_SIZE;
//		gp.obj[3].y = 5 * GamePanel.TILE_SIZE;
//		
//		gp.obj[4] = new OBJ_Chest();
//		gp.obj[4].x = 25 * GamePanel.TILE_SIZE;
//		gp.obj[4].y = 13 * GamePanel.TILE_SIZE;
		
		/*gp.monster[0] = new Slime(gp);
		gp.monster[0].x = 8 * GamePanel.TILE_SIZE;
		gp.monster[0].y = 9 * GamePanel.TILE_SIZE;
		
		gp.monster[1] = new Spider(gp);
		gp.monster[1].x = 10 * GamePanel.TILE_SIZE;
		gp.monster[1].y = 10 * GamePanel.TILE_SIZE;
		
		gp.monster[2] = new Crabe(gp);
		gp.monster[2].x = 12 * GamePanel.TILE_SIZE;
		gp.monster[2].y = 3 * GamePanel.TILE_SIZE;
		
		gp.monster[3] = new Champ(gp);
		gp.monster[3].x = 2 * GamePanel.TILE_SIZE;
		gp.monster[3].y = 10 * GamePanel.TILE_SIZE;*/
		
	}
}
