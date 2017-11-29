package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ShootsAndLooters;

import gameClass.Nina;

public class StoreColorScreen extends StoreScreen implements Screen, InputProcessor {
	private SpriteBatch batch;
	private Nina nina;
	private String ninaPhrase;
	
	public StoreColorScreen(ShootsAndLooters shootsAndLooters) {
		super(shootsAndLooters);
		nina = new Nina(shootsAndLooters);
		batch = new SpriteBatch();
		ninaPhrase = nina.getRandomPhrase();
	}

	@Override
	 public boolean keyDown(int keycode) {
	    return false;
	 }
		
	 @Override
	 public boolean keyUp(int keycode) {
	    return false;
	 }
		
	 @Override
	 public boolean keyTyped(char character) {
	    return false;
	 }
		
	 @Override
	 public boolean mouseMoved(int screenX, int screenY) {
	    return false;
	 }
		
	 @Override
	 public boolean scrolled(int amount) {
	    return false;
	 }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public void show() {
		stage.clear();
		
		//Mark bought upgrades as bought
		for(int i = 0; i < 6; i++) {
			String currColor = game.getShipColors().getColorName(i);
			if(game.getUsrData().getColorIndex() == i) {
				colorUpgrades.get(i).getLabel().setText(currColor + "\nSELECTED");
			}
			else if(game.getUsrData().getColorOwned()[i]) {
				colorUpgrades.get(i).getLabel().setText(currColor + "\nOWNED");
			} else {
				colorUpgrades.get(i).getLabel().setText(currColor + "\nPrice: 200");
			}
		}
		
		//Init table
		mainTable = new Table();
		mainTable.setWidth(stage.getWidth());
		mainTable.align(Align.top);
		mainTable.top().left();
		mainTable.padTop(30).padLeft(30f).padRight(30f);
		mainTable.setPosition(0,Gdx.graphics.getHeight());
		
		//First row
		mainTable.row().height(60f);
		mainTable.add(currFunds).width(128f);
		mainTable.add().width(128f);
		mainTable.add(storeText).width(128f);
		mainTable.add().width(128f);
		ResumeButton.getLabel().setFontScale(defaultFontScale*0.90f);
		mainTable.add(ResumeButton).width(128f).height(128f/3);
		
		//Second row
		mainTable.row().padTop(15f).padBottom(15f);
		mainTable.add().width(128f).expandX();
		mainTable.add(ColorButton).width(128f).height(50f).expandX();
		mainTable.add(HealthButton).width(128f).height(50f).expandX();
		mainTable.add(WeaponButton).width(128f).height(50f).expandX();
		mainTable.add().width(128f).expandX();
		
		//Upgrades
		mainTable.row().height(128f).width(128f).padTop(15f).padBottom(15f);
		mainTable.add();
		for(int i = 0; i < 3; i++) {
			mainTable.add(colorUpgrades.get(i));
		}
		mainTable.add();
		
		//Second row of Upgrades
		mainTable.row().height(128f).width(128f).padTop(15f).padBottom(15f);
		mainTable.add();
		for(int i = 3; i < 6; i++) {
			mainTable.add(colorUpgrades.get(i));
		}
		mainTable.add();
		
		//Buy button
		mainTable.row().padTop(15f).padBottom(15f);
		mainTable.add();
		mainTable.add();
		mainTable.add(errorText);
		mainTable.add();
		mainTable.add();
		
		stage.addActor(mainTable);	
		
		InputMultiplexer im = new InputMultiplexer(stage,this);
		Gdx.input.setInputProcessor(im);
		
		ninaPhrase = nina.getRandomPhrase();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		currFunds.setText("Current funds:\n" + game.getUsrData().getCurrentMoney());
		
		batch.begin();
		nina.draw(batch, ninaPhrase);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		stage.clear();
	}

	@Override
	public void dispose() {
	}
}

