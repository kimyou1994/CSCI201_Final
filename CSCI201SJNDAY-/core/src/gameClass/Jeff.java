package gameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

public class Jeff {
	private Sprite design;
	private float rotation,velocity;
	private Vector2 position;
	private ShootsAndLooters mGame;
	
	public Jeff(ShootsAndLooters game) {
		setmGame(game);
		setPosition(new Vector2(200,100));
		setVelocity(0.0f);
		setRotation(MathUtils.PI / 2);
		
		Sprite jeffSprite = new Sprite(new Texture(Gdx.files.internal("jeff.png")));
		jeffSprite.setSize(150.0f, 150.0f);
		jeffSprite.setCenter(getPosition().x, getPosition().y);
		jeffSprite.setRotation(getRotation());
		jeffSprite.setOriginCenter();
		setDesign(jeffSprite);
	}
	
	public void move() {
		float currVelocity = 0.0f; 
		float currRotation = getRotation();
		
		//move very slowly
		currVelocity = 5.0f * Gdx.graphics.getDeltaTime();
		currRotation -= 0.2f * Gdx.graphics.getDeltaTime(); //rotate very slowly
		
		//Update position vector
		Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation),MathUtils.sin(currRotation));
		setRotation(currRotation); //adjust rotation
		Vector2 movement = forwardUnitVector.scl(currVelocity);
		setPosition(getPosition().add(movement)); //set new position and new velocity
		setVelocity(currVelocity);
		Sprite newSprite = getDesign();
		newSprite.setCenter(getPosition().x, getPosition().y);
		newSprite.setRotation(MathUtils.radDeg*currRotation - 90.0f);
		setDesign(newSprite);
	}
	
	public void moveInSquadFormation() {
		float currVelocity = 0.0f;
		float currRotation = getRotation();
		
		currVelocity = 20.0f * Gdx.graphics.getDeltaTime();
		
		Vector2 forwardUnitVector = new Vector2(MathUtils.cos(currRotation),MathUtils.sin(currRotation));
		Vector2 movement = forwardUnitVector.scl(currVelocity);
	}
	
	public Sprite getDesign() {
		return design;
	}
	public void setDesign(Sprite design) {
		this.design = design;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public ShootsAndLooters getmGame() {
		return mGame;
	}
	public void setmGame(ShootsAndLooters mGame) {
		this.mGame = mGame;
	}
	
}
