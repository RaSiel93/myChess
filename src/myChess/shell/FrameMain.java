package myChess.shell;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import about.DialogAbout;

import myChess.Discription;
import myChess.controller.Controller;
import myChess.controller.Status;
import myChess.shell.panels.PanelGame;
import myChess.shell.panels.PanelMenu;
import myChess.shell.panels.PanelStatus;
import myChess.types.StyleChessboard;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int sizeCell = 75;

	private PanelGame panelGame;
	private PanelStatus panelStatus;
	private PanelMenu panelMenu;
	private Controller controller;

	public FrameMain(Controller controller, Status status) {
		this.controller = controller;

		panelMenu = new PanelMenu(this, this.controller);
		panelGame = new PanelGame(controller);
		panelStatus = new PanelStatus(status);

		setLayout(new BorderLayout());
		add(panelMenu, BorderLayout.NORTH);
		add(panelGame, BorderLayout.CENTER);
		add(panelStatus, BorderLayout.SOUTH);

		addWindowListener(new Exit());
		pack();
		setTitle("myChess " + Discription.version);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private class Exit extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	public void update() {
		panelStatus.update();
	}

	public void switchStyleChessboard(StyleChessboard color) {
		this.panelGame.switchStyleChessboard(color);
	}

	public void switchBacklight() {
		this.panelGame.switchBacklight();
	}

	public void showDialogAbout() throws IOException {
		DialogAbout dialogAbout = new DialogAbout(this, "2", "игра - Шахматы",
				"29.06.2013 16:50:30");
		dialogAbout.setVisible(true);
	}
}
