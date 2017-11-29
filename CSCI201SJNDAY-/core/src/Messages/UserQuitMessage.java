package Messages;

import Server.GameMessage;
import gameClass.UserData;

public class UserQuitMessage extends GameMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7694927382949075128L;
	private UserData savedData;
	
	public UserQuitMessage(UserData savedData) {
		super();
		setSavedData(savedData);
	}
	
	public UserData getSavedData() {
		return savedData;
	}
	public void setSavedData(UserData savedData) {
		this.savedData = savedData;
	}
	
	
}
