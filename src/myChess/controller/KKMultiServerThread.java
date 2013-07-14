package myChess.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;
	private Controller controller;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public KKMultiServerThread(Controller controller, Socket socket) {
		super("KKMultiServerThread");
		this.controller = controller;
		this.socket = socket;
	}

	public void run() {
		try {
			out = new ObjectOutputStream(
					socket.getOutputStream());
			in = new ObjectInputStream(
					socket.getInputStream());

			Protocol protocol = new Protocol(controller);

			pushObject(controller.getChess());
			controller.updateStatus();
			
			while (true) {
				out.writeObject(protocol.execute(in.readObject()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pushObject(Object object){
		try {
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}