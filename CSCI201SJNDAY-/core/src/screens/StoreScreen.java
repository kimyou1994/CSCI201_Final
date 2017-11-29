package screens;

import java.util.List;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ShootsAndLooters;

import gameClass.Nina;
import gameClass.UserShip;

public class StoreScreen implements Screen, InputProcessor {
	protected UserShip player;
	
	//Styles and objects
	protected static Skin skin;
	protected static Stage stage;
	protected static ShootsAndLooters game;
	protected TextButtonStyle currMenuStyle;
	protected TextButtonStyle defStyle;
	protected ImageTextButtonStyle weaponBStyle;
	protected ImageTextButtonStyle healthBStyle;
	
	//All functional buttons
	protected static Table mainTable;
	protected static TextButton ResumeButton;
	protected static TextButton ColorButton;
	protected static TextButton HealthButton;
	protected static TextButton WeaponButton;
	protected static TextButton BuyButton;
	protected static TextButton BuyHealthButton;
	
	//Labels and arrows
	protected static Label currFunds;
	protected static Label storeText;
	protected static Label errorText;
	protected static Drawable lArrow;
	protected static Drawable rArrow;
	
	//All buttons
	protected static List<ImageTextButton> healthUpgrades;
	protected static List<ImageTextButton> weaponUpgrades;
	protected static List<ImageTextButton> colorUpgrades;
	protected static List<ImageTextButton> allButtons;
	protected static List<ImageButton> moveButtons;
	
	//Global font scale params
	protected final float defaultFontScale = 0.20f;
	protected final float defaultTitleFontScale = 0.55f;
	
	//Store screen metadata
	protected static int currentPage;
	
	//Page 0 is health, 1 is weapons, 2 is colors
	protected static int pageNum;
	
