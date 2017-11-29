package screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.ShootsAndLooters;

import Messages.UserQuitMessage;
import Server.GameClient;
import Server.GameServer;
import gameClass.UserData;  

//creating screen

public class EndScreen implements Screen {
	public static ShootsAndLooters mGame;
	public static Stage stage;
	SpriteBatch batch;
	public EndScreen(ShootsAndLooters game) {
		mGame = game;
		stage = new Stage();
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(stage);
		
		//skin based on defaultUI
		Skin skin = new Skin(Gdx.files.internal("defaultUIskin.json"));
		
		//textField specific font size
		TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
		textFieldStyle.font.getData().scale(0.5f);
		
		//creating buttons and text fields
		Label welcomeLabel = new Label("GAME OVER", skin);
		
			//setting font sizes
			welcomeLabel.setFontScale(0.8f);
			
			//adding items to table
			Table menuTable = new Table();
			VerticalGroup v = new VerticalGroup();
			v.addActor(welcomeLabel);
			menuTable.add(v);
			menuTable.setFillParent(true);
			stage.addActor(menuTable);

	}

	
	@Override
	public void show() {
		UserQuitMessage qm = new UserQuitMessage(mGame.getUsrData());
		mGame.getLoginScreen().getGameClient().sendMessage(qm);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//setting background for loginscreen
		Sprite background = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
		batch.begin();
		background.setSize(1024, 768);
		background.setPosition(0.0f,0.0f);
		background.draw(batch);
		
		batch.end();
		stage.act(delta);
		stage.draw();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
	}
	
	public void setGameSuccessFlag() {
		mGame.setLoginSuccess(true);
	}
	
	public void setGameSignupSuccessFlag() {
		mGame.setSignupSuccess(true);
	}
	
	public void setGameFailFlag() {
		mGame.setLoginFail(true);
	}
	
	public void setGameSignupFailFlag() {
		mGame.setSignupFail(true);
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
/*
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}*/
}
