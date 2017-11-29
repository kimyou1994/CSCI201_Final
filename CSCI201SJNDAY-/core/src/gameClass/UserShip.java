package gameClass;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

import screens.GameplayScreen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Laser;

public class UserShip extends Ship{
	private float armor;
	private float attackTimer;
	private boolean laserShown;
	private float shownTimer;
	private UserData data;
	private Upgrade items;
	private EnemyShip currOpponent;
	private Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
	
	//Laser members
	private Texture texLaserS1;
	private Texture texLaserS2;
	private Texture texLaserM1;
	private Texture texLaserM2;
	private Texture texLaserE1;
	private Texture texLaserE2;
	private Sprite spriteLaserS1;
	private Sprite spriteLaserS2;
	private Sprite spriteLaserM1;
	private Sprite spriteLaserM2;
	private Sprite spriteLaserE1;
	private Sprite spriteLaserE2;
	private Laser laser1;
	
	public UserShip(GameplayScreen game) {
		super(game);
		attackTimer = 0.0f;
		shownTimer = 0.0f;
		data = game.mGame.getUsrData();
		//Make Items
		game.mGame.makeItems(new Upgrade());
		this.items = game.mGame.getItems();
		laserShown = false;
		setPosition(new Vector2(512,100)); //Set it to a starting position, near the center bottom of the screen
		setVelocity(0.0f); //Initialize velocity to 0
		setRotation(MathUtils.PI / 2); //Initialize rotation to pointing up
		Sprite shipSprite = new Sprite((new Texture(Gdx.files.internal("shipStatic.png")))); //Start w/ a static sprite
		shipSprite.setSize(50.0f, 50.0f); //Set size of the sprite
		shipSprite.setCenter(getPosition().x, getPosition().y); //Set sprite position to ship position for drawing
		shipSprite.setRotation(getRotation()); //Set sprite rotaiton to ship rotation
		shipSprite.setOriginCenter(); //Set the "rotational origin" of the sprite to the center of its image
		setDesign(shipSprite); //set the Sprite design to this sprite
		
		//Laser setup
		this.texLaserS1 = new Texture(Gdx.files.internal("data/beamstart1.png"));
		this.texLaserS2 = new Texture(Gdx.files.internal("data/beamstart2.png"));
		this.texLaserM1 = new Texture(Gdx.files.internal("data/beammid1.png"));
		this.texLaserM2 = new Texture(Gdx.files.internal("data/beammid2.png"));
		this.texLaserE1 = new Texture(Gdx.files.internal("data/beamend1.png"));
		this.texLaserE2 = new Texture(Gdx.files.internal("data/beamend2.png"));
		this.spriteLaserS1 = new Sprite(this.texLaserS1);
		this.spriteLaserS2 = new Sprite(this.texLaserS2);
		this.spriteLaserM1 = new Sprite(this.texLaserM1);
		this.spriteLaserM2 = new Sprite(this.texLaserM2);
		this.spriteLaserE1 = new Sprite(this.texLaserE1);
		this.spriteLaserE2 = new Sprite(this.texLaserE2);
		this.laser1 = new Laser();
		laser1.begin1 = this.spriteLaserS1;
		laser1.begin2 = this.spriteLaserS2;
		laser1.mid1 = this.spriteLaserM1;
		laser1.mid2 = this.spriteLaserM2;
		laser1.end1 = this.spriteLaserE1;
		laser1.end2 = this.spriteLaserE2;
		laser1.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
		laser1.color = Color.BLUE;
		laser1.degrees = 0;
	}

	public UserData getData() {
		return data;
	}
	public void setData(UserData data) {
		this.data = data;
	}
	public float getArmor() {
		return armor;
	}

	public void setArmor(float armor) {
		this.armor = armor;
	}
	
	public void upgradeShip() {
		return;
	}
	
	public Boolean buyHealthUpgrade() {
		int currentLevel = data.getArmorLevel();
		if (currentLevel == 10) {
			return false;
		}
		ArmorUpgrade next = items.getHealth(currentLevel + 1);
		if (data.getCurrentMoney() >= next.getPrice()) {
			int money = data.getCurrentMoney();
			data.setCurrentMoney(money - next.getPrice());
			data.setArmorLevel(++currentLevel);
			setMaxHealth(next.getHealth());
			setHealth(next.getHealth());
			return true;
		}
		return false;
	}
	
	public Boolean buyWeaponUpgrade() {
		int currentLevel = data.getWeaponLevel();
		if (currentLevel == 10) {
			return false;
		}
		WeaponUpgrade next = items.getWeapon(currentLevel + 1);
		if (data.getCurrentMoney() >= next.getPrice()) {
			int money = data.getCurrentMoney();
			data.setCurrentMoney(money - next.getPrice());
			data.setWeaponLevel(++currentLevel);
			System.out.println(next.getAttackDamage());
			if (getAttack() < next.getAttackDamage()) {
				setAttack(next.getAttackDamage());
			}
			if (getAttackSpeed() < next.getAttackSpeed()) {
				setAttackSpeed(next.getAttackSpeed());
			}
			if (getAttackRange() < next.getNewRange()) {
				setAttackRange(next.getNewRange());
			}
			return true;
		}
		return false;
		
	}

