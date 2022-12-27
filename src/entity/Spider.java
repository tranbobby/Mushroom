package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Spider extends NewMonster {
	
	
	public Spider(GamePanel gp) {
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
		hpMax = 5;
		hp = 5;
		attack = 2;
		defense = 1;
	}

	
	public void getMonsterImage() {
		up1 = setupSprite("/monster/Spider5.png");
		up2 = setupSprite("/monster/Spider6.png");
		down1 = setupSprite("/monster/Spider1.png");
		down2 = setupSprite("/monster/Spider2.png");
		left1 = setupSprite("/monster/Spider3.png");
		left2 = setupSprite("/monster/Spider4.png");
		right1 = setupSprite("/monster/Spider7.png");
		right2 = setupSprite("/monster/Spider8.png");
	}

	public void getMonsterDieImage() {
		dyingUp1 = setupSprite("/monster/dying/spider_up_dying_1.png");
		dyingUp2 = setupSprite("/monster/dying/spider_up_dying_2.png");
		dyingDown1 = setupSprite("/monster/dying/spider_down_dying_1.png");
		dyingDown2 = setupSprite("/monster/dying/spider_down_dying_2.png");
		dyingLeft1 = setupSprite("/monster/dying/spider_left_dying_1.png");
		dyingLeft2 = setupSprite("/monster/dying/spider_left_dying_2.png");
		dyingRight1 = setupSprite("/monster/dying/spider_right_dying_1.png");
		dyingRight2 = setupSprite("/monster/dying/spider_right_dying_2.png");
	}
}
