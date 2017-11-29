package notificationServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class NotifcationServer extends Thread {
	private Vector<NotificationConn> allClients;
	private Vector<NotificationConn> deadClients;
	private ServerSocket mainGameServer;
	private String[] serverMessages;
	private NotificationAutoSend autoServer;
	public NotifcationServer() {
		allClients = new Vector<NotificationConn>();
		setDeadClients(new Vector<NotificationConn>());
		setServerMessages(new String[]{"Go into the store to buy upgrades!", 
										"Check out our twitter for more updates! @ShootsAndLooters",
										"You can buy more health at the store!",
										"You can replenish up to 50 health at the base!",
										});
		
		//Prompt user to bind the server to a port
		while(!this.startServer()) {
		}
		
		System.out.println("Notification server successfully created");
		
		this.start();
		
		autoServer = new NotificationAutoSend(this);
		
		//Listen for client connections
		while(true) {
			try {
				Socket newPlayerSocket = mainGameServer.accept();
				allClients.add(new NotificationConn(newPlayerSocket, this));
				System.out.println("Notificaiton server got connection.");
			} catch (IOException e) {
				System.out.println("Connection failed");
			}
		}
	}
	
	/**
	 * Creates the server socket.
	 * @return True if the server socket was created successfully, false otherwise.
	 */
	private boolean startServer() {
		Boolean canStart = true;
		
		//Try to create the server socket
		try {
			this.mainGameServer = new ServerSocket(6790); //Binds to port
		} catch(IOException e) {
			System.out.println("Invalid port number");
			canStart = false;
		}
		return canStart;
	}
	
	/**
	 * Send messages to everyone
	 */
	public void run() {
		Scanner userInput = new Scanner(System.in);
		String notification;
		while(true) {
			notification = userInput.nextLine();
			if(!allClients.isEmpty()) {
				for(NotificationConn gameThread : allClients) {
					gameThread.writeMessage(notification);
				}
				for(NotificationConn deadThread : deadClients) {
					allClients.removeElement(deadThread);
				}
				deadClients.clear();
			}
		}
	}
	
	public void writeMessage(String message) {
		if(!allClients.isEmpty()) {
			for(NotificationConn gameThread : allClients) {
				gameThread.writeMessage(message);
			}
			for(NotificationConn deadThread : deadClients) {
				allClients.removeElement(deadThread);
			}
			deadClients.clear();
		}
	}
	
	public static void main(String [] args) {
		NotifcationServer notiServer = new NotifcationServer();
	}

	public Vector<NotificationConn> getDeadClients() {
		return deadClients;
	}

	public void setDeadClients(Vector<NotificationConn> deadClients) {
		this.deadClients = deadClients;
	}

	public String[] getServerMessages() {
		return serverMessages;
	}

	public void setServerMessages(String[] serverMessages) {
		this.serverMessages = serverMessages;
	}
}
