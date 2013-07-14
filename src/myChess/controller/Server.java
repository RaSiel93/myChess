package myChess.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Server {
	private static Controller controller;
	static ServerSocket serverSocket = null;
	static ThreadConnect threadConnect = null;
	
	public Server(Controller controller) {
		Server.controller = controller;
		try {
			InetAddress ownIP;
			ownIP = InetAddress.getLocalHost();
			System.out.println(ownIP.getHostAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	public static void runServer() throws IOException {
		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
		}
		threadConnect = new ThreadConnect(Server.controller, Server.serverSocket);
		threadConnect.start();
	}

	public void stopServer() throws IOException {
		//this.kKMultiServerThread.stop();
	}

	public void pushObject(Object object) {
		threadConnect.pushObject(object);
	}
}
