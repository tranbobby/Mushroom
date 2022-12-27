package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Player;
import entity.BossChamp;
import entity.NewMonster;
import object.OBJ_Door;
import object.SuperObject;
import tile.Map;

public class GamePanel extends JPanel implements Runnable{ 
	
	// SCREEN SETTINGS
	public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 px tile, default size for characters, objects, etc.
	public static final int SCALE = 2; // Makes the tiles take thrice as much space, 
								// because high-resolution monitors would otherwise render them too small.
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 px tile
	
	// WORLD
	public static final int MAX_SCREEN_COL = 16; // number of horizontal tiles in a room/screen
	public static final int MAX_SCREEN_ROW = 12; // number of vertical tiles in a room/screen
	public static final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE; 
	public static final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE; 
	public static final int NUMBER_OF_LEVELS = 3;
	public boolean gameOver = false;
	public boolean titleScreen = true;
	
	// FPS 
	int FPS = 60;
	
	//SYSTEM
	public KeyHandler keyH = new KeyHandler(this);
	public Generator randomNumberGenerator = new Generator();
	public int gameLevel;
	public Map map;
	Sound music = new Sound();
	Sound se = new Sound();
	Thread gameThread; // implements Runnable MENDATORY --> definition of run() has to be done
	public Player player = new Player(this, keyH);
	public UI ui = new UI(this);
	
	// ENTITY AND OBJECT
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter = new AssetSetter(this);
	public ArrayList<SuperObject> listOfAvailableObjects;
	public ArrayList<SuperObject> objectsOnGround;
	//public SuperObject obj[] = new SuperObject[100]; //a modifier selon le nombre d objects pr�sents en meme temps sur la carte
	public ArrayList<NewMonster> listOfAvailableMonsters;
	public ArrayList<NewMonster> monsters = new ArrayList<NewMonster>();
	
	public GamePanel() {
		setUpGame();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // donne un meilleur rendu graphique
		this.addKeyListener(keyH);
		this.setFocusable(true); //this GamePanel can be focused to receive key input
	}
	
	public void reset() {
		stopMusic();
		player = new Player(this,keyH);
		//map = new Map(this);
		//obj = new SuperObject[100];
		aSetter = new AssetSetter(this);
		setUpGame();
		gameOver = false;
		ui.gameFinish = false;
		ui.messageOn=false;
		ui.messageCounter=0;
		ui.message="";
	}
	
	public void setUpGame() { // m�thode pour cr�er et positionner les diff�rents objets
		monsters = new ArrayList<NewMonster>();
		objectsOnGround = new ArrayList<SuperObject>();
		aSetter.setObject();
		gameLevel = 0;
		newMap();
		playMusic(3);
		/*for (NewMonster monster : monsters)
			System.out.println(monster.x +", "+monster.y);*/
		//System.out.println(map.grid[0][0].whoIsHere);
		//map.grid[13][2].whatIsHere = obj[1]; //sale pour test
	}
	
	public void newMap() {

		while (monsters.size() > 0) {
			monsters.remove(0);
		}
		
		while (objectsOnGround.size() > 0) {
			objectsOnGround.remove(0);
		}
		
		/*for (int i = 0; i < obj.length; i++) {
			obj[i] = null;
		}*/
		
		gameLevel ++;
		map = new Map(this);
		// Tout le bazar de player, c'est pour le remettre en haut � gauche quand on commence un nouveau niveau.
		player.initTileX = 2;
		player.initTileY = 2;
		player.x = GamePanel.TILE_SIZE*player.initTileX;
		player.y = GamePanel.TILE_SIZE*player.initTileY;
		player.screenX = player.x % GamePanel.SCREEN_WIDTH;
		player.screenY = player.y % GamePanel.SCREEN_HEIGHT;
		//System.out.println(gameLevel);
		aSetter.setObject();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); // call run() method
	}
	
	@Override
	public void run() { 
		
		double drawInterval = 1000000000/FPS; //draw the screen every 0.0167sec
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
			
			// 1 UPDATE: information such as character position
			update();
			
			// 2 DRAW: draw the screen with the updated information
			repaint(); //call paintComponent method
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; //sleep in ms
				
				if (remainingTime < 0) {
					remainingTime =0;
				}
				
				Thread.sleep((long) remainingTime); 
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		if (gameOver == false) {
			player.update();
			if (player.hp <=0) {
				gameOver = true;
			}
			NewMonster monsterKilled = null;
			for(NewMonster monster : monsters) {
				monster.update();
				if (monster.hp <=0 && monster.alive == true) {
					if (monster instanceof BossChamp) {
						/*map.grid[map.MAX_WORLD_COL - 3][map.MAX_WORLD_ROW - 5].whatIsHere = null;
						obj[3] = null;*/
					
						OBJ_Door doorEnd = null;
						for (SuperObject item : objectsOnGround) {
							if (item instanceof OBJ_Door) {
								doorEnd = (OBJ_Door)item;
								break;
							}
						}
						if (doorEnd != null) {
   						map.grid[doorEnd.x/TILE_SIZE][doorEnd.y/TILE_SIZE].whatIsHere = null;
   						objectsOnGround.remove(doorEnd);
						}
					}
					monster.dying = true;
					keyH.stateWait=true;
				}
				else if (monster.hp <= 0 && monster.alive == false) {
					monsterKilled = monster;
					player.hasMove = true;
					if (player.nextCase != null) player.nextCase.whoIsHere = null;
				}
				else {
					monster.hasAttack = false;
					monster.hasMove = false;
				}		
			}
			if (monsterKilled != null) {
				ui.lastMonsterX=monsterKilled.x;
				ui.lastMonsterY=monsterKilled.y;
				monsters.remove(monsterKilled);
				monsterKilled = null;
				keyH.stateWait=false;
			} 
			player.hasMove = false;
			player.hasAttack = false;
		}
}

	public void paintComponent(Graphics g) { //Jpanel method
		super.paintComponent(g); 
		
		Graphics2D g2 = (Graphics2D)g; //convert to get more 2D options 
		
		map.draw(g2); // before player, or bg will hide characters
		
		/*for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null && obj[i].x/GamePanel.SCREEN_WIDTH == player.x/GamePanel.SCREEN_WIDTH 
					&& obj[i].y/GamePanel.SCREEN_HEIGHT == player.y/GamePanel.SCREEN_HEIGHT) {
				obj[i].draw(g2);
			}
		}*/
		/*for(SuperObject objectDrawn : objectsOnGround) {
			if(objectDrawn.x/GamePanel.SCREEN_WIDTH == player.x/GamePanel.SCREEN_WIDTH 
   			&& objectDrawn.y/GamePanel.SCREEN_HEIGHT == player.y/GamePanel.SCREEN_HEIGHT) {
				objectDrawn.draw(g2);
			}
		}*/
		player.draw(g2);		
		
		//UI
		ui.draw(g2);
		
		g2.dispose(); //save memory
	}
	
	public void playMusic(int i){
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) { //Sound effect
		se.setFile(i);
		se.play();
	}
	
}