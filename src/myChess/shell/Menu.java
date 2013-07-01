package myChess.shell;

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
import myChess.types.StyleColor;

public class Menu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private FrameMain frameMain;
	private Controller controller;

	Menu(FrameMain frameMain, Controller controller) {
		this.frameMain = frameMain;
		this.controller = controller;

		JMenu game = new JMenu("����");

		JMenuItem newGame = new JMenuItem("����� ����");
		newGame.addActionListener(new NewGame());
		newGame.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));
		
		JMenuItem undo = new JMenuItem("�������� ���");
		undo.addActionListener(new Undo());
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', CTRL_DOWN_MASK));

		JMenuItem redo = new JMenuItem("������� ���");
		redo.addActionListener(new Redo());
		redo.setAccelerator(KeyStroke.getKeyStroke('Y', CTRL_DOWN_MASK));

		JMenuItem exit = new JMenuItem("�����");
		exit.addActionListener(new Exit());
		exit.setAccelerator(KeyStroke.getKeyStroke('Q', CTRL_DOWN_MASK));

		game.add(newGame);
		game.add(undo);
		game.add(redo);
		game.add(exit);

		add(game);
		// ---------------------------------

		JMenu option = new JMenu("���������");
		JMenu colors = new JMenu("�������� �����");

		JRadioButton styleClassic = new JRadioButton("������������");
		styleClassic.setMnemonic(KeyEvent.VK_C);
		styleClassic.addActionListener(new SwitchColor(StyleColor.Classic));

		JRadioButton styleBrown = new JRadioButton("����������");
		styleBrown.setMnemonic(KeyEvent.VK_B);
		styleBrown.addActionListener(new SwitchColor(StyleColor.Brown));
		styleBrown.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(styleClassic);
		group.add(styleBrown);

		option.add(colors);
		colors.add(styleClassic);
		colors.add(styleBrown);

		add(option);
		// ----------------------------------

		JMenu aboutMenu = new JMenu("� ���������");
		JMenuItem about = new JMenuItem("�� ������");
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

	private class SwitchColor implements ActionListener {
		private StyleColor color;

		SwitchColor(StyleColor color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frameMain.switchColor(this.color);
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
