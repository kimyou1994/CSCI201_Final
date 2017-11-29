package Messages;

import Server.GameMessage;

public class UserSignupRequest extends GameMessage{
	private static final long serialVersionUID = -4723916881650927534L;
	private String userName;
	private String password;
	public UserSignupRequest() {
		super();
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
