package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import object.OBJ_AlmostEmptyHeart;
import object.OBJ_AlmostEmptyHeartM;
import object.OBJ_AlmostFullHeart;
import object.OBJ_AlmostFullHeartM;
import object.OBJ_EmptyHeart;
import object.OBJ_FullHeart;
import object.OBJ_FullHeartM;
import object.OBJ_HalfHeart;
import object.OBJ_HalfHeartM;
import object.OBJ_Key;
import object.SuperObject;

public class UI {

	GamePanel gp;
	Font arial_30,arial_80B;
	BufferedImage titleBackgroundImage;
	
	BufferedImage keyImage;
	BufferedImage almostEmptyHeartImage;
	BufferedImage almostFullHeartImage;
	BufferedImage emptyHeartImage;
	BufferedImage halfHeartImage;
	BufferedImage fullHeartImage;
	BufferedImage almostEmptyHeartMImage;
	BufferedImage almostFullHeartMImage;
	BufferedImage emptyHeartMImage;
	BufferedImage halfHeartMImage;
	BufferedImage fullHeartMImage;
	
	public boolean messageOn = false;
	public boolean messageOnBis = false;
	public String message = "";
	public String messageBis = "";
	int messageCounter = 0;
	int messageCounter1 = 0;
	public boolean gameFinish = false;
	public boolean menuOn = false;
	public boolean menuCommand = false;
	public boolean displayInventory = false;
	public boolean displayTips = false;
	public String messageType;
	public String messageType2;
	public int lastMonsterX;
	public int lastMonsterY;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_30 = new Font("Arial", Font.PLAIN, 30);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
		
