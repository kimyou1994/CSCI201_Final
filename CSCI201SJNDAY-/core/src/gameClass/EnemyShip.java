package gameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Laser;
import com.mygdx.game.ShootsAndLooters;

import screens.GameplayScreen;

public class EnemyShip extends Ship {

	private int loot;
	private float attackTimer;
	private boolean laserShown;
	private boolean spawnDestReached;
	private float shownTimer;
	private int shipNum;
	private Vector2 spawnDest;
	private boolean exploding;
	public boolean marked;
	
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
		
	public EnemyShip(GameplayScreen game, float spawnDestX, float spawnDestY, int shipNum, Vector2 spawnLoc) {
		super(game);
		this.spawnDest = new Vector2(spawnDestX,spawnDestY);
		spawnDestReached = false;
		attackTimer = 0.0f;
		shownTimer = 0.0f;
		laserShown = false;
		exploding = false;
		marked = false;
		this.setShipNum(shipNum);
		setPosition(spawnLoc);
		setHealth(100);
		setAttackRange(200);
		setAttack(5);
		setLoot(100);
		setVelocity(0.0f); //Initialize velocity to 0
		setRotation(MathUtils.PI); //Initialize rotation to pointing up
		Sprite shipSprite = new Sprite((new Texture(Gdx.files.internal("enemyShipStatic.png")))); //Start w/ a static sprite
		shipSprite.setSize(50.0f, 50.0f); //Set size of the sprite
		shipSprite.setPosition(getPosition().x, getPosition().y); //Set sprite position to ship position for drawing
		shipSprite.setRotation(MathUtils.radDeg*getRotation()); //Set sprite rotation to ship rotation
		shipSprite.setOriginCenter(); //Set the "rotational origin" of the sprite to the center of its image
		setDesign(shipSprite); //set the Sprite design to this sprite
		
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
		laser1.color = Color.RED;
		laser1.degrees = 0;
	}

	public int getLoot() {
		return loot;
	}

	public void setLoot(int loot) {
		this.loot = loot;
	}
	
	public Laser shoot() {
		if(getExplode()) return null;
		UserShip player = getmGame().player;
		Vector2 enemyPos = new Vector2(getDesign().getX(), getDesign().getY());
		Vector2 playerPos = new Vector2(player.getDesign().getX(), player.getDesign().getY());
		if(playerPos.dst(enemyPos) < getAttackRange()) {
			attackTimer -= Gdx.graphics.getDeltaTime();
			laser1.position = new Vector2(getDesign().getX(), getDesign().getY());
			float targetAngle = (float) (MathUtils.atan2(playerPos.y - laser1.position.y, playerPos.x - laser1.position.x) * MathUtils.radDeg);
			laser1.distance = playerPos.dst(enemyPos)/1.2f;
			laser1.degrees = targetAngle-90.0f;
			if(attackTimer < 0.0f && !laserShown) {
				attackTimer = 2.0f;
				laserShown = true;
				player.setHealth(player.getHealth() - this.getAttack());
				if (player.getHealth() <= 0) {
					mGame.gameEnd = true;
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
		return null;
	}
	
	public void move() {
		float currVelocity =0.0f;
		float currRotation = getRotation();
		Vector2 currPosition = getPosition();
		Vector2 forwardUnitVector = null;
		if(!spawnDestReached) {
			forwardUnitVector = new Vector2(MathUtils.cos(currRotation+(MathUtils.PI/2)),MathUtils.sin(currRotation+(MathUtils.PI/2)));
			float distanceToDest = currPosition.dst(spawnDest);
			float targetAngle = (float) (MathUtils.atan2(spawnDest.y - currPosition.y, spawnDest.x - currPosition.x) * MathUtils.radDeg);
			targetAngle += 90.0f; //to correct for default angle of sprite
			// Calculate the difference
			float diff = targetAngle - (currRotation*MathUtils.radDeg);
				// Make sure you turn less than 180 degrees
			while (diff < -180){
			    diff += 360;
			}
			while (diff > 180){
			    diff -= 360;
			}
			
			if(diff<0) diff+=180;
			else diff-=180;
			// Based on the difference, turn left or right. 
			if (diff < -2){
				currRotation -= 1f*Gdx.graphics.getDeltaTime();
			} else if (diff > 2) {
				currRotation += 1f*Gdx.graphics.getDeltaTime();
			}
			setRotation(currRotation);
			if(Math.abs(diff) < 2){
				if(distanceToDest > 40.0f) {
					currVelocity = 1000.0f*Gdx.graphics.getDeltaTime();
				} else {
					currVelocity = 0.0f;
					spawnDestReached = true;
				}
			}
		}
		else {
			Vector2 userPosition = getmGame().player.getPosition();
			float distanceToUser = currPosition.dst(userPosition);
			//find the unit vector pointing forward from the ship, using cos and sin of the currRotation
			forwardUnitVector = new Vector2(MathUtils.cos(currRotation+(MathUtils.PI/2)),MathUtils.sin(currRotation+(MathUtils.PI/2)));
			if(distanceToUser < 300.0f) {
				//currRotation += 1.5f*Gdx.graphics.getDeltaTime();
				// Buffer the direct angle from your bot to the opponent
				float targetAngle = (float) (MathUtils.atan2(userPosition.y - currPosition.y, userPosition.x - currPosition.x) * MathUtils.radDeg);
				targetAngle += 90.0f; //to correct for default angle of sprite
				// Calculate the difference
				float diff = targetAngle - (currRotation*MathUtils.radDeg);
					// Make sure you turn less than 180 degrees
				while (diff < -180){
				    diff += 360;
				}
				while (diff > 180){
				    diff -= 360;
				}
				
				// Based on the difference, turn left or right. 
				if(diff<0) diff+=180;
				else diff-=180;
				// Based on the difference, turn left or right. 
				if (diff < -2){
					currRotation -= 1f*Gdx.graphics.getDeltaTime();
				} else if (diff > 2) {
					currRotation += 1f*Gdx.graphics.getDeltaTime();
				}
				setRotation(currRotation);
				if(distanceToUser > 100.0f) {
					currVelocity = 50.0f*Gdx.graphics.getDeltaTime();
				}
			}
		}
		Vector2 movement = forwardUnitVector.scl(currVelocity); //Multiple the forward unit vector by the speed to get the vector for movement
		setPosition(getPosition().add(movement)); //Add the movement vector to the current position, and store that new position
		Sprite newSprite = getDesign(); //Get current design
		newSprite.setCenter(getPosition().x, getPosition().y); //Set the new position of the sprite for drawing
		newSprite.setRotation(MathUtils.radDeg*currRotation);
		setDesign(newSprite); //Set the edited sprite to the sprite for the ship
	}

	public int getShipNum() {
		return shipNum;
	}

	public void setShipNum(int shipNum) {
		this.shipNum = shipNum;
	}

	public void setExplode(boolean explode) {
		exploding = explode;
	}
	
	public boolean getExplode() {
		return exploding;
	}
}