	public StoreScreen(final ShootsAndLooters shootsAndLooters) {
		//Set the game dataMember
		this.game = shootsAndLooters;
		player = game.getPlayScreen().player;
		
		game = shootsAndLooters;
		
		currentPage = 0;
		pageNum = 0;
		
		//Set skin and get skin styles
		skin = new Skin(Gdx.files.internal("defaultUIskin.json"));
		currMenuStyle = skin.get("selectedMenu", TextButtonStyle.class);
		defStyle = skin.get("default", TextButtonStyle.class);
		weaponBStyle = skin.get("weaponUpgradeB", ImageTextButtonStyle.class);
		healthBStyle = skin.get("shieldUpgradeB", ImageTextButtonStyle.class);
		
		//Set view
		stage = new Stage(new ScreenViewport());
		
		//Init buttons
		healthUpgrades = new Vector<ImageTextButton>(10);
		weaponUpgrades = new Vector<ImageTextButton>(10);
		colorUpgrades = new Vector<ImageTextButton>(6);
		allButtons = new Vector<ImageTextButton>(8);
		moveButtons = new Vector<ImageButton>(2);

		//Left and Right button images
		lArrow = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("lArrow.png"))));
		rArrow = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("rArrow.png"))));
		
		//Init Buttons and their functions		
		String[] funcButtonsText = {"Resume Game", "COLOR", "DEFENSE", "WEAPON", "BUY LATEST UPGRADE", "BUY 50 HEALTH\nLOOTIES:50"};
		
		//Create new buttons with their labels and skins
		ResumeButton = new TextButton(funcButtonsText[0], skin);
		ColorButton = new TextButton(funcButtonsText[1], skin);
		HealthButton = new TextButton(funcButtonsText[2], skin);
		WeaponButton = new TextButton(funcButtonsText[3], skin);
		BuyButton = new TextButton(funcButtonsText[4], skin);
		BuyHealthButton = new TextButton(funcButtonsText[5], skin);
		
		//Add labels to buttons
		TextButton[] funcButtons = {ResumeButton, ColorButton, HealthButton, WeaponButton, BuyButton, BuyHealthButton};
		for(int i = 0; i < 6; i++) {
			funcButtons[i].getLabel().setFontScale(defaultFontScale);
			funcButtons[i].padRight(20f).padLeft(20f);
		}
		
		//Add current money
		currFunds = new Label("Current funds:\n" + game.getUsrData().getCurrentMoney(), skin);
		currFunds.setFontScale(defaultFontScale);
		currFunds.setAlignment(Align.center);
		
		//Add store label
		storeText = new Label("WARP STORE", skin);
		storeText.setAlignment(Align.center);
		storeText.setFontScale(defaultTitleFontScale);
		
		//Add error text label
		errorText = new Label("", skin);
		errorText.setAlignment(Align.center);
		errorText.setFontScale(defaultFontScale);
		errorText.setColor(Color.RED);
		errorText.setWrap(true);
		
		//Construct the upgrade contents
		moveButtons.add(new ImageButton(lArrow));
		moveButtons.add(new ImageButton(rArrow));
		for(int i = 0; i < 10; i++)
		{
			//TODO get names and costs
			//Setup health button visuals
			ImageTextButton healthButton = new ImageTextButton(game.getItems().getHealth(i).getName() + "\nPRICE:" + game.getItems().getHealth(i).getPrice(), skin);
			healthButton.clearChildren();
			healthButton.add(healthButton.getImage());
			healthButton.row().padTop(10f).padBottom(10f);
			healthButton.add(healthButton.getLabel());
			
			//Add styles
			ImageTextButtonStyle healthButtonStyle = skin.get("shieldUpgrade", ImageTextButtonStyle.class);
			healthButton.getLabel().setFontScale(defaultFontScale);
			healthButton.setStyle(healthButtonStyle);
			healthUpgrades.add(healthButton);
			
			//Setup weapon button visuals
			ImageTextButton weaponButton = new ImageTextButton(game.getItems().getWeapon(i).getName() + "\nPRICE:" + game.getItems().getWeapon(i).getPrice(), skin);
			weaponButton.clearChildren();
			weaponButton.add(weaponButton.getImage());
			weaponButton.row().padTop(10f).padBottom(10f);
			weaponButton.add(weaponButton.getLabel());
			
			//Add styles
			ImageTextButtonStyle weaponButtonStyle = skin.get("weaponUpgrade", ImageTextButtonStyle.class);
			weaponButton.getLabel().setFontScale(defaultFontScale);
			weaponButton.setStyle(weaponButtonStyle);
			weaponUpgrades.add(weaponButton);
		}
		
		//Set colors
		//Green, Yellow, Orange, Purple, Blue, Pink
		for(int i = 0; i < 6; i++) {
			TextureRegionDrawable shipImage = new TextureRegionDrawable(new TextureRegion(game.getShipColors().getTexture(i, "static")));
			ImageTextButtonStyle newColorStyle = new ImageTextButtonStyle(null, null, null, skin.getFont("PressStart"));
			ImageTextButton colorButton = new ImageTextButton(game.getShipColors().getColorName(i), skin);
			colorButton.setStyle(newColorStyle);
			colorButton.getStyle().imageUp = shipImage;
			colorButton.getStyle().imageDown = shipImage;
			colorButton.clearChildren();
			colorButton.add(colorButton.getImage());
			colorButton.row().padTop(10f).padBottom(10f);
			colorButton.add(colorButton.getLabel());
			colorButton.getLabel().setFontScale(defaultFontScale);
			
			final int index = i;
			//Add color button functionality
			colorButton.addListener(new ClickListener() {
				   @Override
				   public void clicked(InputEvent event, float x, float y) {
					  errorText.setText("");
					  if(game.getUsrData().getColorOwned()[index]) {
						  //If they own the color, set the ship to this color
						  game.getUsrData().setColorIndex(index);
						  
						//Change the user's colors
						Sprite staticSprite = new Sprite(game.getShipColors().getTexture(index, "static"));
						Sprite thrustSprite = new Sprite(game.getShipColors().getTexture(index, "thrust"));
						Sprite reverseSprite = new Sprite(game.getShipColors().getTexture(index, "reverse"));
						game.getPlayScreen().player.setStaticDesign(staticSprite);
						game.getPlayScreen().player.setThrustDesign(thrustSprite);
						game.getPlayScreen().player.setReverseDesign(reverseSprite);
					  } else if (game.getUsrData().getCurrentMoney() < 200) {
						  errorText.setText("Not enough money");
					  } else {
						  Boolean[] colorOwned = game.getUsrData().getColorOwned();
						  game.getUsrData().setCurrentMoney(game.getUsrData().getCurrentMoney() - 200);
						  colorOwned[index] = true;
						  game.getUsrData().setColorOwned(colorOwned);
					  }
					  
					  //Refresh screen
					  shootsAndLooters.setScreen(shootsAndLooters.getColorScreen());
				      event.stop();
				   }
				});
			
			colorUpgrades.add(colorButton);
		}
		
		//Set the first page to health
		HealthButton.setStyle(currMenuStyle);
		
		//Set button functions
		ResumeButton.addListener(new ClickListener() {
		   @Override
		   public void clicked(InputEvent event, float x, float y) {
				currentPage = 0;
				pageNum = 0;
				HealthButton.setStyle(currMenuStyle);
				WeaponButton.setStyle(defStyle);
				ColorButton.setStyle(defStyle);
				errorText.setText("");
		      //Go back to game screen only if ship has full health.
			  if(game.getPlayScreen().player.getHealth() <= 0 && game.getUsrData().getCurrentMoney() >= 50) {
				  errorText.setText("You have no health!\nBuy more health!");
			  } else if (game.getPlayScreen().player.getHealth() <= 0) {
				  game.setScreen(game.getEndScreen());
			  }
			  shootsAndLooters.setScreen(shootsAndLooters.getPlayScreen());
		      event.stop();
		   }
		});
		
		ColorButton.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				  currentPage = 0;
				  pageNum = 1;
				  HealthButton.setStyle(defStyle);
				  WeaponButton.setStyle(defStyle);
				  ColorButton.setStyle(currMenuStyle);
				  errorText.setText("");
				  shootsAndLooters.setScreen(shootsAndLooters.getColorScreen());
			      event.stop();
			   }
			});
		
		BuyHealthButton.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   //Buy Health
				   if(game.getUsrData().getCurrentMoney() >= 50 && (player.getHealth() != player.getMaxHealth())) {
					   errorText.setText("");
					   game.getPlayScreen().player.setHealth(game.getPlayScreen().player.getHealth() + 50);
					   game.getUsrData().setCurrentMoney(game.getUsrData().getCurrentMoney() - 50);
				   } else if (player.getHealth() == player.getMaxHealth()) {
					   errorText.setText("You are at max health");
				   } else {
					   errorText.setText("Not enough money");
				   }
				   shootsAndLooters.setScreen(shootsAndLooters.getHealthScreen());
				   event.stop();
			   }
			});
		
		BuyButton.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   if(pageNum == 0) {
					   //Try to buy a health upgrade
					   if(game.getUsrData().getArmorLevel() != 9 && player.buyHealthUpgrade()) {
						   
					   } else if (game.getUsrData().getArmorLevel() == 9){
						   errorText.setText("Max level");
					   } else {
						   errorText.setText("Not enough money");
					   }
					   shootsAndLooters.setScreen(shootsAndLooters.getHealthScreen());
				   } else if(pageNum == 1) {
					   //Try to buy a weapon upgrade
					   if(game.getUsrData().getWeaponLevel() != 9 && player.buyWeaponUpgrade()) {
						   
					   } else if (game.getUsrData().getWeaponLevel() == 9){
						   errorText.setText("Max level");
					   } else {
						   errorText.setText("Not enough money");
					   }
					   shootsAndLooters.setScreen(shootsAndLooters.getWeaponScreen());
				   }
				   event.stop();
			   }
			});
		
		WeaponButton.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   currentPage = 0;
				   pageNum = 1;
				   HealthButton.setStyle(defStyle);
				   WeaponButton.setStyle(currMenuStyle);
				   ColorButton.setStyle(defStyle);
				   errorText.setText("");
				   shootsAndLooters.setScreen(shootsAndLooters.getWeaponScreen());
				   event.stop();
			   }
			});
		
		HealthButton.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   currentPage = 0;
				   pageNum = 0;
				   HealthButton.setStyle(currMenuStyle);
				   WeaponButton.setStyle(defStyle);
				   ColorButton.setStyle(defStyle);
				   errorText.setText("");
				   shootsAndLooters.setScreen(shootsAndLooters.getHealthScreen());
				   event.stop();
			   }
			});
		
		moveButtons.get(0).addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   //Decrease page by 1
				   if(currentPage <= 0) {
					   currentPage = 0;
				   } else {
					   currentPage -= 1;
				   }
				   
				   //Refresh screen
				   if(pageNum == 0) {
					   shootsAndLooters.setScreen(shootsAndLooters.getHealthScreen());
				   } else if (pageNum == 1) {
					   shootsAndLooters.setScreen(shootsAndLooters.getWeaponScreen());
				   } else if (pageNum == 2) {
					   
				   }
				   event.stop();
			   }
			});
		
		moveButtons.get(1).addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   //Increase page by 1
				   if(currentPage >= 1) {
					   currentPage = 1;
				   } else {
					   currentPage += 1;
				   }
				   
				   //Refresh screen
				   if(pageNum == 0) {
					   shootsAndLooters.setScreen(shootsAndLooters.getHealthScreen());
				   } else if (pageNum == 1) {
					   shootsAndLooters.setScreen(shootsAndLooters.getWeaponScreen());
				   } else if (pageNum == 2) {
					   
				   }
				   event.stop();
			   }
			});
		
	}

	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
	}

	@Override
	public void dispose() {
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
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

