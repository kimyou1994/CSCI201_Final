package Messages;

import Server.GameMessage;
import gameClass.UserData;

public class LoginSuccessMessage extends GameMessage{
	private UserData userData;

	private static final long serialVersionUID = -1327836009173813201L;
	public LoginSuccessMessage() {
		super();
	}
	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
}
