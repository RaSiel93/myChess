package myChess.controller;

import java.io.IOException;
import java.net.ServerSocket;


public class ThreadConnect extends Thread {
	private Controller controller;
	private ServerSocket serverSocket = null;
	private KKMultiServerThread kKMultiServerThread = null;
	
	public ThreadConnect(Controller controller, ServerSocket serverSocket) {
		super("ThreadConnect");
		this.controller = controller;
		this.serverSocket = serverSocket;
	}

	public void run() {
		boolean listening = true;
		while(listening){
			try {
				new KKMultiServerThread(controller, this.serverSocket.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void pushObject(Object object) {
		kKMultiServerThread.pushObject(object);
	}
}