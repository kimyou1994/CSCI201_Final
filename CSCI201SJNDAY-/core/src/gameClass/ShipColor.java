package gameClass;

import java.util.HashMap;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Stores all the textures for ship colors
 * @author joshl
 *
 */
public class ShipColor {
	private static HashMap<String, Texture> texturesMap = new HashMap<String, Texture>();
	private static Vector<Texture> staticTextures = new Vector<Texture>(6);
	private static Vector<Texture> thrustTextures = new Vector<Texture>(6);
	private static Vector<Texture> reverseTextures = new Vector<Texture>(6);
	private static String colorNames[] = {"Green", "Yellow", "Orange", "Purple", "Blue", "Pink"};
	
	public ShipColor() {
		for(int i = 0; i < 6; i++) {
			Texture newStatic = new Texture(Gdx.files.internal("shipStatic" + i + ".png"));
			Texture newThrust = new Texture(Gdx.files.internal("shipThrust" + i + ".png"));
			Texture newRev = new Texture(Gdx.files.internal("shipReverse" + i + ".png"));
			
			texturesMap.put(colorNames[i].toLowerCase() + "static" , newStatic);
			texturesMap.put(colorNames[i].toLowerCase() + "thrust" , newThrust);
			texturesMap.put(colorNames[i].toLowerCase() + "rev" , newRev);
			staticTextures.add(newStatic);
			thrustTextures.add(newThrust);
			reverseTextures.add(newRev);
		}
	}
	
	/**
	 * Get the texture 
	 * @param index Index of the color. Green - 0, Yellow - 1, Orange - 2, Purple - 3, Blue - 4, Pink - 5
	 * @param type String type of texture. Can be "static", "thrust", or "reverse".
	 * @return Texture of the color index
	 */
	public Texture getTexture(int index, String type) {
		if(type.equals("static")) {
			return staticTextures.get(index);
		} else if (type.equals("thrust")) {
			return thrustTextures.get(index);
		} else if (type.equals("reverse")) {
			return reverseTextures.get(index);
		}
		
		return null;
	}
	
	public Texture getTexture(String textureName) {
		return texturesMap.get(textureName);
	}
	
	public String getColorName(int index) {
		return colorNames[index];
	}

}
