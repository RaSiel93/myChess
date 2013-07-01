package myChess.shell;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import About.DialogAbout;
import myChess.controller.Controller;
import myChess.controller.Status;
import myChess.types.StyleColor;
import myChess.shell.panels.*;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int sizeCell = 75;
	static String version = "0.8.0";
	
	private PanelGame panelGame;
	private PanelStatus panelStatus;
	private Menu menu;
	private Controller controller;

	public FrameMain(Controller controller, Status status) {
		this.controller = controller;
		this.controller.start();
		
		menu = new Menu(this, this.controller);
		panelGame = new PanelGame(controller);
		panelStatus = new PanelStatus(status);

		setLayout(new BorderLayout());
		add(menu, BorderLayout.NORTH);
		add(panelGame, BorderLayout.CENTER);
		add(panelStatus, BorderLayout.SOUTH);
		
		pack();
		setTitle("myChess " + FrameMain.version);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public int getSizeCell() {
		return sizeCell;
	}

	public void update() {
		panelStatus.update();
	}

	public void switchColor(StyleColor color) {
		this.panelGame.switchColor(color);
	}
	
	public void showDialogAbout() throws IOException {
		DialogAbout dlgAbout = new DialogAbout(this, "2", "игра - Шахматы", "29.06.2013 16:50:30");
		dlgAbout.setVisible(true);
	}
}
