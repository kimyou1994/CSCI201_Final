package screens;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Laser;
import com.mygdx.game.ShootsAndLooters;

import gameClass.AllyShip;
import gameClass.Base;
import gameClass.EnemyShip;
import gameClass.EnemySpawner;
import gameClass.Jeff;
import gameClass.Upgrade;
import gameClass.UserData;
import gameClass.UserShip;

public class GameplayScreen implements Screen{
	public SpriteBatch batch;
	public Texture img;
	public ShootsAndLooters mGame;
	public UserShip player;
	public Vector<EnemyShip> enemys;
	public Vector<Laser> enemyLasers;
	public Vector<EnemySpawner> spawners;
	public Vector<AllyShip> allys;
	public Vector<Laser> allyLasers;
	public Animation<TextureRegion> explodeAnimation;
	public Vector<Float> deathTimers;
	public Vector<EnemyShip> markedEnemies;
	public Texture explodeSheet;
	public float stateTime;
	private static final int FRAME_COLS = 9, FRAME_ROWS = 9;
	public HUD hud;
	public PauseScreenOverlay pauseScreenOverlay;
	public int loot;
	public Jeff jeff;
	public Base base;
	public static Stage stage;
	public Upgrade items;
	public UserData data;
	private Music background;
	private Sound playerLaserSound;
	private Sound enemyLaserSound;
	private boolean soundPlaying;
	private Vector<Boolean> enemySoundPlaying;
	private Vector<Float> enemySoundTimer;
	private float soundTimer;
	public Boolean paused, gameEnd;
	public Sprite backgroundSprite; 
	public Sprite backgroundPausedSprite;
	public TextureRegion currentFrame;

