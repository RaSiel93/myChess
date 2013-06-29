package Shell;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Controller.Controller;
import Main.Cell;

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
			if (controller.checkGameOver()) {
				controller.stop();
			} else {
				if (controller.checkFriendChessmen()) {
					controller.setChessmenActive();
				} else if (controller.getChessmenActive() != null) {
					if (controller.checkMove()) {
						controller.move();
						
						Cell shah = controller.checkShah();						
						if (shah != null) {
							controller.setCellDanger(shah);
							controller.setCommentShah();
							controller.update();
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
