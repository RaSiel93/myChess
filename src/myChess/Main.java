package myChess;

import myChess.controller.Controller;
import myChess.controller.Status;
import myChess.model.Chess;
import myChess.shell.FrameMain;

public class Main {
	public static String version = "0.8.3";
	public static void main(String[] args) {
 		Chess chess = new Chess();
		Status status = new Status();

		Controller controller = new Controller();
		controller.setModel(chess);
		controller.setStatus(status);
		
		FrameMain frameMain = new FrameMain(controller, status);
		
		controller.setFrame(frameMain);
	}
}