	public GameplayScreen(ShootsAndLooters game) {
		mGame = game;
		player = new UserShip(this);
		enemys = new Vector<EnemyShip>();
		enemyLasers = new Vector<Laser>();
		spawners = new Vector<EnemySpawner>();
		allys = new Vector<AllyShip>();
		allyLasers = new Vector<Laser>();
		markedEnemies = new Vector<EnemyShip>();
		items = game.getItems();
		data = game.getUsrData();
		loot = 0;
		gameEnd = false;
		stage = new Stage();
		for(int i=0; i<5; i++) {
			EnemySpawner spawner = new EnemySpawner(this,i);
			spawner.needToSpawn = true;
			spawner.spawnTimer = 3.0f;
			spawners.add(spawner);
		}
		AllyShip ally1 = new AllyShip(this,100, 50,1);
		AllyShip ally2 = new AllyShip(this,924, 50,2);
		allys.add(ally1);
		allys.add(ally2);
		hud = new HUD();
		pauseScreenOverlay = new PauseScreenOverlay();
		jeff = new Jeff(mGame);
		base = new Base(mGame);
		paused = false;
		playerLaserSound = Gdx.audio.newSound(Gdx.files.internal("playerLaser.mp3"));
		enemyLaserSound = Gdx.audio.newSound(Gdx.files.internal("enemyLaser.mp3"));
		soundPlaying = false;
		enemySoundPlaying = new Vector<Boolean>();
		enemySoundTimer = new Vector<Float>();
		deathTimers = new Vector<Float>();
		for(int i=0; i<5; i++) {
			enemySoundPlaying.add(false);
			enemySoundTimer.add(0.0f);
			deathTimers.add(0.0f);
		}
		background = Gdx.audio.newMusic(Gdx.files.internal("background.ogg"));
		background.setVolume(0.5f);
		background.setLooping(true);
		background.play();
		// Load the sprite sheet as a Texture
		explodeSheet = new Texture(Gdx.files.internal("explosion-sheet.png"));
		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(explodeSheet, 
				explodeSheet.getWidth() / FRAME_COLS,
				explodeSheet.getHeight() / FRAME_ROWS);
		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		// Initialize the Animation with the frame interval and array of frames
		explodeAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
		// time to 0
		stateTime = 0f;
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("background.jpg"))); //load in the background
		backgroundPausedSprite = new Sprite(new Texture(Gdx.files.internal("backgroundMono.jpg")));
		instructionMessage();
		
	}
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//fpslog.log();
		//Game is NOT Paused
		if (!paused) {
 			
 			base.move();
 			player.move();
 			jeff.move();
 			enemyLasers.clear();
 			allyLasers.clear();
 			for(EnemySpawner spawn : spawners) {
 				if(spawn.needToSpawn) {
 					spawn.spawnTimer -= Gdx.graphics.getDeltaTime();
 					if(spawn.spawnTimer <= 0.0f) {
 						spawn.spawnTimer = 10.0f;
 						spawn.needToSpawn = false;
 						spawn.spawnEnemy();
 					}
 				}
 			}
 			Laser playerLaser = null;
 			if(enemys.size() != 0) playerLaser = player.shoot(enemys);
 			for(AllyShip as : allys) {
 				as.move();
 				allyLasers.add(as.shoot());
 			}
 			for(EnemyShip es : enemys) {
	 			if(!es.getExplode()) es.move();
	 			enemyLasers.add(es.shoot());
 			}
 			batch.begin();
 			backgroundSprite.setSize(1024, 768);
 			backgroundSprite.setPosition(0.0f,0.0f);
 			backgroundSprite.draw(batch);
 			if(data.equals(null)) {
 				loot = 0;
 			} else {
 				loot = data.getCurrentMoney();
 			}
 			
 			//Added notifications
 			mGame.getNotiClient().draw(batch);
 			jeff.getDesign().draw(batch);
 			player.getDesign().draw(batch);
 			soundTimer -= Gdx.graphics.getDeltaTime();
			if(soundTimer < 0.0f) soundPlaying = false;
 			if(playerLaser != null) {
 				playerLaser.render(Gdx.graphics.getDeltaTime(),batch);
 				if(!soundPlaying) {
 					playerLaserSound.play(1.0f);
	 				soundPlaying = true;
	 				soundTimer = 2.0f;
 				}
 			}
 			for(AllyShip as : allys) {
 				as.getDesign().draw(batch);
 				if(allyLasers.get(allys.indexOf(as)) != null) allyLasers.get(allys.indexOf(as)).render(Gdx.graphics.getDeltaTime(),batch);
 			}
 			for(EnemyShip es : enemys) {
 				if(es.getExplode()) {
 					deathTimers.set(enemys.indexOf(es), deathTimers.elementAt(enemys.indexOf(es)) + Gdx.graphics.getDeltaTime());
 					if(deathTimers.elementAt(enemys.indexOf(es)) <= 2.0f) {
 						currentFrame = explodeAnimation.getKeyFrame(deathTimers.elementAt(enemys.indexOf(es)), true);
 						batch.draw(currentFrame, es.getDesign().getX()-30.0f, es.getDesign().getY()-30.0f);
 					}
 					else {
 						spawners.elementAt(es.getShipNum()).needToSpawn = true;
 						deathTimers.set(enemys.indexOf(es), 0.0f);
 						markedEnemies.add(es);
 					}
 				}
 				else es.getDesign().draw(batch);
 			}
 			for(int i=0; i<markedEnemies.size(); i++) {
 				EnemyShip death = markedEnemies.remove(0);
 				enemys.remove(death);
 			}
 			for(EnemyShip es : enemys) {
 				enemySoundTimer.set(enemys.indexOf(es),enemySoundTimer.get(enemys.indexOf(es))-Gdx.graphics.getDeltaTime());
 				if(enemySoundTimer.get(enemys.indexOf(es)) < 0.0f) enemySoundPlaying.set(enemys.indexOf(es), false);
 				if(enemyLasers.get(enemys.indexOf(es)) != null) {
 					enemyLasers.get(enemys.indexOf(es)).render(Gdx.graphics.getDeltaTime(),batch);
 					if(!enemySoundPlaying.get(enemys.indexOf(es))) {
 	 					enemyLaserSound.play(1.0f);
 		 				enemySoundPlaying.set(enemys.indexOf(es), true);
 		 				enemySoundTimer.set(enemys.indexOf(es), 2.0f);
 	 				}
 				}
 			}
 			
 			base.getDesign().draw(batch);
 			hud.draw(batch, player, loot);
 			batch.end();
 			
 			if(hud.pauseButtonJustPressed()) {
 				paused = true;
 			}
 			
 		//Game is Paused
		} else {
			batch.begin();
			backgroundPausedSprite.setSize(1024, 768);
			backgroundPausedSprite.setPosition(0.0f,0.0f);
			backgroundPausedSprite.draw(batch);
			jeff.getDesign().draw(batch);
 			player.getDesign().draw(batch);
 			base.getDesign().draw(batch);
 			for(AllyShip as : allys) {
 				as.getDesign().draw(batch);
 				if(allyLasers.get(allys.indexOf(as)) != null) allyLasers.get(allys.indexOf(as)).render(Gdx.graphics.getDeltaTime(),batch);
 			}
 			for(EnemyShip es : enemys) {
 				es.getDesign().draw(batch);
 				if(enemyLasers.get(enemys.indexOf(es)) != null) enemyLasers.get(enemys.indexOf(es)).render(Gdx.graphics.getDeltaTime(),batch);
 			}
 			pauseScreenOverlay.draw(batch);
 			batch.end();
 			
 			//Resume the game if the resume button is pressed
 			paused = pauseScreenOverlay.resumeButtonJustPressed();
 			
 			//If quit button is clicked, go back to login screen
 			if (pauseScreenOverlay.quitButtonJustPressed()) {
 				this.background.pause();
 				this.background = Gdx.audio.newMusic(Gdx.files.internal("credits.ogg"));
 				this.background.setLooping(true);
 				this.background.play();
 				mGame.setScreen(mGame.getEndScreen());
 			}
 			
 			//If shop button pressed, go to shop screen
 			if (pauseScreenOverlay.shopButtonJustPressed()) {
 				mGame.setScreen(mGame.getHealthScreen());
 			}
		}
		stage.act(delta);
	    stage.draw();
	    if (gameEnd) {
	    		gameEnd = false;
	    		paused = true;
	    		endMessage();
	    		Gdx.input.setInputProcessor(stage);
	    		stage.act();
	    		stage.draw();
	    }
	    System.gc();
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
	
	public void setData(UserData newData) {
		this.data = newData;
		mGame.setUsrData(newData);
		player.setData(newData);
	}

	public void instructionMessage() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		//pop up error message
		Dialog instruction = new Dialog("Instruction", skin);
		instruction.text("Move your ship with the keyboard: left(A), right(D), up(W), and down(S).\n"
				+ "Enemys will detect your ship when you get close to them.\n"
				+ "Ally ships will help you fight with enemy ships - \n"
				+ "but be careful, enemies will still target you first! \n"
				+ "You can buy upgrades or change colors from the shop!\n"
				+ "For every enemy ship you destroy, you will earn 100 currency.");
		instruction.button("Start!", true);
		instruction.key(Input.Keys.ENTER, true);
		instruction.show(stage);
	}
	
	public void endMessage() {
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		//pop up error message
		Dialog end = new Dialog("Instruction", skin) {
			 @Override
		        protected void result(Object object) {
		            boolean state = (Boolean) object;
		            if (state) {
		            		mGame.setScreen(mGame.getHealthScreen());
		            } else {
		            		background.pause();
		            		background = Gdx.audio.newMusic(Gdx.files.internal("credits.ogg"));
		            		background.setLooping(true);
		            		background.play();
		            		mGame.setScreen(mGame.getEndScreen());
		            }
		        }
		};
		end.text("Your ship has 0 health. \nTo continue, buy health from the shop or end game");
		end.button("End", false);
		end.key(Input.Keys.ESCAPE, false);
		end.button("Shop", true);
		end.key(Input.Keys.ENTER, true);
		end.show(stage);
	}
}
