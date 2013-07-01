package myChess.shell.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import myChess.controller.Controller;
import myChess.shell.FrameMain;

public class ListenerMoveCursor implements MouseMotionListener {

	private Controller controller;

	public ListenerMoveCursor(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		controller.setCellActive(e.getX() / FrameMain.sizeCell, e.getY()
				/ FrameMain.sizeCell);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		controller.setCellActive(e.getX() / FrameMain.sizeCell, e.getY()
				/ FrameMain.sizeCell);
	}

}
