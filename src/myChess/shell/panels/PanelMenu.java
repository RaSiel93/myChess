package myChess.shell.panels;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import myChess.Discription;
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

		JMenu game = new JMenu("����");

		JMenuItem newGame = new JMenuItem("����� ����");
		newGame.addActionListener(new NewGame());
		newGame.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));

		JMenuItem loadGame = new JMenuItem("��������� ����");
		loadGame.addActionListener(new LoadGame());
		loadGame.setAccelerator(KeyStroke.getKeyStroke('L', CTRL_DOWN_MASK));
		
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
		game.add(loadGame);
		game.add(undo);
		game.add(redo);
		game.add(exit);

		add(game);
		// ---------------------------------

		JMenu multiplayer = new JMenu("������� ����");

		JMenuItem createMultiplayer = new JMenuItem("������� ����");
		createMultiplayer.addActionListener(new CreateMultiplayer());

		JMenuItem connectMultiplayer = new JMenuItem("������������ � ����");
		connectMultiplayer.addActionListener(new ConnectMultiplayer());

		JMenuItem disconnectMultiplayer = new JMenuItem("����������� �� ����");
		disconnectMultiplayer.addActionListener(new DisconnectMultiplayer());

		multiplayer.add(createMultiplayer);
		multiplayer.add(connectMultiplayer);
		multiplayer.add(disconnectMultiplayer);

		add(multiplayer);
		// ---------------------------------

		JMenu option = new JMenu("���������");
		JMenu colors = new JMenu("�������� �����");

		JRadioButton styleClassic = new JRadioButton("������������");
		styleClassic.setMnemonic(KeyEvent.VK_C);
		styleClassic.addActionListener(new SwitchStyleChessboard(
				StyleChessboard.Classic));

		JRadioButton styleBrown = new JRadioButton("����������");
		styleBrown.setMnemonic(KeyEvent.VK_B);
		styleBrown.addActionListener(new SwitchStyleChessboard(
				StyleChessboard.Brown));
		styleBrown.setSelected(true);

		JCheckBox backlight = new JCheckBox("���������");
		backlight.setSelected(true);
		backlight.addActionListener(new Backlight());
		backlight.setMnemonic(KeyEvent.VK_L);

		JCheckBox reversChess = new JCheckBox("���������� �����");
		reversChess.setSelected(false);
		reversChess.addActionListener(new ReversChess());
		reversChess.setMnemonic(KeyEvent.VK_R);

		JCheckBox modeRead = new JCheckBox("����� ���������");
		modeRead.setSelected(false);
		modeRead.addActionListener(new ModeRead());
		modeRead.setMnemonic(KeyEvent.VK_M);

		ButtonGroup group = new ButtonGroup();
		group.add(styleClassic);
		group.add(styleBrown);

		option.add(colors);
		colors.add(styleClassic);
		colors.add(styleBrown);

		option.add(backlight);
		option.add(reversChess);
		option.add(modeRead);

		add(option);
		// ----------------------------------

		JMenu aboutMenu = new JMenu("� ���������");
		JMenuItem aboutVersions = new JMenuItem("� ������� (����. �� ����.)");
		aboutVersions.addActionListener(new aboutDiscription());

		JMenuItem aboutAuthor = new JMenuItem("�� ������");
		aboutAuthor.addActionListener(new About());

		aboutMenu.add(aboutVersions);
		aboutMenu.add(aboutAuthor);

		add(aboutMenu);
	}

	private class NewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.newGame();
		}
	}

	private class LoadGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controller.loadGame();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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

	private class CreateMultiplayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controller.createMultiplayer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ConnectMultiplayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.connectMultiplayer();
		}
	}

	private class DisconnectMultiplayer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.disconnectMultiplayer();
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

	private class ReversChess implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frameMain.reversChess();
		}
	}

	private class ModeRead implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.switchModeRead();
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

	private class aboutDiscription implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Discription.write("discription.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
