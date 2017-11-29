package notificationServer;

import java.util.Random;

public class NotificationAutoSend extends Thread {
	private NotifcationServer server;
	public NotificationAutoSend(NotifcationServer server) {
		setServer(server);
		this.start();
	}
	
	public void run() {
		Random generator = new Random();
		while(true) {
			int rn = generator.nextInt(server.getServerMessages().length);
			server.writeMessage(server.getServerMessages()[rn]);
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public NotifcationServer getServer() {
		return server;
	}

	public void setServer(NotifcationServer server) {
		this.server = server;
	}
}
