package tile;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.BossChamp;
import entity.Entity;
import entity.Living;
import entity.NewMonster;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_DoorEnd;
import object.OBJ_Key;
import object.OBJ_Portal;
import object.SuperObject;


public class Map {
	// This class manages the creation of the map and the generation of all its elements.
	// The map is made out of Tile objects. Those tiles are regrouped in chunks, which have 
	// the same size as the screen. Those chunks are randomized (except the ending one) both 
	// background-wise and entities-wise.
	
   public GamePanel gp;
   public Tile[][] grid;	// The core map
	public BufferedImage[] tileImage;	// The images of the different kinds of tiles
   static final String chunkExitPath = "/maps/map_exit.txt";   
   static final String[] chunkPath = {"/maps/map01.txt", "/maps/map02.txt"};
   // Where to find the .txt of each chunk
   static final String[] tilePath = {"/tiles/grass.png", "/tiles/wall.png", "/tiles/water.png", "/tiles/earth.png", 
   		"/tiles/tree.png", "/tiles/sand.png"};
   // Where to find the image of each tile
	public static final boolean FLAG_PLACE_KEY = true;
   
	//WORLD 
   public final int NUMBER_OF_ROOMS_HORIZONTAL;
   public final int NUMBER_OF_ROOMS_VERTICAL;
   public final int NUMBER_OF_ROOMS;
	public final int MAX_WORLD_COL;	// number of horizontal tiles on a map
	public final int MAX_WORLD_ROW;	// number of vertical tiles on a map
	public final int WORLD_WIDTH;		// in pixels
	public final int WORLD_HEIGHT;	// in pixels 
	
   
   // constructors
	public Map(GamePanel gp) {
		this.gp = gp;

	   NUMBER_OF_ROOMS_HORIZONTAL = 1 + gp.gameLevel;
	   NUMBER_OF_ROOMS_VERTICAL = 1 + gp.gameLevel;
	   NUMBER_OF_ROOMS = NUMBER_OF_ROOMS_HORIZONTAL * NUMBER_OF_ROOMS_VERTICAL;
		MAX_WORLD_COL = GamePanel.MAX_SCREEN_COL * NUMBER_OF_ROOMS_HORIZONTAL;	// number of horizontal tiles on a map
		MAX_WORLD_ROW = GamePanel.MAX_SCREEN_ROW * NUMBER_OF_ROOMS_VERTICAL;	// number of vertical tiles on a map
		WORLD_WIDTH = GamePanel.TILE_SIZE * MAX_WORLD_COL;
		WORLD_HEIGHT = GamePanel.TILE_SIZE * MAX_WORLD_ROW; 
		
		grid = new Tile [MAX_WORLD_COL][MAX_WORLD_ROW];
		
		int chunkXKey,chunkYKey;	// The chunk where the key will appear.
		
		getTileImages();
		
		if (gp.gameLevel < GamePanel.NUMBER_OF_LEVELS) {
         do {	// Sets the chunk where the key will appear. It cannot be the start or the end chunks.
         	chunkXKey = gp.randomNumberGenerator.nextInt(NUMBER_OF_ROOMS_HORIZONTAL);
         	chunkYKey = gp.randomNumberGenerator.nextInt(NUMBER_OF_ROOMS_VERTICAL);
         } while ((chunkXKey == 0 && chunkYKey == 0) || (chunkXKey == NUMBER_OF_ROOMS_HORIZONTAL - 1 && chunkYKey == NUMBER_OF_ROOMS_VERTICAL - 1));
		}else {
			chunkXKey = -1;
			chunkYKey = -1;
		}
      for (int chunkCol = 0 ; chunkCol < NUMBER_OF_ROOMS_HORIZONTAL; chunkCol++){
         for (int chunkRow = 0; chunkRow < NUMBER_OF_ROOMS_VERTICAL; chunkRow++){
         	if (chunkCol == (NUMBER_OF_ROOMS_HORIZONTAL - 1) && chunkRow == (NUMBER_OF_ROOMS_VERTICAL - 1)) {
         		// The bottom-right chunk holds the exit.
         		setExit();
         	}else{
            	// For each other chunk, except the exit, generate that chunk.
            	loadChunkBackground(chunkCol, chunkRow);
            	if (chunkCol == chunkXKey && chunkRow == chunkYKey)
            		loadChunkEntities(chunkCol, chunkRow, FLAG_PLACE_KEY);
            	else
               	loadChunkEntities(chunkCol, chunkRow, !FLAG_PLACE_KEY);
         	}
         }
      }
      setBorder();
	}

