package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import tile.Tile;
public class NewMonster extends Living {
		public final static double DIST_AGRO = 6*GamePanel.TILE_SIZE;
		int spriteNumM = 1;
		public int spriteDieCounter = 0;
		public boolean dying = false;
		public boolean alive = true;
		public BufferedImage dyingUp1, dyingUp2, dyingDown1, dyingDown2, dyingLeft1, dyingLeft2, dyingRight1, dyingRight2;
		
	
	public void update() {
		if (isOnPlayerChunk()) {
			// We still have some debug to do with our A*. Once it's done, we'll modify 
			// this part so we don't need an generic aggression distance. It'll be dependent 
			// on the type of monster and will be directly managed by the A*. Principle is :
			// do the A* search until a monster-specific path length (= aggro distance) is 
			// reached or no way can be found.
			double distToPlayer = getEuclidDist();
			if (gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE] != null)
				gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE].whoIsHere = null;
			if (gp.player.hasMove == true) {
				if (distToPlayer>DIST_AGRO) {
					randomMove();
					collisionOn = false;
					gp.cChecker.checkTile(this);
					gp.cChecker.checkObject2(this,false);
					moveMonster();
					hasMove = true;
				}
				else if (distToPlayer > GamePanel.TILE_SIZE) {			
					pathfinding();
					collisionOn = false;
					gp.cChecker.checkTile(this);
					gp.cChecker.checkObject2(this,false);
					moveMonster();
					hasMove = true;
				}
				else {
					pathfinding();
					collisionOn = false;
					gp.cChecker.checkTile(this);
					gp.cChecker.checkObject2(this,false);
					switch(direction) {
						case "up": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)-1]; break;
						case "down": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)][(y/GamePanel.TILE_SIZE)+1]; break;
						case "left": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)-1][(y/GamePanel.TILE_SIZE)]; break;
						case "right": nextCase = gp.map.grid[(x/GamePanel.TILE_SIZE)+1][(y/GamePanel.TILE_SIZE)]; break;
					}
					if (hasMove == false && gp.player.hasMove == true && nextCase.whoIsHere == gp.player && this.hp > 0){
						attack(gp.player); 
					}
				}
			}
				gp.map.grid[x/GamePanel.TILE_SIZE][y/GamePanel.TILE_SIZE].whoIsHere = this;
		}
		if (dying == true) {
			spriteDieCounter++;

			if (spriteDieCounter <= 2) {spriteNumM = 1;}
			if (spriteDieCounter > 2 && spriteDieCounter <= 4) {spriteNumM = 2;}
			if (spriteDieCounter > 4 && spriteDieCounter <= 6) {spriteNumM = 1;}
			if (spriteDieCounter > 6 && spriteDieCounter <= 6) {spriteNumM = 2;}
			if (spriteDieCounter > 8 && spriteDieCounter <= 8) {spriteNumM = 1;}
			if (spriteDieCounter > 10 && spriteDieCounter <= 12) {spriteNumM = 2;}
			if (spriteDieCounter > 12) {
				spriteNumM = 1;
				spriteDieCounter = 0;
				dying = false;
				alive = false;
			}
		}
		
			gp.cChecker.checkObject2(this, false); // False here means it's for a monster.
	}

	public void moveMonster() {
		
		if (collisionOn == false) {
				switch(direction) {	
					case "up": 
						if ((y / GamePanel.TILE_SIZE) % GamePanel.MAX_SCREEN_ROW != 1) {
							// Can't move if the tile is one of the entrances of the room.
							y-= speed; screenY = y % GamePanel.SCREEN_HEIGHT; break;
						}break;
					case "down":
						if ((y / GamePanel.TILE_SIZE) %GamePanel.MAX_SCREEN_ROW != GamePanel.MAX_SCREEN_ROW-2) {
							y += speed; screenY = y % GamePanel.SCREEN_HEIGHT; break;
						}break;
					case "left":
						if ((x / GamePanel.TILE_SIZE) % GamePanel.MAX_SCREEN_COL != 1) {
							x -= speed; screenX = x % GamePanel.SCREEN_WIDTH; break;
						}break;
					case "right": 
						if ((x / GamePanel.TILE_SIZE) % GamePanel.MAX_SCREEN_COL != GamePanel.MAX_SCREEN_COL -2) {
							x+= speed; screenX = x % GamePanel.SCREEN_WIDTH; break;
						}break;
				}			
		}
		
		spriteNumM++;
		if (spriteNumM>2) {
			spriteNumM = 1;
		}
	}
	
	// Checks if the monster is in the player's current room.
	public boolean isOnPlayerChunk() {
		return (x/GamePanel.SCREEN_WIDTH == gp.player.x/GamePanel.SCREEN_WIDTH 
				&& y/GamePanel.SCREEN_HEIGHT== gp.player.y/GamePanel.SCREEN_HEIGHT);
	}

	public double getEuclidDist() {
		// Won't be necessary once the A* is implemented.
		int xdif = Math.abs(x-gp.player.x);
		int ydif = Math.abs(y-gp.player.y);
		double dToEnd = Math.sqrt((xdif*xdif)+(ydif*ydif));
		return dToEnd;
	}
	
	public void randomMove() {
		switch(gp.randomNumberGenerator.nextInt(4)) {
		case 0: direction="up"; break;
		case 1: direction="down"; break;
		case 2: direction="left"; break;
		case 3: direction="right"; break;
		}
	}
	
	public void agroMove() {
		// Old stuff used before A* (pathfinding()) was implemented. Still here for debug purposes.
		int diffX= Math.abs(x-gp.player.x);
		int diffY= Math.abs(y-gp.player.y);
		// Check if monster has more distance top Player on x or y axes 
		if (diffX>diffY) {
			//if x is has greater than y to the Player
			if(x<gp.player.x)  direction="right";
			else  direction="left";					
			}		
		else if(diffX<diffY){
			//if y is has greater distance than x to the Player
			if(y>gp.player.y) direction="up";
			else  direction="down";					
			}		
		else { // x equal y
			int random=gp.randomNumberGenerator.nextInt(2);
			// if random equal 1 then priority to x, else to y
			if (random==1) {			
				if(x<gp.player.x)  direction ="right";
				else direction ="left";
			}else {
				if(y>gp.player.y)  direction="up";
				else  direction="down";
			}
		}
	}

	class Node{
		// These are used for the A* (pathfinding()) just below.
		int nodeX, nodeY, lengthOfPathFromStart;
		float distanceToEnd, sumDistances;
		Node whosYourDaddy;
		
		public Node (int x, int y){
			nodeX = x;
			nodeY = y;
			lengthOfPathFromStart = -1;
			sumDistances = -1;
			whosYourDaddy = null;
		}
	}
	
	public void pathfinding () {
		// This is still being worked out because some neighboring nodes don't seem to be considered 
		// neighbors in game. Thus, some monsters sometimes try to go around a pond instead of just
		// taking the shortest path.
		var openList = new ArrayList<Node>();
		var closedList = new ArrayList<Node>();
		boolean endIsReached = false;
		// Initializing
		Node endNode = new Node(gp.player.x/GamePanel.TILE_SIZE, gp.player.y/GamePanel.TILE_SIZE);
		endNode.distanceToEnd = 0;
		Node startNode = new Node(x/GamePanel.TILE_SIZE, y/GamePanel.TILE_SIZE);
		startNode.lengthOfPathFromStart = 0;
		startNode.distanceToEnd = (float) Math.sqrt((double)
				((startNode.nodeX - endNode.nodeX)*(startNode.nodeX - endNode.nodeX) +
				(startNode.nodeY - endNode.nodeY)*(startNode.nodeY - endNode.nodeY)) );
		startNode.sumDistances = startNode.lengthOfPathFromStart + startNode.distanceToEnd;
		openList.add(startNode);
		openList.add(endNode);
		
		for (int i = x / GamePanel.SCREEN_WIDTH * GamePanel.MAX_SCREEN_COL; i < (x / GamePanel.SCREEN_WIDTH + 1) * GamePanel.MAX_SCREEN_COL; i++) {
			for (int j = y / GamePanel.SCREEN_HEIGHT * GamePanel.MAX_SCREEN_ROW; j < (y / GamePanel.SCREEN_HEIGHT + 1) * GamePanel.MAX_SCREEN_ROW; j++) {
				Tile tileChecked = gp.map.grid[i][j];
				if (tileChecked.whoIsHere == null && !tileChecked.collision) {
					Node nodeChecked = new Node(tileChecked.tileX, tileChecked.tileY);
					nodeChecked.distanceToEnd = (float) Math.sqrt((double)
							((nodeChecked.nodeX - endNode.nodeX)*(nodeChecked.nodeX - endNode.nodeX) +
							(nodeChecked.nodeY - endNode.nodeY)*(nodeChecked.nodeY - endNode.nodeY)) );
					nodeChecked.sumDistances = nodeChecked.lengthOfPathFromStart + nodeChecked.distanceToEnd; // Useless when initializing
					openList.add(nodeChecked);
				}
			}
		}
		//System.out.println(openList.size());	// Working
		int i = 0; // debug
		
		while (!endIsReached) {
			Node currentNode = null;
			for (Node nodeChecked : openList) { // Get the available node with the lesser sum of distances.
				if (nodeChecked.lengthOfPathFromStart != -1) {
					if (currentNode == null) {
						currentNode = nodeChecked;
					}
					else if (nodeChecked.sumDistances < nodeChecked.sumDistances) {
						currentNode = nodeChecked;
					}
				}
			}
			
			if (currentNode == null/* or currentNode.sumDistances > agressionDistance */) {
				//System.out.println("i1 : " + i);
				endIsReached = true;
				//System.out.println("Plus aucun noeud dispo.");
				//randomMove();
			}else {
   			for (Node nodeChecked : openList) { //Get its neighbours that haven't been checked yet.
   				if ((nodeChecked.nodeY == currentNode.nodeY && nodeChecked.nodeX == currentNode.nodeX+1) ||
   						(nodeChecked.nodeY == currentNode.nodeY && nodeChecked.nodeX == currentNode.nodeX-1) ||
   						(nodeChecked.nodeX == currentNode.nodeX && nodeChecked.nodeY == currentNode.nodeY+1) ||
   						(nodeChecked.nodeX == currentNode.nodeX && nodeChecked.nodeY == currentNode.nodeY-1)){
   					if (((Object) nodeChecked).equals((Object) endNode)) {
   						//System.out.println(closedList.toString());
   						endIsReached = true;
      					nodeChecked.whosYourDaddy = currentNode;
      					nodeChecked.lengthOfPathFromStart = currentNode.lengthOfPathFromStart + 1;
      					nodeChecked.sumDistances = nodeChecked.lengthOfPathFromStart;
      					while (nodeChecked.whosYourDaddy.whosYourDaddy != null)
      						nodeChecked = nodeChecked.whosYourDaddy;
      					// Ajouter aggroMove()
      					if ((x/GamePanel.TILE_SIZE)>nodeChecked.nodeX) {
                        direction="left";        
                        
                        //System.out.println("gauche "+"joueur :"+(screenX/GamePanel.TILE_SIZE)+"node "+nodeChecked.nodeX);
                      }        
                      else if((x/GamePanel.TILE_SIZE)<nodeChecked.nodeX){
                     	 direction="right";          
                     	//System.out.println("droit "+"joueur :"+(screenX/GamePanel.TILE_SIZE)+"node "+nodeChecked.nodeX);
                      }        
                      else if((y/GamePanel.TILE_SIZE)>nodeChecked.nodeY) {
                     	 direction ="up";
                     	//System.out.println("haut "+"joueur :"+(screenY/GamePanel.TILE_SIZE)+"node "+nodeChecked.nodeY);
                      }
                      else if((y/GamePanel.TILE_SIZE)<nodeChecked.nodeY){
                     	 direction="down";    
                     	//System.out.println("bas "+"joueur :"+(screenY/GamePanel.TILE_SIZE)+"node "+nodeChecked.nodeY);
                      }
   					}
   					else if (nodeChecked.lengthOfPathFromStart > currentNode.lengthOfPathFromStart + 1 || nodeChecked.lengthOfPathFromStart == -1) {
      					nodeChecked.whosYourDaddy = currentNode;
      					nodeChecked.lengthOfPathFromStart = currentNode.lengthOfPathFromStart + 1;
      					nodeChecked.sumDistances = nodeChecked.distanceToEnd + nodeChecked.lengthOfPathFromStart;
      				}
   				}
   			}
				//closedList.add(currentNode);	
				openList.remove(openList.indexOf(currentNode));
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
   	solidArea = new Rectangle(0,0,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);	
   	
   	BufferedImage image = null;
   	
   	switch(direction) {
   	case "up":
   		if (dying == false) {
   			if (spriteNumM == 1) {image = up1;}
   			if (spriteNumM == 2) {image = up2;}
   		}
   		if (dying == true) {
   			if (spriteNumM == 1) {image = dyingUp1;}
   			if (spriteNumM == 2) {image = dyingUp2;}
   		}
   		break;
   	case "down":
   		if (dying == false) {
   			if (spriteNumM == 1) {image = down1;}
   			if (spriteNumM ==2) {image = down2;}
   		}
   		if (dying == true) {
   			if (spriteNumM == 1) {image = dyingDown1;}
   			if (spriteNumM == 2) {image = dyingDown2;}
   		}
   		break;
   	case "left":
   		if (dying == false) {
   			if (spriteNumM == 1) {image = left1;}
   			if (spriteNumM ==2) {image = left2;}
   		}
   		if (dying == true) {
   			if (spriteNumM == 1) {image = dyingLeft1;}
   			if (spriteNumM == 2) {image = dyingLeft2;}
   		}
   		break;
   	case "right":
   		if (dying == false) {
   			if (spriteNumM == 1) {image = right1;}
   			if (spriteNumM == 2) {image = right2;}
   		}
   		if (dying == true) {
   			if (spriteNumM == 1) {image = dyingRight1;}
   			if (spriteNumM == 2) {image = dyingRight2;}
   		}
   		break;
   	}
   	
   	g2.drawImage(image, x % GamePanel.SCREEN_WIDTH, y % GamePanel.SCREEN_HEIGHT, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
   }
		
}