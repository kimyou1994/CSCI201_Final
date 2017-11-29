package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import gameClass.UserShip;

//A class for the heads up display that will stay fixed regardless of camera position.
//The HUD will display player health, shield strength, and money earned
//The HUD includes the pause button.
public class HUD implements Screen {
	
	private int health, shield, money;
	Sprite pauseButton;
	
	//Fonts for HUD
	BitmapFont font;
	
	public HUD() {
		health = 100;
		shield = 100;
		money = 0;
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter);
		
		pauseButton = new Sprite(new Texture(Gdx.files.internal("pauseButton.png")));
	}
	
	public void draw(SpriteBatch batch, UserShip userShip, int totalMoney) {
		//Update health, shield, and money values
		health = userShip.getHealth();
		shield = userShip.getShield();
		money = totalMoney;
		
		//Start drawing to batch
		font.setColor(Color.GREEN);
		font.draw(batch, "health: " + health, 25, 743);
		
		font.setColor(Color.YELLOW);
		font.draw(batch, "$" + money, 25, 723);
		
		batch.draw(pauseButton, 949, 693);
	}
	
	public Boolean pauseButtonJustPressed() {
		//Check for click input
		if(Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x >= 949 && x <= 999 && y >= 27 && y <= 75) {
				return true;	 //pause button was pressed
			}
		}
		return false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