	public void loadChunkBackground(int chunkCol, int chunkRow) {
      String chunkLoaded = chunkPath[gp.randomNumberGenerator.nextInt(chunkPath.length)];
      // Get a random chunk.

   	try {
			InputStream is = getClass().getResourceAsStream(chunkLoaded);
			// Opens a stream reading bytes from chunkLoaded.
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			// Reads bytes and arrange them into chars.
			int orientation = gp.randomNumberGenerator.nextInt(4);
			// 0 : base, 1 : symetry /x, 2 : symetry /y, 3 : symetry /x and /y
      	
			switch (orientation) {
      	case 0 :
   			for (int tileRow = 0; tileRow < GamePanel.MAX_SCREEN_ROW; tileRow++) {
   				String numbers[] = br.readLine().split(" ");
   				// Reads a line and converts it into an array of (String) numbers.
            	for (int tileCol = 0; tileCol < GamePanel.MAX_SCREEN_COL; tileCol++) {
            		int num = Integer.parseInt(numbers[tileCol]);
            		boolean collision = (num == 1 || num == 2 || num == 4);
            		grid[chunkCol*GamePanel.MAX_SCREEN_COL + tileCol][chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow] = 
            				new Tile(num, chunkCol*GamePanel.MAX_SCREEN_COL + tileCol, chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow, 
            						tileImage[num], collision);
            	}
   			}
   			break;
   			
      	case 1 :
   			for (int tileRow = 0; tileRow < GamePanel.MAX_SCREEN_ROW; tileRow++) {
   				String numbers[] = br.readLine().split(" ");
            	for (int tileCol = GamePanel.MAX_SCREEN_COL-1; tileCol >= 0 ; tileCol--) {
            		int num = Integer.parseInt(numbers[GamePanel.MAX_SCREEN_COL - 1 - tileCol]);
            		boolean collision = (num == 1 || num == 2 || num == 4);
            		grid[chunkCol*GamePanel.MAX_SCREEN_COL + tileCol][chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow] = 
            				new Tile(num, chunkCol*GamePanel.MAX_SCREEN_COL + tileCol, chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow, 
            						tileImage[num], collision);
            	}
   			}
   			break;
   			
      	case 2 :
   			for (int tileRow = GamePanel.MAX_SCREEN_ROW-1; tileRow >= 0; tileRow--) {
   				String numbers[] = br.readLine().split(" ");
            	for (int tileCol = 0; tileCol < GamePanel.MAX_SCREEN_COL; tileCol++) {
            		int num = Integer.parseInt(numbers[tileCol]);
            		boolean collision = (num == 1 || num == 2 || num == 4);
            		grid[chunkCol*GamePanel.MAX_SCREEN_COL + tileCol][chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow] = 
            				new Tile(num, chunkCol*GamePanel.MAX_SCREEN_COL + tileCol, chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow, 
            						tileImage[num], collision);
            	}
   			}
   			break;
   			
      	case 3 :
   			for (int tileRow = GamePanel.MAX_SCREEN_ROW-1; tileRow >= 0; tileRow--) {
   				String numbers[] = br.readLine().split(" ");
            	for (int tileCol = GamePanel.MAX_SCREEN_COL-1; tileCol >= 0 ; tileCol--) {
            		int num = Integer.parseInt(numbers[GamePanel.MAX_SCREEN_COL - 1 - tileCol]);
            		boolean collision = (num == 1 || num == 2 || num == 4);
            		grid[chunkCol*GamePanel.MAX_SCREEN_COL + tileCol][chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow] = 
            				new Tile(num, chunkCol*GamePanel.MAX_SCREEN_COL + tileCol, chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow, 
            						tileImage[num], collision);
            	}
   			}
   			break;
   			
      	}
      	br.close();
   	}catch(Exception e) {}
		
	}

