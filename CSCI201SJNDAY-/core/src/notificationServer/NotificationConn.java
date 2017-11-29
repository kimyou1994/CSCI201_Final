package notificationServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NotificationConn {
	
	private Socket playerSocket;
	private NotifcationServer mainServe;
	private ObjectOutputStream playerOutput;
	
	public NotificationConn(Socket newPlayerSocket, NotifcationServer newNotifcationServer) {
		this.playerSocket = newPlayerSocket;
		this.mainServe = newNotifcationServer;
		
		//Create sockets
		try {
			new ObjectInputStream(playerSocket.getInputStream());
			playerOutput= new ObjectOutputStream(playerSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
	}
	
	public void writeMessage(String message) {
		try {
			if(!playerOutput.equals(null)) {
				playerOutput.writeObject(message);
				playerOutput.flush();
			} else {
				mainServe.getDeadClients().add(this);
			}
		} catch (IOException e) {
			System.out.println("IOException in write message" + e.getMessage());
			mainServe.getDeadClients().add(this);
		}
	}

}
