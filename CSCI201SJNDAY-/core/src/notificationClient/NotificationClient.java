package notificationClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import gameClass.UserShip;

public class NotificationClient{
	private ObjectInputStream clientInput;
	private String label;
	private Queue<String> notificatonQ = new ConcurrentLinkedDeque<String>();
	private long lastNotiMessage;
	private BitmapFont font;
	private Socket clientSocket;
	private NotificationThreadListener tListen;
	
	public NotificationClient() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 14;
		font = generator.generateFont(parameter);
		label = "";
		try {
			if(connect()) {
				new ObjectOutputStream(clientSocket.getOutputStream());
				clientInput = new ObjectInputStream(clientSocket.getInputStream());
				tListen = new NotificationThreadListener(this);
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Brings up prompts to connect to a server
	 * @return Returns true if a server connection is made, false otherwise.
	 */
	private boolean connect() {
		Boolean canConnect = true;
		lastNotiMessage = System.currentTimeMillis();
		//Create clientSocket
		try {
			this.clientSocket = new Socket("localhost", 6790);
		} catch (IOException e) {
			System.out.println("Unable to connect to notification server as client.");
			canConnect = false;
		}
		return canConnect;
	}
	
	public void draw(SpriteBatch batch) {
		//Update label if previous message expired
		if(System.currentTimeMillis() - lastNotiMessage >  10000) {
			if(!notificatonQ.isEmpty()) {
				label = "New Message:" + notificatonQ.remove();
				lastNotiMessage = System.currentTimeMillis();
			} else {
				label = "";
			}
		}
		
		//Start drawing to batch
		font.setColor(Color.WHITE);
		font.draw(batch, label, 25, 703);
	}
	
	public ObjectInputStream getCliInput() {
		return clientInput;
	}
	
	public Queue<String> GetNotificatonQ() {
		return notificatonQ;
	}
}