package Shell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Controller.Controller;

public class ListenerMoveCursor implements MouseMotionListener {

	private Controller controller;

	ListenerMoveCursor(Controller controller) {
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
