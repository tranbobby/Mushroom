package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Crabe extends NewMonster {
	
	
	public Crabe(GamePanel gp) {
		this.gp = gp;
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.height = GamePanel.TILE_SIZE;
		solidArea.width = GamePanel.TILE_SIZE;
		direction="down";
		speed = GamePanel.TILE_SIZE;
		getMonsterImage();
		getMonsterDieImage();
		hpMax=3;
		hp = 3;
		attack = 2;
		defense = 2;
	}

	
	public void getMonsterImage() {
		up1 = setupSprite("/monster/Crab5.png");
		up2 = setupSprite("/monster/Crab6.png");
		down1 = setupSprite("/monster/Crab1.png");
		down2 = setupSprite("/monster/Crab2.png");
		left1 = setupSprite("/monster/Crab3.png");
		left2 = setupSprite("/monster/Crab4.png");
		right1 = setupSprite("/monster/Crab7.png");
		right2 = setupSprite("/monster/Crab8.png");
	}
	
	public void getMonsterDieImage() {
		dyingUp1 = setupSprite("/monster/dying/crab_up_dying_1.png");
		dyingUp2 = setupSprite("/monster/dying/crab_up_dying_2.png");
		dyingDown1 = setupSprite("/monster/dying/crab_down_dying_1.png");
		dyingDown2 = setupSprite("/monster/dying/crab_down_dying_2.png");
		dyingLeft1 = setupSprite("/monster/dying/crab_left_dying_1.png");
		dyingLeft2 = setupSprite("/monster/dying/crab_left_dying_2.png");
		dyingRight1 = setupSprite("/monster/dying/crab_right_dying_1.png");
		dyingRight2 = setupSprite("/monster/dying/crab_right_dying_2.png");
	}
}
