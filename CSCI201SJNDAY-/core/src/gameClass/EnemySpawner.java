package gameClass;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

import screens.GameplayScreen;

import java.util.Random;
import java.util.stream.IntStream;

public class EnemySpawner {
	public Vector2 spawnPos;
	public float spawnTimer;
	public boolean needToSpawn;
	public Vector2[] spawnLocations; //An array out out-of-screen spawn locations along top half of map (20 options)
	public GameplayScreen mGame;
	public int spawnerNum;
	Random rand;
	
	public EnemySpawner(GameplayScreen game, int num) {
		mGame = game;
		spawnerNum = num;
		spawnTimer = 10.0f;
		needToSpawn = false;
		spawnLocations = new Vector2[20];
		rand = new Random();
		int spawnCount = 0;
		for(int i=0; i<5; i++) {
			spawnLocations[spawnCount] = new Vector2(-100,398+(90*i));
			spawnCount++;
		}
		for(int i=0; i<10; i++) {
			spawnLocations[spawnCount] = new Vector2(-100+(125*i),848);
			spawnCount++;
		}
		for(int i=0; i<5; i++) {
			spawnLocations[spawnCount] = new Vector2(1150,848-(90*i));
			spawnCount++;
		}
	}
	public void spawnEnemy() {
		int xLoc = rand.nextInt(169);
		int yLoc = rand.nextInt(169);
		int spawnIndex = rand.nextInt(20);
		EnemyShip currEnemy = new EnemyShip(mGame,xLoc+100+(spawnerNum*169),yLoc+500,spawnerNum,spawnLocations[spawnIndex]);
		mGame.enemys.add(currEnemy);
	}
}