	public void loadChunkEntities(int chunkCol, int chunkRow, boolean placeKey) {
		// Get a list of entities (up to one object, the key and some monsters) to spawn on that chunk.
		ArrayList<Entity> entitiesToSpawn = new ArrayList<Entity>();
		
		if (placeKey) {
   		SuperObject key = (SuperObject) new OBJ_Key();
			entitiesToSpawn.add(key);
			System.out.println("Placing the Key.");
		}
		
		if (gp.randomNumberGenerator.nextBoolean()) {	// 1/2 chance to have an object
			int index = gp.randomNumberGenerator.nextInt(gp.listOfAvailableObjects.size());
			SuperObject objectChecked = gp.listOfAvailableObjects.get(index);
			try{
				SuperObject objectToAdd = objectChecked.getClass().getConstructor().newInstance();
				entitiesToSpawn.add(objectToAdd);
				//gp.obj.add(objectToAdd);
			}catch(Exception e) {}
			if (objectChecked.isUnique)	// We will have more items later, some of them unique.
				gp.listOfAvailableObjects.remove((Object) objectChecked);
		}
		
		for (int i = 0; i < (3 + gp.gameLevel); i++){
			int index = gp.randomNumberGenerator.nextInt(gp.listOfAvailableMonsters.size());
			NewMonster monsterChecked = gp.listOfAvailableMonsters.get(index);
			try{
				NewMonster monsterToAdd = monsterChecked.getClass().getConstructor(GamePanel.class).newInstance(gp);
				entitiesToSpawn.add(monsterToAdd);
			}catch(Exception e) {}
		}
		
		// Get the possible spawn locations of those entities.
		ArrayList<Tile> whereCanEntitySpawn = new ArrayList<Tile>();
		for (int tileRow = 2; tileRow < GamePanel.MAX_SCREEN_ROW - 2; tileRow++) {
      	for (int tileCol = 2; tileCol < GamePanel.MAX_SCREEN_COL - 2; tileCol++) {
      		Tile tileBeingChecked = grid[chunkCol*GamePanel.MAX_SCREEN_COL + tileCol][chunkRow*GamePanel.MAX_SCREEN_ROW + tileRow];
      		if (!(chunkRow == 0 && chunkCol == 0 && tileRow == 2 && tileCol == 2) && !tileBeingChecked.collision) {
      			whereCanEntitySpawn.add(tileBeingChecked);
      		}
      	}
		}
		//System.out.println(entitiesToSpawn);
		
		for (Entity entityChecked : entitiesToSpawn) {
			int index = gp.randomNumberGenerator.nextInt(whereCanEntitySpawn.size());
			Tile entitySpawnTile = whereCanEntitySpawn.get(index);
			if (entityChecked instanceof SuperObject) {
				entitySpawnTile.whatIsHere = (SuperObject) entityChecked;
				gp.objectsOnGround.add((SuperObject) entityChecked);
			}else{
				entitySpawnTile.whoIsHere = (Living) entityChecked;
				gp.monsters.add((NewMonster) entityChecked);
			}/**/
			//entitySpawnTile.whoIsHere = (Living) entityChecked;
			entityChecked.x = entitySpawnTile.tileX*GamePanel.TILE_SIZE;
			entityChecked.y = entitySpawnTile.tileY*GamePanel.TILE_SIZE;
		}
		
	}/**/
	
	public void setBorder() {
		// Y'know, Earth is flat and Antarctica is just a big wall circling it to avoid falling, some say.
		
      // This method replaces the outermost tiles with walls to avoid leaving the map.
      for (int i = 0; i < MAX_WORLD_ROW; i++) {
      	grid[0][i].image = tileImage[1];
      	grid[0][i].collision = true;
      	grid[0][i].num = -1;
      	grid[MAX_WORLD_COL - 1][i].image = tileImage[1];
      	grid[MAX_WORLD_COL - 1][i].collision = true;
      	grid[MAX_WORLD_COL - 1][i].num = -1;
      }
      for (int i = 1; i < MAX_WORLD_COL - 1; i++) {
      	grid[i][0].image = tileImage[1];
      	grid[i][0].collision = true;
      	grid[i][MAX_WORLD_ROW - 1].image = tileImage[1];
      	grid[i][MAX_WORLD_ROW - 1].collision = true;
      }
	}
	
