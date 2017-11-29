package gameClass;

public class WeaponUpgrade extends Item {
	private int newRange, attackDamage, attackSpeed;
	
	public WeaponUpgrade() {
	}
	
	public int getNewRange() {
		return newRange;
	}
	
	public void setNewRange(int range) {
		newRange = range;
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public void setAttackDamage(int damage) {
		attackDamage = damage;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(int speed) {
		attackSpeed = speed;
	}
	
}
