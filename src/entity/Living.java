package entity;

import java.util.Random;

public abstract class Living extends Entity{

	public void attack(Living ennemi) {
		if (this.hasAttack == false && ennemi.defense < this.attack) {
			Random random = new Random();
			int checkDodged = random.nextInt(100);
			if (ennemi.dodge >= checkDodged) {
				if (this instanceof Player) {
					gp.ui.messageType = "EnnemyDodged";
					gp.ui.showMessage("Dodge");
				}
				else {
					gp.ui.messageType2 = "PlayerDodged";
					gp.ui.showMessage2("Dodge");
				}
			}
			else {
				int PA = Math.max((this.attack-ennemi.defense),1);
				ennemi.hp = ennemi.hp - PA;
				if (this instanceof Player) {
					gp.ui.messageType = "AttackOnMonsterPassed";
					gp.ui.showMessage("-"+Integer.toString(PA));
				}
				else {
					gp.ui.messageType2 = "AttackOnPlayerPassed";
					gp.ui.showMessage2("-"+Integer.toString(PA));
				}
			}
			this.hasAttack = true;
		}
	}
	
	// Other stuff should be moved from Player and Monster to here, and some abstract 
	// methods should be put here too, but this class is still a WIP.
}
