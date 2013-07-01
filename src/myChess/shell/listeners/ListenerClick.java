package myChess.shell.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import myChess.controller.Controller;

public class ListenerClick implements MouseListener {
	private Controller controller;

	public ListenerClick(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (controller.isGame()) {
			controller.action();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
