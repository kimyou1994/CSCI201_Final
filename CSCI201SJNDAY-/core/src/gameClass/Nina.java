package gameClass;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

public class Nina {
	private Sprite design;
	private Sprite speechBubbleDesign;
	BitmapFont font;
	private Vector2 position;
	private Vector2 bubblePosition;
	private ShootsAndLooters mGame;
	private Vector<String> phrases;
	
	public Nina(ShootsAndLooters game) {
		setmGame(game);
		setPosition(new Vector2(150,150)); 	//position Nina in the upper-right
											//corner of the Shop window
		setBubblePosition(new Vector2(600, 70));
		phrases = getPhrases();
		
		//Set image of Nina
		Sprite ninaSprite = new Sprite(new Texture(Gdx.files.internal("nina.png")));
		ninaSprite.setSize(300.0f, 300.0f);
		ninaSprite.setCenter(getPosition().x, getPosition().y);
		ninaSprite.setOriginCenter();
		setDesign(ninaSprite);
		
		//Set speech bubble image
		Sprite bubbleSprite = new Sprite(new Texture(Gdx.files.internal("speechBubble.png")));
		bubbleSprite.setSize(600f, 100f);
		bubbleSprite.setCenter(getBubblePosition().x, getBubblePosition().y);
		bubbleSprite.setOriginCenter();
		setSpeechBubbleDesign(bubbleSprite);
		
		//Generate font for speech bubble
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter);
	}
	
	public void draw(SpriteBatch batch, String phrase) {
		getDesign().draw(batch);
		getSpeechBubbleDesign().draw(batch);
		font.setColor(Color.BLACK);
		font.draw(batch, phrase, 350, 70);
	}
	
	public String getRandomPhrase() {
		int maxIndex = phrases.size();
		Random generator = new Random();
		int rn = generator.nextInt(maxIndex);
		return phrases.get(rn);
	}
	
	public Vector<String> getPhrases() {
		Vector<String> p = new Vector<String>();
		p.add("What are you doing? Buy something already!!!");
		p.add("Please buy something NOW");
		p.add("I thought our meeting was at 4:30, not 4:15");
		p.add("Have you seen all the colors you\ncan get for your ship?");
		p.add("Upgrade your weapons so you can\nfinish this game");
		p.add("I am Nina, the shopkeeper.\nPlease buy something already");
		p.add("Buy an upgrade for your ship.\nBuy it now!!!");
		return p;
	}

	public Sprite getDesign() {
		return design;
	}

	public void setDesign(Sprite design) {
		this.design = design;
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

	public Sprite getSpeechBubbleDesign() {
		return speechBubbleDesign;
	}

	public void setSpeechBubbleDesign(Sprite speechBubbleDesign) {
		this.speechBubbleDesign = speechBubbleDesign;
	}

	public Vector2 getBubblePosition() {
		return bubblePosition;
	}

	public void setBubblePosition(Vector2 bubblePosition) {
		this.bubblePosition = bubblePosition;
	}
}