	public void setExit() {
   	try {
   		// Same as loadChunkBackground, but this one is special
			InputStream is = getClass().getResourceAsStream(chunkExitPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (int tileRow = (NUMBER_OF_ROOMS_VERTICAL-1) * GamePanel.MAX_SCREEN_ROW ;
					tileRow < MAX_WORLD_ROW;
					tileRow++) {
				String numbers[] = br.readLine().split(" ");
         	for (int tileCol = (NUMBER_OF_ROOMS_HORIZONTAL-1) * GamePanel.MAX_SCREEN_COL, imageNumber = 0; 
         			tileCol < MAX_WORLD_COL; 
         			tileCol++, imageNumber++) {
         		int num = Integer.parseInt(numbers[imageNumber]);
         		boolean collision = (num == 1 || num == 2 || num == 4);
         		grid[tileCol][tileRow] = 
         				new Tile(num, tileCol,tileRow, tileImage[num], collision);
         	}
			}
      	br.close();
   	}catch(Exception e) {
   		//System.out.println(e.toString());
   		}
   	if (gp.gameLevel < GamePanel.NUMBER_OF_LEVELS) {
   		SuperObject portal = (SuperObject) new OBJ_Portal();
   		portal.x = (MAX_WORLD_COL - 6) * GamePanel.TILE_SIZE;
   		portal.y = (MAX_WORLD_ROW - 2) * GamePanel.TILE_SIZE; 
   		grid[MAX_WORLD_COL - 6][MAX_WORLD_ROW - 2].whatIsHere = portal;
   		gp.objectsOnGround.add(portal);
   		SuperObject endOfLevelDoor = new OBJ_Door();
   		endOfLevelDoor.x = (MAX_WORLD_COL - 3) * GamePanel.TILE_SIZE;
   		endOfLevelDoor.y = (MAX_WORLD_ROW - 5) * GamePanel.TILE_SIZE; 
   		grid[MAX_WORLD_COL - 3][MAX_WORLD_ROW - 5].whatIsHere = endOfLevelDoor;
   		gp.objectsOnGround.add(endOfLevelDoor);
   	}else {
   		SuperObject chest = (SuperObject) new OBJ_Chest();
   		chest.x = (MAX_WORLD_COL - 6) * GamePanel.TILE_SIZE;
   		chest.y = (MAX_WORLD_ROW - 2) * GamePanel.TILE_SIZE; 
   		grid[MAX_WORLD_COL - 6][MAX_WORLD_ROW - 2].whatIsHere = chest;
   		gp.objectsOnGround.add(chest);
   		
   		SuperObject endOfLevelDoor = new OBJ_Door();
   		endOfLevelDoor.x = (MAX_WORLD_COL - 3) * GamePanel.TILE_SIZE;
   		endOfLevelDoor.y = (MAX_WORLD_ROW - 5) * GamePanel.TILE_SIZE;
   		grid[MAX_WORLD_COL - 3][MAX_WORLD_ROW - 5].whatIsHere = endOfLevelDoor;
   		gp.objectsOnGround.add(endOfLevelDoor);
   		
   		Living boss = new BossChamp(gp);
   		grid[MAX_WORLD_COL - GamePanel.MAX_SCREEN_COL/2][MAX_WORLD_ROW - GamePanel.MAX_SCREEN_ROW/2].whoIsHere = boss;
			boss.x = (MAX_WORLD_COL - GamePanel.MAX_SCREEN_COL/2)*GamePanel.TILE_SIZE;
			boss.y = (MAX_WORLD_ROW - GamePanel.MAX_SCREEN_ROW/2)*GamePanel.TILE_SIZE;
			gp.monsters.add((NewMonster) boss);
   	}
	}
	
	public void getTileImages() {
		try {
			int n = tilePath.length;
			tileImage = new BufferedImage[n];
			for (int i = 0; i < n; i++) {
   			tileImage[i] = ImageIO.read(getClass() .getResource(tilePath[i]));
			}
		}catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	public void draw(Graphics2D g2) {
	// 		(gp.player.x/gp.SCREEN_WIDTH)*gp.MAX_SCREEN_COL != gp.player.x/TILE_SIZE because of integer division
	//		=	(position in number of chunks)*screen size
		for (int worldCol = (gp.player.x/GamePanel.SCREEN_WIDTH)*GamePanel.MAX_SCREEN_COL, x = 0;
				worldCol < (gp.player.x/GamePanel.SCREEN_WIDTH + 1)*GamePanel.MAX_SCREEN_COL;
				worldCol++, x+=GamePanel.TILE_SIZE) {
			for (int worldRow = (gp.player.y/GamePanel.SCREEN_HEIGHT)*GamePanel.MAX_SCREEN_ROW, y = 0;
					worldRow < (gp.player.y/GamePanel.SCREEN_HEIGHT + 1)*GamePanel.MAX_SCREEN_ROW;
					worldRow++, y+=GamePanel.TILE_SIZE) {
				g2.drawImage(grid[worldCol][worldRow].image, x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
				// Draws the current tile at screen position (x, y), given its size.
				if (grid[worldCol][worldRow].whatIsHere != null) {
					//System.out.println(grid[worldCol][worldRow].whoIsHere.getClass());
					grid[worldCol][worldRow].whatIsHere.draw(g2, worldCol % GamePanel.MAX_SCREEN_COL, worldRow % GamePanel.MAX_SCREEN_ROW);
				}
				if (grid[worldCol][worldRow].whoIsHere != null) {
					//System.out.println(grid[worldCol][worldRow].whoIsHere.getClass());
					grid[worldCol][worldRow].whoIsHere.draw(g2);
				}
			}
		}

	}

}