package gameClass;

import java.util.ArrayList;
import java.util.List;

public class Upgrade {
	private List<WeaponUpgrade> weapons;
	private List<ArmorUpgrade> healths;
	
	public Upgrade() {
		weapons = new ArrayList<WeaponUpgrade>();
		healths = new ArrayList<ArmorUpgrade>();
		createWeapons();
		createHealths();
	}
	
	public WeaponUpgrade getWeapon(int index) {
		return weapons.get(index);
	}
	
	public ArmorUpgrade getHealth(int index) {
		return healths.get(index);
	}
	
	public void createWeapons() {
		WeaponUpgrade lv1 = new WeaponUpgrade();
		lv1.setAttackDamage(15);
		lv1.setName("Damage 15");
		lv1.setPrice(100);
		weapons.add(lv1);
		WeaponUpgrade lv2 = new WeaponUpgrade();
		lv2.setAttackSpeed(100);
		lv2.setName("Attack Speed 100");
		lv2.setPrice(150);
		weapons.add(lv2);
		WeaponUpgrade lv3 = new WeaponUpgrade();
		lv3.setNewRange(315);
		lv3.setName("Attack Range 315");
		lv3.setPrice(200);
		weapons.add(lv3);
		for (int i = 0; i < 7; i++) {
			WeaponUpgrade weapon = new WeaponUpgrade();
			weapon.setName("Weapon " + (i + 4));
			weapon.setAttackDamage(15 + i * 5);
			weapon.setAttackSpeed(105 + i * 5);
			weapon.setNewRange(320 + i * 5);
			weapon.setPrice(250 + i * 50);
			weapons.add(weapon);
		}
	}
	
	public void createHealths() {
		for (int i = 0; i < 10; i ++) {
			ArmorUpgrade health = new ArmorUpgrade();
			String name = "Max Health " + (105 + i * 20);
			health.setName(name);
			health.setHealth(105 + i * 20);
			health.setPrice(100 + i * 50);
			healths.add(health);
		}
	}

}
