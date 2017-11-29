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
import Messages.UserSignupRequest;
import gameClass.UserData;
import screens.LoginScreen;

public class GameClient extends Thread{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//private ShootsAndLooters mGame;
	private LoginScreen login;
	private UserData userData;
	//client connects to server or ioexception thrown
	//creates new object input and output stream to grab input/output from socket
	public GameClient(String hostname, int port, LoginScreen loginScreen) {
		this.login = loginScreen;
		try {
			System.out.println("Trying to connect to " + hostname + ":" + port);
			Socket s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			this.start();			
		} catch (IOException ioe) {
			System.out.println("ioe in Client constructor: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(GameMessage gm) {
		try {
			oos.reset();
			oos.writeObject(gm);
			oos.flush();
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			while(true) {
				System.out.println("first object waiting...");
				GameMessage initial_message = (GameMessage)ois.readObject();
				GameMessage result = null;
				System.out.println("passed first object waiting...");
				boolean success = false;
				if(initial_message instanceof LoginSuccessMessage || initial_message instanceof ErrorMessage) {
					System.out.println("first object read");
//					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					do {
						System.out.println("before the instances...");
						if(initial_message instanceof LoginSuccessMessage) {
							System.out.println("in client login success");
							initial_message = (LoginSuccessMessage)initial_message;				
							login.setGameSuccessFlag(((LoginSuccessMessage) initial_message).getUserData());
							this.userData = ((LoginSuccessMessage) initial_message).getUserData();
						}
						else if(initial_message instanceof ErrorMessage) {
							System.out.println("in client login error");
							//call loginscreen reset function
							initial_message = (ErrorMessage)initial_message;
							login.setGameFailFlag();
						}
						Object msg = ois.readObject();
						if(!(msg instanceof ErrorMessage) && (msg instanceof GameMessage)) {
							result = (GameMessage) msg;
							success = true;
						} 
					}while(!success && result == null);
					login.setGameSuccessFlag(((LoginSuccessMessage)result).getUserData());
					//login.setGameSuccessFlag(this.userData);
				}
				else if(initial_message instanceof SignupSuccessMessage || initial_message instanceof SignupErrorMessage) {
					boolean msgLoginSuccess = false;
					do {
						if(initial_message instanceof SignupSuccessMessage) {
							System.out.println("in client signup success");
							initial_message = (SignupSuccessMessage)initial_message;
							login.setGameSignupSuccessFlag();
						}
						else if(initial_message instanceof SignupErrorMessage) {
							System.out.println("in client signup error");
							//call loginscreen reset function
							initial_message = (SignupErrorMessage)initial_message;
							login.setGameSignupFailFlag();
						}
						Object msg = ois.readObject();
						if(!(msg instanceof SignupErrorMessage) && (msg instanceof GameMessage)) {
							result = (GameMessage) msg;
							success = true;
							if(msg instanceof LoginSuccessMessage || msg instanceof ErrorMessage) {
								msgLoginSuccess = true;
							}
						} 
					}while(!success && result == null);
					if(msgLoginSuccess == false) {
						login.setGameSignupSuccessFlag();
					}
				}
				System.out.println("waiting for next object....");
				//initial_message = (GameMessage)ois.readObject();
			}
		}catch (IOException ioe) {
			System.out.println("ioe in Client.run(): " + ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
	
	public void getLoginInfo(String userName, String password) {
		UserInfoRequest userInfoMessage = new UserInfoRequest();
		userInfoMessage.setUsername(userName);
		userInfoMessage.setPassword(password);
		sendMessage(userInfoMessage);
	}
	
	public void getSignupInfo(String userName, String password) {
		UserSignupRequest userSignupMessage = new UserSignupRequest();
		userSignupMessage.setUsername(userName);
		userSignupMessage.setPassword(password);
		sendMessage(userSignupMessage);
	}

	
	//client connecting to server
	//must input ip address and port number
	//either connects or outputs invalid connection
//	public static void main(String [] args) {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		GameClient client;
//		boolean isConnected = false;
//		System.out.println("Welcome to Shooters 'n Looters!");
//		do {
//			try {
//				System.out.println("Please enter the IP address");
//				String ipAddressInput = br.readLine();
//				System.out.println("Please enter the port number");
//				String portInput = br.readLine();
//				int portNumber = Integer.parseInt(portInput);
//				client = new GameClient(ipAddressInput, portNumber);
//				isConnected = true;
//			}catch(IllegalArgumentException iae) {
//				System.out.println("Unable to connect to server with the provided fields");
//			}catch (SocketException se) {
//				System.out.println("Unable to connect to server with the provided fields");
//			}catch (IOException e) {
//				System.out.println("Unable to connect to server with the provided fields");
//			}
//		}while(!isConnected);
//	}
}
