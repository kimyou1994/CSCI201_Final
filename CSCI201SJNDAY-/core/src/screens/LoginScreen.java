package screens;

import com.badlogic.gdx.Gdx;
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

import Server.GameClient;
import gameClass.UserData;  

//creating screen

public class LoginScreen implements Screen, InputProcessor {
	public static ShootsAndLooters mGame;
	private GameClient gc;
	public static Stage stage;
	private static UserData ud;
	static TextField textUsername;
	static TextField textPassword;
	SpriteBatch batch;
	private Sprite backgroundSprite;
	private Sprite logo;
	TextButton buttonLogin;
	private boolean signupFlag;
	private boolean loginFlag;
	private String recentUsername;

	public LoginScreen(ShootsAndLooters game) {
		signupFlag = false;
		loginFlag = false;
		mGame = game;
		stage = new Stage();
		batch = new SpriteBatch();
		gc = new GameClient("localhost", 6789, this);
		
		Gdx.input.setInputProcessor(stage);
		
		//creating background and logo sprites
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("pEeUsp1.jpg")));
		logo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
		
		
		//skin based on defaultUI
		Skin skin = new Skin(Gdx.files.internal("defaultUIskin.json"));
		
		//textField specific font size
		TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
		textFieldStyle.font.getData().scale(0.5f);
		
		//creating buttons and text fields
		TextButton buttonLogin = new TextButton("LOGIN AS USER", skin);
		TextButton guestLogin = new TextButton("PLAY AS GUEST", skin);
		TextButton signUp = new TextButton("CREATE A NEW ACCOUNT", skin);
		Label welcomeLabel = new Label("", skin);
		Label welcomeLabel1 = new Label("", skin);
		Label welcomeLabel2 = new Label("", skin);
		Label welcomeLabel3 = new Label("", skin);
		Label welcomeLabel4 = new Label("", skin);
		Label welcomeLabel5 = new Label("", skin);
		Label welcomeLabel6 = new Label("", skin);
		Label welcomeLabel7 = new Label("", skin);
		Label welcomeLabel8 = new Label("", skin);
		Label usernameLabel = new Label("Username:", skin);
		textUsername = new TextField("", textFieldStyle);
		textUsername.setMessageText("Username");
		Label passwordLabel = new Label("Password:", skin);
		textPassword = new TextField("", textFieldStyle);
		textPassword.setMessageText("Password");
		textPassword.setPasswordCharacter('.');
		textPassword.setPasswordMode(true);
		
		//function for login button click
		buttonLogin.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent e, float x, float y) {
				   recentUsername = textUsername.getText();
				   System.out.println("Username is:" + textUsername.getText());
				   System.out.println("Password is:" + textPassword.getText());
				   buttonLoginClicked();	   
			   }
			});
		
		//function for guest login button click 
		guestLogin.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent e, float x, float y) {
				   guestLoginClicked();
				   e.stop();
			   }
			});
		
		//function for signup button click
		signUp.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent e, float x, float y) {
				   signUpButtonClicked();
				   e.stop();
			   }
			});
		
		textUsername.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				 //System.out.println("Key Pressed");
			}
		});
			
		textPassword.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				//System.out.println("Key Pressed");
			}
		});
		
	
			//setting font sizes
			buttonLogin.getLabel().setFontScale(0.3f);
			guestLogin.getLabel().setFontScale(0.3f);
			signUp.getLabel().setFontScale(0.3f);
			usernameLabel.setFontScale(0.5f);
			passwordLabel.setFontScale(0.5f);
			welcomeLabel.setFontScale(0.8f);
			textUsername.getStyle().font.getData().setScale(0.5f);
			textPassword.getStyle().font.getData().setScale(0.5f);
			
			//adding items to table
			Table menuTable = new Table();
			VerticalGroup v = new VerticalGroup();
			v.addActor(welcomeLabel);
			v.addActor(welcomeLabel1);
			v.addActor(welcomeLabel2);
			v.addActor(welcomeLabel3);
			v.addActor(welcomeLabel4);
			v.addActor(welcomeLabel5);
			v.addActor(welcomeLabel6);
			v.addActor(welcomeLabel7);
			v.addActor(welcomeLabel8);
			menuTable.add(v);
			menuTable.row();
			menuTable.add(usernameLabel).expandX().padTop(10).padLeft(150);
			menuTable.add(textUsername).width(500).padTop(65).padLeft(10);
			menuTable.row();
			menuTable.add(passwordLabel).padBottom(10).padLeft(150);
			menuTable.add(textPassword).width(500).padTop(60).padBottom(10);
			menuTable.row();
			menuTable.add(buttonLogin).width(500);
			menuTable.add(guestLogin).width(500);
			menuTable.row();
			menuTable.add(signUp).width(500).padLeft(500).padTop(10);
			menuTable.setFillParent(true);
			stage.addActor(menuTable);

	}

	

	
	public void setSignupFlag(boolean flag) {
		this.signupFlag = flag;
	}
	
	public void setLoginFlag(boolean flag) {
		this.loginFlag = flag;
	}
	
	//checking against database
	public void buttonLoginClicked() {
		gc.getLoginInfo(textUsername.getText(), textPassword.getText());
		System.out.println("stuck in login info!");
		this.loginFlag = false;
	}
	
	//login as a guest
	public void guestLoginClicked() {
		ud = new UserData("", true, 500, 0, 0, 0, new Boolean[]{true, false, false, false, false, false});
  		mGame.setUsrData(ud);
  		this.setGameSuccessFlag(ud);
	}
	
	//sign up for an account
	public void signUpButtonClicked() {
		gc.getSignupInfo(textUsername.getText(), textPassword.getText());
		System.out.println("stuck in signup info!");
		this.signupFlag = false;
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//setting background for loginscreen
		//Sprite background = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
		batch.begin();
		backgroundSprite.setSize(1024, 768);
		backgroundSprite.setPosition(0.0f,0.0f);
		backgroundSprite.draw(batch);
		logo.setSize(500, 300);
		logo.setPosition(260, 460);
		logo.draw(batch);
		
		batch.end();
		stage.act(delta);
		stage.draw();
	}
	
	//resets the username and password fields
	public void reset() {
		textUsername.setText("");
		textPassword.setText("");
		
	}
	
	public void setGameSuccessFlag(UserData userData) {
		//TODO
		//Get mySQL data
		//ud = new UserData("test", false, 500, 2, 2, 2, new Boolean[]{true, true, true, true, true, true});
  		ud = userData;
		mGame.setUsrData(ud);
		mGame.setLoginSuccess(true);
	}
	
	public void setGameSignupSuccessFlag() {
		System.out.println("in setgame signup success flag");
		//TODO
		//Get mySQL data
		ud = new UserData(textUsername.getText(), false, 500, 0, 0, 0, new Boolean[]{true, false, false, false, false, false});
		System.out.println("what is username" + textUsername.getText());
		mGame.setUsrData(ud);
		mGame.setSignupSuccess(true);
	}
	
	public void setGameFailFlag() {
		mGame.setLoginFail(true);
	}
	
	public void setGameSignupFailFlag() {
		mGame.setSignupFail(true);
	}
	
	
	//incorrect password
	public void errorMessage() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		//pop up error message
		Dialog dialog = new Dialog("Warning", skin, "dialog");
		dialog.text("Invalid Username or Password, please try again!");
		dialog.button("OK", true);
		dialog.show(stage);
	}
	
	//successfully creates new account
	public void signUpMessageSuccess() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		//popup success message
		Dialog dialog2 = new Dialog("Success", skin, "dialog");
		dialog2.text("Successfully created new account with username" + textUsername.getText());
		dialog2.button("OK", true);
		dialog2.show(stage);
	}
	
	//does not successfully create new account
	public void signUpMessageFail() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		//pop up username taken error message
		Dialog dialog3 = new Dialog("Fail", skin, "dialog");
		dialog3.text("Sorry, the username " + textUsername.getText() + "is already taken. Please try again!");
		dialog3.button("OK", true);
		dialog3.show(stage);
	}
	
	public void loginSuccess() {
		ud = new UserData(textUsername.getText(), false, 500, 2, 2, 2, new Boolean[]{true, false, false, false, false, false});
		mGame.setUsrData(ud);
		System.out.println("loginscreen: in login success");
	}
	
	/**
	 * 
	 * @return
	 * @author joshl
	 */
	public GameClient getGameClient() {
		return gc;
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
	}
	
}