	public Laser shoot(Vector<EnemyShip> enemys) {
		Vector2 playerPos = new Vector2(getDesign().getX()+25, getDesign().getY()+30);
		float closestDist = Float.MAX_VALUE;
		EnemyShip closestEnemy = null;
		for(EnemyShip es : enemys) {
			if(!es.getExplode()) {
				Vector2 enemyPos = new Vector2(es.getPosition().x, es.getPosition().y);
				float dist = playerPos.dst(enemyPos);
				if(dist < closestDist) {
					closestDist = dist;
					closestEnemy = es;
				}
			}
		}
		
		Vector2 closestPos = new Vector2(closestEnemy.getDesign().getX(), closestEnemy.getDesign().getY());
		//System.out.println("Closest dist: "+playerPos.dst(closestPos));
		if(playerPos.dst(closestPos) < getAttackRange()) {
			currOpponent = closestEnemy;
			attackTimer -= Gdx.graphics.getDeltaTime();
			Vector2 oldPos = new Vector2(getDesign().getX()-6, getDesign().getY()-6);
			laser1.position = oldPos.add(new Vector2(MathUtils.cos(getRotation()),MathUtils.sin(getRotation())).scl(30.0f));
			float targetAngle = (float) (MathUtils.atan2(closestPos.y - laser1.position.y, closestPos.x - laser1.position.x) * MathUtils.radDeg);
			laser1.distance = playerPos.dst(closestPos)/1.2f;
			laser1.degrees = targetAngle-90.0f;
			if(attackTimer < 0.0f && !laserShown) {
				attackTimer = 2.0f;
				laserShown = true;
				closestEnemy.setHealth(closestEnemy.getHealth() - this.getAttack());
				if(closestEnemy.getHealth() <= 0.0f) {
					if(!closestEnemy.getExplode()) {
						data.setCurrentMoney(data.getCurrentMoney() + closestEnemy.getLoot());
						closestEnemy.setExplode(true);
						explosionSound.play(2.0f);
					}
				}
				return laser1;
			}
			else if(laserShown) {
				shownTimer += Gdx.graphics.getDeltaTime();
				if(shownTimer > .5f) {
					laserShown = false;
					shownTimer = 0.0f;
					attackTimer = 2.0f;
					return null;
				}
				return laser1;
			}
		}
		else currOpponent = null;
		return null;
	}
	
	//Takes in nothing, and returns nothing. Called every frame
	//Reads from WASD to add rotation/velocity
	public void move() {
		float currVelocity = 0.0f; 
		float currRotation = getRotation();
		
		//Reading keyboard input from the user for this frame

		//THRUST (FORWARD, BACKWARD, OR NEITHER)
		//Note: we multiply by deltaTime if speed is non-zero, so the ship has same speed regardless of framerate
		if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S)) currVelocity = 0.0f; //If both W and S are pressed, the current velocity for the frame is 0
		else if(Gdx.input.isKeyPressed(Input.Keys.W)) currVelocity = 100.0f*Gdx.graphics.getDeltaTime(); //If only W is pressed, we're moving forward
		else if(Gdx.input.isKeyPressed(Input.Keys.S)) currVelocity = -50.0f*Gdx.graphics.getDeltaTime(); //If only S is pressed, we're going back (at half the speed of forward thrusters)
		else currVelocity = 0.0f; //Otherwise, neither are pressed, so we don't want to move
		
		//ROTATION (CLOCKWISE OR COUNTER-CLOCKWISE)
		if(Gdx.input.isKeyPressed(Input.Keys.A)) currRotation += 1.5f*Gdx.graphics.getDeltaTime(); //Adds current rotation if A is pressed
		if(Gdx.input.isKeyPressed(Input.Keys.D)) currRotation -= 1.5f*Gdx.graphics.getDeltaTime(); //Subtracts current rotation if D is pressed
		//NOTE: If BOTH are pressed, net change will be 0
		
		//We've found user input. Now we calculate movement/rotation changes
		
		//First find the unit vector pointing forward from the ship, using cos and sin of the currRotation
		Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation),MathUtils.sin(currRotation));
		setRotation(currRotation); //Set the rotation variable for use next frame
		
		Vector2 movement = forwardUnitVector.scl(currVelocity); //Multiple the forward unit vector by the speed to get the vector for movement
		setPosition(getPosition().add(movement)); //Add the movement vector to the current position, and store that new position
		setVelocity(currVelocity);
		//Now we will change the sprite image/location for drawing
		Sprite newSprite = getDesign(); //Get current design
		ShootsAndLooters game = this.getmGame().mGame;
		UserData data = game.getUsrData();
		if(currVelocity > 0) newSprite = super.getThrustDesign(); //If going forward, use thrusting image
		else if(currVelocity == 0) newSprite = super.getStaticDesign(); //If not thrusting, use default image
		else newSprite = super.getReverseDesign(); //If going back, use reverse image
		newSprite.setCenter(getPosition().x, getPosition().y);
		newSprite.setRotation(MathUtils.radDeg*currRotation - 90.0f); //Set the rotation of the sprite (our default image is up and down, so we have to subtract by 90 degrees)
		setDesign(newSprite); //Set the edited sprite to the sprite for the ship
	}

	public EnemyShip getCurrOpponent() {
		return currOpponent;
	}

	public void setCurrOpponent(EnemyShip currOpponent) {
		this.currOpponent = currOpponent;
	}
	
}
