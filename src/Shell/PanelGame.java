package Shell;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import Controller.Controller;
import Main.StyleColor;

public class PanelGame extends JPanel {
	private static final long serialVersionUID = 1L;
	private PanelChessView panelChessView;
	private JPanel panel1;
	private JPanel panel2;

	PanelGame(Controller controller) {

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

	public void switchColor(StyleColor color) {
		this.panelChessView.switchColor(color);
	}
}
