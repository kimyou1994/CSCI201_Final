package gameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

public class Base {
	private double restoreHealthPercentage;
	private float rotation, velocity;
	private Vector2 position;
	private ShootsAndLooters mGame;
	private Sprite design;
	private int range; //the range of healing
	private int maxHealedHealth; //the base will only restore the health up to this threshold
	
	public Base(ShootsAndLooters mGame) {
		setRestoreHealthPercentage(0.50);
		setmGame(mGame);
		setPosition(new Vector2(700,200));
		setVelocity(0.0f);
		setRotation(MathUtils.PI);
		setRange(70);
		setMaxHealedHealth(50);
		
		Sprite baseSprite = new Sprite(new Texture(Gdx.files.internal("baseArea.png")));
		baseSprite.setSize(140f, 140f);
		baseSprite.setCenter(getPosition().x, getPosition().y);
		baseSprite.setOriginCenter();
		setDesign(baseSprite);
	}
	
	//Called each frame
	//The base moves freely through space
	//Edge detection to make sure it doesn't go off the screen
	public void move() {
		//Called each frame, to restore the user's health if in range
		restoreHealth();
		
		float currVelocity = 0.0f; 
		float currRotation = getRotation();
		
		//Current velocity will always be the same
		currVelocity = 20.0f * Gdx.graphics.getDeltaTime();
		
		//Set the base's rotation +90 degrees if it tries to bounce off the screen
		float baseXPosition = getPosition().x;
		float baseYPosition = getPosition().y;
		
		if (baseXPosition <= 0 || baseXPosition >= 1000 || baseYPosition <= 0 || baseYPosition >= 734) {
			currRotation += MathUtils.PI / 2; //rotate +90 degrees
		}
		
		Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation),MathUtils.sin(currRotation));
		setRotation(currRotation); //adjust rotation
		Vector2 movement = forwardUnitVector.scl(currVelocity);
		setPosition(getPosition().add(movement)); //set new position and new velocity
		setVelocity(currVelocity);
		Sprite newSprite = getDesign();
		newSprite.setCenter(getPosition().x, getPosition().y);
		setDesign(newSprite);
	}
	
	public void restoreHealth() {
		UserShip userShip = mGame.getPlayScreen().player;
		
		Vector2 playerPosition = userShip.getPosition();
		Vector2 basePosition = getPosition();
		
		float playerXPosition = playerPosition.x;
		float playerYPosition = playerPosition.y;
		float baseXPosition = basePosition.x;
		float baseYPosition = basePosition.y;
		
		if (playerXPosition >= baseXPosition - range && playerXPosition <= baseXPosition + range && 
				playerYPosition >= baseYPosition - range && playerYPosition <= baseYPosition + range) {
			if (userShip.getHealth() < maxHealedHealth) {
				userShip.setHealth(userShip.getHealth()+1);
			}
		}
	}

	public ShootsAndLooters getmGame() {
		return mGame;
	}

	public void setmGame(ShootsAndLooters mGame) {
		this.mGame = mGame;
	}

	public double getRestoreHealthPercentage() {
		return restoreHealthPercentage;
	}

	public void setRestoreHealthPercentage(double restoreHealthPercentage) {
		this.restoreHealthPercentage = restoreHealthPercentage;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Sprite getDesign() {
		return design;
	}

	public void setDesign(Sprite design) {
		this.design = design;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getMaxHealedHealth() {
		return maxHealedHealth;
	}

	public void setMaxHealedHealth(int maxHealedHealth) {
		this.maxHealedHealth = maxHealedHealth;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
