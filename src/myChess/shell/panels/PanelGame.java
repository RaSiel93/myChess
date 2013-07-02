package myChess.shell.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import myChess.controller.Controller;
import myChess.shell.listeners.ListenerClick;
import myChess.shell.listeners.ListenerMoveCursor;
import myChess.types.StyleChessboard;

public class PanelGame extends JPanel {
	private static final long serialVersionUID = 1L;
	private PanelChessView panelChessView;
	private JPanel panel1;
	private JPanel panel2;

	public PanelGame(Controller controller) {

		setBackground(new Color(200, 180, 120));

		this.panelChessView = new PanelChessView(controller);
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(50, 0));
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(50, 0));

		panelChessView.addMouseListener(new ListenerClick(controller));
		panelChessView.addMouseMotionListener(new ListenerMoveCursor(
				controller));

		add(panel1);
		add(panelChessView);
		add(panel2);
	}

	public void switchStyleChessboard(StyleChessboard color) {
		this.panelChessView.switchStyleChessboard(color);
	}
}
