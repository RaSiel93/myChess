package myChess.shell.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import myChess.controller.Controller;
import myChess.shell.FrameMain;
import myChess.types.Cell;

public class ListenerMoveCursor implements MouseMotionListener {

	private Controller controller;

	public ListenerMoveCursor(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
			controller.setCellActive(new Cell(FrameMain.getCoord(e.getX()
					/ FrameMain.sizeCell), FrameMain.getCoord(e.getY()
					/ FrameMain.sizeCell)));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
			controller.setCellActive(new Cell(FrameMain.getCoord(e.getX()
					/ FrameMain.sizeCell), FrameMain.getCoord(e.getY()
					/ FrameMain.sizeCell)));
	}
}