		try {
			titleBackgroundImage = ImageIO.read(getClass() .getResourceAsStream("/background/Title_bg.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Player HP
		OBJ_AlmostEmptyHeart aEHeart = new OBJ_AlmostEmptyHeart();
		almostEmptyHeartImage = aEHeart.image;
		OBJ_AlmostFullHeart aFHeart = new OBJ_AlmostFullHeart();
		almostFullHeartImage = aFHeart.image;
		OBJ_EmptyHeart emptyHeart = new OBJ_EmptyHeart();
		emptyHeartImage = emptyHeart.image;
		OBJ_HalfHeart halfHeart = new OBJ_HalfHeart();
		halfHeartImage = halfHeart.image;
		OBJ_FullHeart fullHeart = new OBJ_FullHeart();
		fullHeartImage = fullHeart.image;
		
		// Monster HP
		OBJ_AlmostEmptyHeartM aEHeartM = new OBJ_AlmostEmptyHeartM();
		almostEmptyHeartMImage = aEHeartM.image;
		OBJ_AlmostFullHeartM aFHeartM = new OBJ_AlmostFullHeartM();
		almostFullHeartMImage = aFHeartM.image;
		OBJ_EmptyHeart emptyHeartM = new OBJ_EmptyHeart();
		emptyHeartMImage = emptyHeartM.image;
		OBJ_HalfHeartM halfHeartM = new OBJ_HalfHeartM();
		halfHeartMImage = halfHeartM.image;
		OBJ_FullHeartM fullHeartM = new OBJ_FullHeartM();
		fullHeartMImage = fullHeartM.image;
		
	}
	
	public void draw(Graphics2D g2) {
		
		//Page de titre
		if (gp.titleScreen == true) {
			displayTitleScreen(g2);	
			}
		else if (gp.gameOver == true && gp.titleScreen == false) {
			displayGameOverScreen(g2);
			}
		else if (gameFinish == true) {
			displayWinnerScreen(g2);
			}
		else if (gp.titleScreen==false && gp.gameOver == false) { //Play Screen
			
				displayKeyCounter(g2); //Show number of keys currently owned
				displayPlayerHp(g2); //Show hp of the player	
				displayMonsterHp(g2); // Show hp monster focused by player
			
			if (menuOn == true) {
				displayMenu(g2);
			}	
			if (menuCommand == true) {
				displayMenuCommand(g2);
			}	
			if (displayInventory == true) {
				displayInventory(g2);
			}			
			//Action in game requesting a message display
			if (messageOn == true ) {			
				displayMessage(g2);
			}
			if (messageOnBis == true) {
				displayMessage2(g2);
			}

		}
	}
	
	public void displayKeyCounter(Graphics2D g2) {
		g2.setFont(arial_30);
		g2.setColor(Color.white);
		g2.drawImage(keyImage,gp.TILE_SIZE/4, gp.TILE_SIZE/6,36,36,null);
		g2.drawString("x "+gp.player.hasKey, 54, 40); // y correspond à la base du texte !
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void showMessage2(String text) {
		messageBis = text;
		messageOnBis = true;
	}

	public void displayMessage(Graphics2D g2) {
		switch(messageType) {
		case "Key":
			g2.setFont(g2.getFont().deriveFont(40f));
			g2.drawString(message, GamePanel.TILE_SIZE/2, GamePanel.SCREEN_HEIGHT-GamePanel.TILE_SIZE/2);
			break;
		case "DodgeUp":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.yellow);
			g2.drawString(message, gp.player.screenX-20, gp.player.screenY-10);
			break;
		case "AttackUp":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.red);
			g2.drawString(message, gp.player.screenX-20, gp.player.screenY-10);
			break;
		case "DefenseUp":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.blue);
			g2.drawString(message, gp.player.screenX-20, gp.player.screenY-10);
			break;
		case "EnnemyDodged":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.red);
			try{
				g2.drawString(message, gp.player.nextCase.whoIsHere.screenX-7, gp.player.nextCase.whoIsHere.screenY-10);
			}catch(Exception e) {
				
			}
		case "AttackOnMonsterPassed":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.green);
			if(gp.player.nextCase.whoIsHere != null) {
				lastMonsterX=gp.player.nextCase.whoIsHere.screenX;
				lastMonsterY=gp.player.nextCase.whoIsHere.screenY;
			}
			g2.drawString(message, lastMonsterX+GamePanel.TILE_SIZE/4, lastMonsterY-10);
			//System.out.println(lastMonsterX+"  "+lastMonsterY);
			break;
		}
		
		messageCounter++;
		
		if (messageCounter> gp.FPS*2) {
			messageCounter = 0;
			messageOn = false;
		}

	}
	
	public void displayMessage2(Graphics2D g2) {
		switch(messageType2) {
		case "PlayerDodged":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.green);
			g2.drawString(message, gp.player.screenX-7, gp.player.screenY-10);
			break;
		case "AttackOnPlayerPassed":
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
			g2.setColor(Color.red);
			g2.drawString(message, gp.player.screenX+GamePanel.TILE_SIZE/3, gp.player.screenY-10);
			break;

		}
		
		messageCounter1++;
		
		if (messageCounter1> gp.FPS*2) {
			messageCounter1 = 0;
			messageOnBis = false;
		}

	}
	
	public int textCenteredX(String text, Graphics2D g2) {
		int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return GamePanel.SCREEN_WIDTH/2 - textLength/2;
	}
	
	public String getItemTipsText(SuperObject o) {
			String classe = o.getClass().getSimpleName();
			String message = "";
			switch(classe) {
			case "OBJ_Shield":
				message = "Defense +1 \nOld basic wooden shield. \nStill usable !";
				break;
			case "OBJ_Key":
				message =  "Can this rusty key really be \nusefull ?";
				break;
			case "OBJ_Sword":
				message =  "Attack +1 \nSteel aways make you feel \nstronger !";
				break;
			case "OBJ_Boots":
				message =  "Dodge +1% \nHarder, Better, Faster, \nStronger !";
				break;
			}
			return message;
	}
	
	public void displayMenuCommand(Graphics2D g2) {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Command Menu";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Command Display
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
		
		text = "UP = Z";
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*2;
			y = GamePanel.TILE_SIZE*6;
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
	
		text = "DOWN = S";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*2;
			y = (int)(GamePanel.TILE_SIZE*7);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
		
		text = "LEFT = Q";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*2;
			y = (int)(GamePanel.TILE_SIZE*8);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
			
		text = "RIGHT = D";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*2;
			y = (int)(GamePanel.TILE_SIZE*9);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
			
		text = "WAIT = W";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*2;
			y = (int)(GamePanel.TILE_SIZE*10);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
			
		text = "UN/PAUSE MUSIC = P";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*6;
			y = (int)(GamePanel.TILE_SIZE*6);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
			
		text = "INVENTORY = I";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*6;
			y = (int)(GamePanel.TILE_SIZE*7);
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
			
			
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
		text = "ESC TO QUIT";				
			g2.setColor(Color.black);
			x = GamePanel.TILE_SIZE*(GamePanel.MAX_SCREEN_COL-4);
			y = GamePanel.TILE_SIZE*1;
			g2.drawString(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x-4, y-4);
		}
	
	public void displayTitleScreen(Graphics2D g2) {
		g2.drawImage(titleBackgroundImage,0,0,null);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "Mushrooms";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Start Game on Title Screen
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		text = "Start Game";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*6;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		// cursor
		if (gp.keyH.commandNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		text = "Commands";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int) (GamePanel.TILE_SIZE*7.5);
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		// cursor
		if (gp.keyH.commandNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		// Quit on Title Screen
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		text = "Quit";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*9;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		// cursor
		if (gp.keyH.commandNum == 2) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
	
		if (menuCommand == true) {
			g2.setColor(Color.black);
			g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
			g2.drawImage(titleBackgroundImage,0,0,null);
			displayMenuCommand(g2);
		}
	}		
	
	public void displayGameOverScreen(Graphics2D g2) {
		//Screen GameOver
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Game Over";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Part Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		
		text = "Retry";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*6;
		g2.drawString(text, x, y);
		
		//move the cursor ">" top and bottom follow if player push "Z" or "S"
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		
		//Part Quit				
		text = "Quit";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int)(GamePanel.TILE_SIZE*7.5);
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
	}
	
	public void displayWinnerScreen(Graphics2D g2) {
		//Screen Winner
		gp.stopMusic();
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Congratulation !";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Part Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		
		text = "Retry";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*6;
		g2.drawString(text, x, y);
		
		//move the cursor ">" top and bottom follow if player push "Z" or "S"
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		
		//Part Quit				
		text = "Quit";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int)(GamePanel.TILE_SIZE*7.5);
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
	}
	
	public void displayPlayerHp(Graphics2D g2) {
		double ratioHP = ((double)gp.player.hp/(double)gp.player.hpMax)*100;

		if (ratioHP>75) {
			g2.drawImage(fullHeartImage,gp.TILE_SIZE*4,2,36,36,null);
		}else if (ratioHP>50) {
			g2.drawImage(almostFullHeartImage,gp.TILE_SIZE*4,2,36,36,null);
		}else if (ratioHP>25) {
			g2.drawImage(halfHeartImage,gp.TILE_SIZE*4,2,36,36,null);
		}else if (ratioHP>5) {
			g2.drawImage(almostEmptyHeartImage,gp.TILE_SIZE*4,2,36,36,null);
		}else if (ratioHP>0){
			g2.drawImage(emptyHeartImage,gp.TILE_SIZE*4,2,36,36,null);
		}
	}
	
	public void displayMonsterHp(Graphics2D g2) {
		if (gp.player.nextCase != null && gp.player.nextCase.whoIsHere != null) {
			double ratioHPMonster = ((double)gp.player.nextCase.whoIsHere.hp/(double)gp.player.nextCase.whoIsHere.hpMax)*100;
			if (ratioHPMonster>75) {
				g2.drawImage(fullHeartMImage,gp.TILE_SIZE*(gp.MAX_SCREEN_COL-4),2,36,36,null);
			}else if (ratioHPMonster>50) {
				g2.drawImage(almostFullHeartMImage,gp.TILE_SIZE*(gp.MAX_SCREEN_COL-4),2,36,36,null);
			}else if (ratioHPMonster>25) {
				g2.drawImage(halfHeartMImage,gp.TILE_SIZE*(gp.MAX_SCREEN_COL-4),2,36,36,null);
			}else if (ratioHPMonster>5) {
				g2.drawImage(almostEmptyHeartMImage,gp.TILE_SIZE*(gp.MAX_SCREEN_COL-4),2,36,36,null);
			}else if (ratioHPMonster>0){
				g2.drawImage(emptyHeartMImage,gp.TILE_SIZE*(gp.MAX_SCREEN_COL-4),2,36,36,null);
			}
		}
	}

	public void displayMenu(Graphics2D g2) {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Menu";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Part Resume
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		
		text = "Resume";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*6;
		g2.drawString(text, x, y);
		
		//move the cursor ">" top and bottom follow if player push "Z" or "S"
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		
		// Partie Commands
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		
		text = "Commands";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int)(GamePanel.TILE_SIZE*7.5);
		g2.drawString(text, x, y);
		
		//move the cursor ">" top and bottom follow if player push "Z" or "S"
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		
		//Part Retry			
		text = "Retry";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int)(GamePanel.TILE_SIZE*9);
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 2) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
		
		// Partie Quit
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
		
		text = "Quit";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = (int) (GamePanel.TILE_SIZE*10.5);
		g2.drawString(text, x, y);
		
		//move the cursor ">" top and bottom follow if player push "Z" or "S"
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		if (gp.keyH.commandNum == 3) {
			g2.setColor(Color.black);
			g2.drawString(">", x-40, y);
			g2.setColor(Color.white);
			g2.drawString(">", x-40, y-4);
		}
	}

	public void displayInventory(Graphics2D g2) {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);
		
		String text;
		int x;
		int y;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Inventory";
		
		g2.setColor(Color.black);
		x = textCenteredX(text,g2);
		y = GamePanel.TILE_SIZE*3;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		
		//DRAW INVENTORY

		int x1 = (int) (GamePanel.TILE_SIZE*(1*1.5));
		int y1 = GamePanel.TILE_SIZE*4;
		
		for (int i = 0; i < gp.player.inventaire.size();i++) {
			if (x1 > GamePanel.TILE_SIZE*(GamePanel.MAX_SCREEN_COL-5)) {
				x1 = (int) (GamePanel.TILE_SIZE*(2*1.5));
				y1 = (int) (y1 + GamePanel.TILE_SIZE*1.5);
			}
			else {
				x1 = (int) (x1 + (GamePanel.TILE_SIZE*1.5));
			}
			g2.drawImage(gp.player.inventaire.get(i).image,x1, y1,36,36,null);
			
			Stroke stroke1 = new BasicStroke(2f);
			 
			g2.setColor(Color.WHITE);
			g2.setStroke(stroke1);
			int width = (int) (GamePanel.TILE_SIZE*1.2);
			int height = (int) (GamePanel.TILE_SIZE*1.2);
			int arcWidth = 10;
			int arcHeight = 10;
			//DRAW SQUARE AROUND THE ITEM SELECTED 
			if (gp.keyH.commandNum == i) {
				g2.drawRoundRect(x1, y1, width, height, arcWidth, arcHeight);
				// IF ENTER PRESSED, TIPS SHOWN
				if (displayTips == true) {
					// GET ITEM DESCRIPTION
					String textItem = getItemTipsText(gp.player.inventaire.get(gp.keyH.commandNum));
					//SET TIPS WINDOW
					int x2 = x1 - 80;
					int y2 = y1 - 100;
					
					g2.setColor(Color.black);
					g2.fillRoundRect(x2, y2,210, 80,arcWidth, arcHeight);
					g2.setColor(Color.white);
					g2.drawRoundRect(x2, y2, 210, 80, arcWidth, arcHeight);
					g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15f));
					
					for (String line : textItem.split("\n")) {
						g2.drawString(line, x2+10, y2+20);
						y2+=25;
					}
				}
			}
		}
	}
}