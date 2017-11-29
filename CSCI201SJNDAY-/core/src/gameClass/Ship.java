package gameClass;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Laser;

import screens.GameplayScreen;

public abstract class Ship {
	private int attack, attackRange, attackSpeed, health, shield, maxHealth;
	private float rotation,velocity;
	private Sprite design;
	private Sprite staticDesign;
	private Sprite thrustDesign;
	private Sprite reverseDesign;
	private Vector2 position;
	protected GameplayScreen mGame;
	
	public Ship(GameplayScreen game) {
		setmGame(game);
		setStaticDesign(new Sprite(game.mGame.getShipColors().getTexture(0, "static")));
		setThrustDesign(new Sprite(game.mGame.getShipColors().getTexture(0, "thrust")));
		setReverseDesign(new Sprite(game.mGame.getShipColors().getTexture(0, "reverse")));
		setMaxHealth(100);
	}
	
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		if(health > maxHealth) {
			this.health = maxHealth;
		} else {
			this.health = health;
		}
	}
	public int getShield() {
		return shield;
	}
	public void setShield(int shield) {
		this.shield = shield;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public Sprite getDesign() {
		return design;
	}
	public void setDesign(Sprite desgin) {
		this.design = desgin;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	
	public Laser shoot() {
		return null;
	}
	
	public void move() {
		return;
	}

	public GameplayScreen getmGame() {
		return mGame;
	}

	public void setmGame(GameplayScreen mGame) {
		this.mGame = mGame;
	}

	public Sprite getThrustDesign() {
		return thrustDesign;
	}

	public void setThrustDesign(Sprite thrustDesign) {
		this.thrustDesign = thrustDesign;
	}

	public Sprite getReverseDesign() {
		return reverseDesign;
	}

	public void setReverseDesign(Sprite reverseDesign) {
		this.reverseDesign = reverseDesign;
	}

	public Sprite getStaticDesign() {
		return staticDesign;
	}

	public void setStaticDesign(Sprite staticDesign) {
		this.staticDesign = staticDesign;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
