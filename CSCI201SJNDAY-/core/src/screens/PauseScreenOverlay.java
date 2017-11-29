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

public class PauseScreenOverlay implements Screen {
	
	Sprite pauseIcon;
	
	//Fonts for HUD
	BitmapFont font;
	
	public PauseScreenOverlay() {
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter);
		
		pauseIcon = new Sprite(new Texture(Gdx.files.internal("pauseButtonMono.png")));
	}
	
	public void draw(SpriteBatch batch) {

		batch.draw(pauseIcon, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		font.setColor(Color.WHITE);
		font.draw(batch, "Resume Game", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 50);

		font.draw(batch, "Shop", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 100);

		font.draw(batch, "Quit Game", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 150);
		
		
	}
	
	public Boolean resumeButtonJustPressed() {
		//Check for click input
		if(Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x >= 510 && x <= 650 && y >= 427 && y <= 446) {
				return false; //resume button was pressed
			}
		}
		return true;
	}
	
	public Boolean quitButtonJustPressed() {
		if(Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x >= 509 && x <= 621 && y >= 527 && y <= 548) {
				return true; //quit button was pressed
			}
		}
		return false;
	}
	
	public Boolean shopButtonJustPressed() {
		if(Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x >= 509 && x <= 561 && y >= 478 && y <= 496) {
				return true; //shop button was pressed
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
