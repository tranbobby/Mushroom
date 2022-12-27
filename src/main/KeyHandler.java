package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

public class KeyHandler implements KeyListener{
	// This class manages the keyboard inputs of the player.
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, waitPressed;
	public int counter; // helps to limit the multi-tapped issue du to high-speed processing
	public int commandNum = 0; //Used to indentify the option selected on menus by player
	public boolean musicPaused = false;
	public GamePanel gp;
	public boolean stateWait = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	} // Not used but needed to avoid errors.

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (stateWait == false) {
		// Pauses or unpauses game's music. (available on all screens)
		if (code == KeyEvent.VK_P) {
			if (musicPaused == false) {
				gp.music.pauseMusic();
				musicPaused = true;
				}
			else {
				gp.music.unpauseMusic();
				musicPaused = false;
				}		
		}
	
		// During regular playing session, displays or hides General Menu.
		if (gp.gameOver == false && gp.ui.gameFinish == false && gp.titleScreen == false && gp.ui.menuCommand == false && gp.ui.displayInventory == false) {
			if(code == KeyEvent.VK_ESCAPE) {
				if (gp.ui.menuOn == false) {
					System.out.println(gp.objectsOnGround);
					gp.ui.menuOn = true;
				}
				else {
					gp.ui.menuOn = false;
				}
			}
		} // During regular playing session where menuCommand has been displayed
		else if (gp.ui.menuCommand == true) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.menuCommand = false;
				gp.ui.menuOn = true;
			}
		}
		
		//During regular playing session without any screen/menu displayed : regular commands
		if (gp.gameOver == false && gp.ui.gameFinish == false && gp.titleScreen == false && gp.ui.menuOn == false && gp.ui.menuCommand == false && gp.ui.displayInventory == false) {
		
			if (code == KeyEvent.VK_Z) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if (code == KeyEvent.VK_Q) {
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if (code == KeyEvent.VK_W) {
				waitPressed = true;
			}
			if (code == KeyEvent.VK_I) {
					gp.ui.displayInventory = true;
			}
			counter=1;
		} // Commands when inventory is displayed
		else if (gp.ui.displayInventory == true) {
			if (code == KeyEvent.VK_I) {
					commandNum = 0;
					gp.ui.displayInventory = false;
					gp.ui.displayTips = false;
			}// Commands to display item tool tips when inventory is displayed
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.displayTips == true) gp.ui.displayTips = false;
				else gp.ui.displayTips = true;
			}	
			// Navigates the selecting square on inventory items
			if(code == KeyEvent.VK_S) {
				commandNum+=7;
				if (commandNum >= gp.player.inventaire.size()) {
					commandNum = commandNum%7;
					}
				}
				if(code == KeyEvent.VK_Z) {
					commandNum-=7;
					if (commandNum < 0) {
						commandNum += 7+((int)(gp.player.inventaire.size()/7))*7;
						if (commandNum >= gp.player.inventaire.size()) {
							commandNum-=7;
						}
					}
				}
				if(code == KeyEvent.VK_D) {
					commandNum++;
					if (commandNum >= gp.player.inventaire.size()) {
						commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_Q) {
					commandNum--;
					if (commandNum < 0) {
						commandNum = gp.player.inventaire.size()-1;
					}
				}
			}
		
			// When the Game Over screen is displayed
			else if (gp.gameOver == true || gp.ui.gameFinish == true){
				if(code == KeyEvent.VK_S) {
					commandNum--;
					if (commandNum < 0) {
						commandNum = 1;
					}
				}
				if(code == KeyEvent.VK_Z) {
					commandNum++;
					if (commandNum > 1) {
						commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if (commandNum == 0 ) { // RETRY
						gp.reset();	
					}
					else if (commandNum == 1) { //QUIT (go titleScreen)
						gp.titleScreen=true;
						if (gp.gameOver == true) gp.gameOver=false;
						else if (gp.ui.gameFinish == true) gp.ui.gameFinish = false;
						gp.reset();
						commandNum = 0;
					}
				}
			}
			// When InGame menu is displayed
			else if (gp.ui.menuOn == true){
				if(code == KeyEvent.VK_S) {
					commandNum++;
					if (commandNum > 3) {
						commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_Z) {
					commandNum--;
					if (commandNum < 0) {
						commandNum = 3;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if (commandNum == 0 ) { // RESUME
						gp.ui.menuOn = false;
						
					}
					else if (commandNum == 1) { //COMMANDS
						gp.ui.menuCommand = true;
						gp.ui.menuOn = false;
						commandNum = 0;
					}
					else if (commandNum == 2) { //RETRY
						gp.reset();
						gp.ui.menuOn = false;
						commandNum = 0;
					}
					else if (commandNum == 3) { //QUIT (go titleScreen)
						gp.ui.menuOn = false;
						gp.titleScreen=true;
						gp.gameOver=false;
						gp.reset();
						commandNum = 0;
					}
				}
			}
			// When Title Screen is displayed
			else if (gp.titleScreen == true) {
				if(code == KeyEvent.VK_S) {
					commandNum++;
					if (commandNum > 2) {
						commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_Z) {
					commandNum--;
					if (commandNum < 0) {
						commandNum = 2;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if (commandNum == 0 ) { //START
						gp.titleScreen=false;
						
					}
					else if (commandNum == 1) { //COMMANDS
						gp.ui.menuCommand = true;
						
					}
					else if (commandNum == 2) { //QUIT
						System.exit(0);
					}
				}
			}
		} 	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode(); // renvoie le code li� � la touche press�e
		
		if (code == KeyEvent.VK_Z) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_Q) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_W) {
			waitPressed = false;
		}
		counter=0;
	} 
	
}
