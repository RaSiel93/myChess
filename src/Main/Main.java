package Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Controller.Controller;
import Controller.Status;
import Model.Chess;
import Shell.FrameMain;

public class Main {
	public static void main(String[] args) {
		Controller controller = new Controller();
 		Chess chess = new Chess();
		Status status = new Status();

		controller.setModel(chess);
		controller.setStatus(status);
		
		FrameMain frameMain = new FrameMain(controller, status);
		
		controller.setFrame(frameMain);
		
		frameMain.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}