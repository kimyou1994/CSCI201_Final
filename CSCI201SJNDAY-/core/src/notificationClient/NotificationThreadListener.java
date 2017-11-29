package notificationClient;

import java.io.IOException;

public class NotificationThreadListener extends Thread {
	NotificationClient notificationClient;
	public NotificationThreadListener(NotificationClient notificationClient) {
		this.notificationClient = notificationClient;
		this.start();
	}
	
	public void run() {
		while(true) {
			String message = "";

			if(notificationClient.getCliInput().equals(null)) {
				break;
			}
			try {
				message = (String)notificationClient.getCliInput().readObject();
				notificationClient.GetNotificatonQ().add(message);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
