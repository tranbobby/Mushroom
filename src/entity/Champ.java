package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Champ extends NewMonster {
	
	
	public Champ(GamePanel gp) {
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
		hpMax=100;
		hp = 10;
		attack = 1;
		defense = 1;
	}

	
	public void getMonsterImage() {
		up1 = setupSprite("/monster/Champ5.png");
		up2 = setupSprite("/monster/Champ6.png");
		down1 = setupSprite("/monster/Champ1.png");
		down2 = setupSprite("/monster/Champ2.png");
		left1 = setupSprite("/monster/Champ3.png");
		left2 = setupSprite("/monster/Champ4.png");
		right1 = setupSprite("/monster/Champ7.png");
		right2 = setupSprite("/monster/Champ8.png");
	}
	
	public void getMonsterDieImage() {
		dyingUp1 = setupSprite("/monster/dying/champ_dying_1.png");
		dyingUp2 = setupSprite("/monster/dying/champ_dying_2.png");
		dyingDown1 = setupSprite("/monster/dying/champ_dying_1.png");
		dyingDown2 = setupSprite("/monster/dying/champ_dying_2.png");
		dyingLeft1 = setupSprite("/monster/dying/champ_dying_1.png");
		dyingLeft2 = setupSprite("/monster/dying/champ_dying_2.png");
		dyingRight1 = setupSprite("/monster/dying/champ_dying_1.png");
		dyingRight2 = setupSprite("/monster/dying/champ_dying_2.png");
	}
}
