package Prompts;

import Server.GameMessage;

public class ShootsSetupPrompt extends GameMessage {
	private static final long serialVersionUID = -490828542478707026L;
	public String CREATE__CHOOSE_USERNAME = "Please type in a username";
	public String CREATE__CHOOSE_PASSWORD = "Please type in a password";

	public ShootsSetupPrompt() {
		super();
	}
}
