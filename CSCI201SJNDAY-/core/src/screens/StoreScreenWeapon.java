package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ShootsAndLooters;

import gameClass.Nina;

public class StoreScreenWeapon extends StoreScreen implements Screen, InputProcessor {
	private SpriteBatch batch;
	private Nina nina;
	private String ninaPhrase;
	
	public StoreScreenWeapon(ShootsAndLooters shootsAndLooters) {
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
		for(int i = 0; i < 10; i++) {
			if(i <= game.getUsrData().getWeaponLevel()) {
				weaponUpgrades.get(i).setStyle(weaponBStyle);
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
			int index = i + (6 * currentPage);
			if(!(index >= 10)) {
				mainTable.add(weaponUpgrades.get(index));
			} else {
				mainTable.add();
			}
		}
		mainTable.add();
		
		//Second row of Upgrades
		mainTable.row().height(128f).width(128f).padTop(15f).padBottom(15f);
		mainTable.add(moveButtons.get(0));
		for(int i = 0; i < 3; i++) {
			int index = i + (6 * currentPage) + 3;
			if(!(index >= 10)) {
				mainTable.add(weaponUpgrades.get(index));
			} else {
				mainTable.add();
			}
		}
		mainTable.add(moveButtons.get(1));
		
		//Buy button
		mainTable.row().padTop(15f).padBottom(15f);
		mainTable.add();
		mainTable.add();
		mainTable.add(BuyButton).width(300f);
		mainTable.add(errorText);
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

