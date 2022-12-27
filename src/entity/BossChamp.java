package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class BossChamp extends NewMonster {
	
	
	public BossChamp(GamePanel gp) {
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
		hp = 100;
		attack = 6;
		defense = 2;
	}

	
	public void getMonsterImage() {
		up1 = setupSprite("/monster/BossUp1.png");
		up2 = setupSprite("/monster/BossUp2.png");
		down1 = setupSprite("/monster/BossDown1.png");
		down2 = setupSprite("/monster/BossDown2.png");
		left1 = setupSprite("/monster/BossLeft1.png");
		left2 = setupSprite("/monster/BossLeft2.png");
		right1 = setupSprite("/monster/BossRight1.png");
		right2 = setupSprite("/monster/BossRight2.png");
	}
	
	public void getMonsterDieImage() {
		dyingUp1 = setupSprite("/monster/dying/boss_vertical_dying_1.png");
		dyingUp2 = setupSprite("/monster/dying/boss_vertical_dying_2.png");
		dyingDown1 = setupSprite("/monster/dying/boss_vertical_dying_1.png");
		dyingDown2 = setupSprite("/monster/dying/boss_vertical_dying_2.png");
		dyingLeft1 = setupSprite("/monster/dying/boss_horizontal_dying_1.png");
		dyingLeft2 = setupSprite("/monster/dying/boss_horizontal_dying_2.png");
		dyingRight1 = setupSprite("/monster/dying/boss_horizontal_dying_1.png");
		dyingRight2 = setupSprite("/monster/dying/boss_horizontal_dying_2.png");
	}
}
