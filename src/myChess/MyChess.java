package myChess;

import myChess.controller.Controller;
import myChess.model.Chess;
import myChess.shell.FrameMain;

public class MyChess {
	public static void main(String[] args) {
		Controller controller = new Controller(new Chess());
		FrameMain frameMain = new FrameMain(controller);
		controller.setFrame(frameMain);
	}
}