package gameClass;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ShootsAndLooters;

public class JeffSquad {
	private Vector<Jeff> jeffs;
	private Nina nina;
	private ShootsAndLooters mGame;

	public JeffSquad(ShootsAndLooters game) {
		setmGame(game);
		jeffs = new Vector<Jeff>();
		nina = new Nina(mGame);
		float x = 100f; 
		float y = 300f;
		for (int i = 0; i < 10; i++) {
			Jeff j = new Jeff(mGame);
			j.setPosition(new Vector2(x,y));
			j.setVelocity(20.0f * Gdx.graphics.getDeltaTime());
			jeffs.add(j);
			x += 70f;
			y -= 30f;
		}
		x = 100f;
		y = 300f;
		for (int i = 0; i < 10; i++) {
			Jeff j = new Jeff(mGame);
			j.setPosition(new Vector2(x,y));
			j.setVelocity(20.0f * Gdx.graphics.getDeltaTime());
			jeffs.add(j);
			x += 70f;
			y += 30f;
		}
	}
	
	public void draw(SpriteBatch batch) {
		for (Jeff j: jeffs) {
			j.move();
			j.getDesign().draw(batch);
		}
	}
	
	public ShootsAndLooters getmGame() {
		return mGame;
	}

	public void setmGame(ShootsAndLooters mGame) {
		this.mGame = mGame;
	}
}
