package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Messages.ErrorMessage;
import Messages.LoginSuccessMessage;
import Messages.SignupErrorMessage;
import Messages.SignupSuccessMessage;
import Messages.UserInfoRequest;
import Messages.UserQuitMessage;
import Messages.UserSignupRequest;

public class GameServerThread extends Thread {
	//private PrintWriter pw;
		//private BufferedReader br;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private GameServer gameServer;
		//server thread constructor that creates a new object input and output stream
		//also calls initial prompt to start the prompting process
		public GameServerThread(Socket s, GameServer gameServer) {
			try {
				this.gameServer = gameServer;
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				this.start();
				//initialPrompt();
			} catch (IOException ioe) {
				System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
			}
		}
		
		//sends blackjack message object
		public void sendMessage(GameMessage gm) {
			try {
				oos.reset();
				oos.writeObject(gm);
				oos.flush();
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		public void sendLoginSuccessMessage(LoginSuccessMessage lsm) {
			try {
				oos.reset();
				oos.writeObject(lsm);
				oos.flush();
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		public void sendSignupSuccessMessage(SignupSuccessMessage ssm) {
			try {
				oos.reset();
				oos.writeObject(ssm);
				oos.flush();
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		public void sendErrorMessage(ErrorMessage em) {
			try {
				oos.reset();
				oos.writeObject(em);
				oos.flush();
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
		public void sendSignupErrorMessage(SignupErrorMessage sem) {
			try {
				oos.reset();
				oos.writeObject(sem);
				oos.flush();
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
		
//		private void initialPrompt() {
//			System.out.println("initial prompt");
//			ShootsSetupPrompt message = new ShootsSetupPrompt();
//			sendMessage(message);
//		}
		
		public void run() {
			try {
				while(true) { //check for the errors in the if statements and if an error exists, create errormessage response and send it back
					GameMessage initial_message = (GameMessage)ois.readObject();
					if(initial_message instanceof UserInfoRequest) {
						System.out.println("in thread instanceof Userinfo");
						//check if login returns true or false. if true, send message success to client. if false, send errormessage to client
						boolean loginSuccess = false;
						initial_message = (UserInfoRequest)initial_message;
						loginSuccess = gameServer.login(((UserInfoRequest) initial_message).getUsername(), ((UserInfoRequest) initial_message).getPassword());
						if(loginSuccess == false) {
							System.out.println("in thread login failure");
//							LoginScreen.reset();
//							LoginScreen.errorMessage();
							ErrorMessage errorMessage = new ErrorMessage();
							sendErrorMessage(errorMessage);
						}
						else {
							System.out.println("in thread login success");
							//popup gamescreen NOT login screen gameServer.createLogin();
//							LoginScreen.loginSuccess();
							LoginSuccessMessage loginSuccessMessage = new LoginSuccessMessage();
							loginSuccessMessage.setUserData(gameServer.readData(((UserInfoRequest) initial_message).getUsername()));
							System.out.println("the user data score is: " +loginSuccessMessage.getUserData().getCurrentMoney());
							System.out.println("after server retrieves data");
							sendLoginSuccessMessage(loginSuccessMessage);
						}
					}
					else if(initial_message instanceof UserSignupRequest) {
						System.out.println("i should be here in thread signuprequest");
						boolean signupSuccess = false;
						initial_message = (UserSignupRequest)initial_message;
						signupSuccess = gameServer.signup(((UserSignupRequest) initial_message).getUsername(), ((UserSignupRequest) initial_message).getPassword());
						if(signupSuccess == false) {
							System.out.println("in thread signup failure");
//							LoginScreen.reset();
//							LoginScreen.errorMessage();
							SignupErrorMessage errorMessage = new SignupErrorMessage();
							sendSignupErrorMessage(errorMessage);
						}
						else {
							System.out.println("in thread signup success");
							//popup gamescreen NOT login screen gameServer.createLogin();
//							LoginScreen.loginSuccess();
							SignupSuccessMessage signupSuccessMessage = new SignupSuccessMessage();
							sendSignupSuccessMessage(signupSuccessMessage);
						}
					}
					
					//Added by joshl
					//Saves user data when the user quits
					else if(initial_message instanceof UserQuitMessage) {
						UserQuitMessage dataMessage = (UserQuitMessage)initial_message;
						System.out.println("dataMessage.getSavedData().getUserName()");
						gameServer.saveData(dataMessage.getSavedData());
					}
				}
			} catch (IOException ioe) {
				System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe in ServerThread.run()" + cnfe.getMessage());
			}
		}
}
