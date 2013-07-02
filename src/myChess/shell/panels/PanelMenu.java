package myChess.shell.panels;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import myChess.controller.Controller;
import myChess.shell.FrameMain;
import myChess.types.StyleChessboard;

public class PanelMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private FrameMain frameMain;
	private Controller controller;

	public PanelMenu(FrameMain frameMain, Controller controller) {
		this.frameMain = frameMain;
		this.controller = controller;

		JMenu game = new JMenu("Игра");

		JMenuItem newGame = new JMenuItem("Новая игра");
		newGame.addActionListener(new NewGame());
		newGame.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));

		JMenuItem undo = new JMenuItem("Отменить ход");
		undo.addActionListener(new Undo());
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', CTRL_DOWN_MASK));

		JMenuItem redo = new JMenuItem("Вернуть ход");
		redo.addActionListener(new Redo());
		redo.setAccelerator(KeyStroke.getKeyStroke('Y', CTRL_DOWN_MASK));

		JMenuItem exit = new JMenuItem("Выйти");
		exit.addActionListener(new Exit());
		exit.setAccelerator(KeyStroke.getKeyStroke('Q', CTRL_DOWN_MASK));

		game.add(newGame);
		game.add(undo);
		game.add(redo);
		game.add(exit);

		add(game);
		// ---------------------------------

		JMenu option = new JMenu("Настройки");
		JMenu colors = new JMenu("Цветовая гамма");

		JRadioButton styleClassic = new JRadioButton("Классическая");
		styleClassic.setMnemonic(KeyEvent.VK_C);
		styleClassic.addActionListener(new SwitchStyleChessboard(
				StyleChessboard.Classic));

		JRadioButton styleBrown = new JRadioButton("Коричневая");
		styleBrown.setMnemonic(KeyEvent.VK_B);
		styleBrown.addActionListener(new SwitchStyleChessboard(
				StyleChessboard.Brown));
		styleBrown.setSelected(true);

		JMenuItem backlight = new JMenuItem("Подсветка");
		backlight.addActionListener(new Backlight());
		backlight.setAccelerator(KeyStroke.getKeyStroke('B', CTRL_DOWN_MASK));

		ButtonGroup group = new ButtonGroup();
		group.add(styleClassic);
		group.add(styleBrown);

		option.add(colors);
		colors.add(styleClassic);
		colors.add(styleBrown);

		option.add(backlight);

		add(option);
		// ----------------------------------

		JMenu aboutMenu = new JMenu("О программе");
		JMenuItem about = new JMenuItem("Об авторе");
		about.addActionListener(new About());
		aboutMenu.add(about);

		add(aboutMenu);
	}

	private class NewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.newGame();
		}
	}

	private class Undo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.undoHistory();
		}
	}

	private class Redo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.redoHistory();
		}
	}

	private class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class SwitchStyleChessboard implements ActionListener {
		private StyleChessboard color;

		SwitchStyleChessboard(StyleChessboard color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frameMain.switchStyleChessboard(this.color);
		}
	}

	private class Backlight implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frameMain.switchBacklight();
		}
	}

	private class About implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				frameMain.showDialogAbout();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
