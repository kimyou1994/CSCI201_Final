package gameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Laser;
import com.mygdx.game.ShootsAndLooters;

import screens.GameplayScreen;

public class AllyShip extends Ship {

	private int loot;
	private float attackTimer;
	private boolean laserShown;
	private float shownTimer;
	private boolean isHelping;
	private int allyNum;
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
		
	public AllyShip(GameplayScreen game, float xPos, float yPos, int allyNum) {
		super(game);
		attackTimer = 0.0f;
		shownTimer = 0.0f;
		laserShown = false;
		isHelping = false;
		this.allyNum = allyNum;
		setHealth(50);
		setAttackRange(200);
		setAttack(5);
		setPosition(new Vector2(xPos,yPos)); //Set it to a starting position, near the center bottom of the screen
		setVelocity(0.0f); //Initialize velocity to 0
		setRotation(0.0f); //Initialize rotation to pointing up
		Sprite shipSprite = new Sprite((new Texture(Gdx.files.internal("allyShipStatic.png")))); //Start w/ a static sprite
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
		laser1.color = Color.GREEN;
		laser1.degrees = 0;
	}

	public int getLoot() {
		return loot;
	}

	public void setLoot(int loot) {
		this.loot = loot;
	}
	
	public Laser shoot() {
		if(isHelping) {
			EnemyShip enemy = getmGame().player.getCurrOpponent();
			if(enemy != null) {
				Vector2 enemyPos = new Vector2(enemy.getDesign().getX(), enemy.getDesign().getY());
				Vector2 thisPos = getPosition();
				attackTimer -= Gdx.graphics.getDeltaTime();
				laser1.position = new Vector2(getDesign().getX(), getDesign().getY());
				float targetAngle = (float) (MathUtils.atan2(enemyPos.y - laser1.position.y, enemyPos.x - laser1.position.x) * MathUtils.radDeg);
				laser1.distance = enemyPos.dst(thisPos)/1.2f;
				laser1.degrees = targetAngle-90.0f;
				if(attackTimer < 0.0f && !laserShown) {
					attackTimer = 2.0f;
					laserShown = true;
					enemy.setHealth(enemy.getHealth() - this.getAttack());
					if(enemy.getHealth() <= 0.0f) {
						if(!enemy.getExplode()) {
							getmGame().player.getData().setCurrentMoney(getmGame().player.getData().getCurrentMoney() + enemy.getLoot());
							enemy.setExplode(true);
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
				else return null;
			} else return null;
		}
		else return null;
	}
	
	public void move() {
		if(isHelping) {
			float currVelocity = getmGame().player.getVelocity(); 
			float currRotation = getmGame().player.getRotation();
			Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation),MathUtils.sin(currRotation));
			setRotation(currRotation); //Set the rotation variable for use next frame
			Vector2 movement = forwardUnitVector.scl(currVelocity); //Multiple the forward unit vector by the speed to get the vector for movement
			setPosition(getPosition().add(movement)); //Add the movement vector to the current position, and store that new position
			//Now we will change the sprite image/location for drawing
			Sprite newSprite = getDesign(); //Get current design
			newSprite.setCenter(getPosition().x, getPosition().y);
			//newSprite.setPosition(getPosition().x, getPosition().y); //Set the new position of the sprite for drawing
			newSprite.setRotation(MathUtils.radDeg*currRotation - 90.0f); //Set the rotation of the sprite (our default image is up and down, so we have to subtract by 90 degrees)
			setDesign(newSprite); //Set the edited sprite to the sprite for the ship
		} else {
			float currVelocity =0.0f;
			float currRotation = getRotation();
			Vector2 currPosition = getPosition();
			Vector2 userPosition = getmGame().player.getPosition();
			float distanceToUser = currPosition.dst(userPosition);
			//find the unit vector pointing forward from the ship, using cos and sin of the currRotation
			Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation+(MathUtils.PI/2)),MathUtils.sin(currRotation+(MathUtils.PI/2)));
			if(distanceToUser < 300.0f) {
				//currRotation += 1.5f*Gdx.graphics.getDeltaTime();
				// Buffer the direct angle from your bot to the opponent
				float targetAngle = 0.0f;
				if(allyNum==1) targetAngle = (float) (MathUtils.atan2(userPosition.y - currPosition.y, userPosition.x + 80 - currPosition.x) * MathUtils.radDeg);
				else targetAngle = (float) (MathUtils.atan2(userPosition.y - currPosition.y, userPosition.x - 80 - currPosition.x) * MathUtils.radDeg);
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
				if (diff > 0){
					currRotation -= 1.5f*Gdx.graphics.getDeltaTime();
				} else if (diff < 0) {
					currRotation += 1.5f*Gdx.graphics.getDeltaTime();
				}
				setRotation(currRotation);
				if(distanceToUser > 100.0f) {
					currVelocity = 100.0f*Gdx.graphics.getDeltaTime();
				}
				else setHelping(true);
			}
			else setHelping(false);
			Vector2 movement = forwardUnitVector.scl(currVelocity); //Multiple the forward unit vector by the speed to get the vector for movement
			setPosition(getPosition().add(movement)); //Add the movement vector to the current position, and store that new position
			Sprite newSprite = getDesign(); //Get current design
			newSprite.setCenter(getPosition().x, getPosition().y); //Set the new position of the sprite for drawing
			newSprite.setRotation(MathUtils.radDeg*currRotation);
			setDesign(newSprite); //Set the edited sprite to the sprite for the ship
		}
	}

	public boolean isHelping() {
		return isHelping;
	}

	public void setHelping(boolean isHelping) {
		this.isHelping = isHelping;
	}

}
