package myChess.shell.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;

import javax.swing.JPanel;

import myChess.model.chessmens.Chessmen;
import myChess.controller.Controller;
import myChess.shell.FrameMain;
import myChess.types.Cell;
import myChess.types.StyleChessboard;
import myChess.types.ColorChessmen;

public class PanelChessView extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private Color colorChessboard1;
	private Color colorChessboard2;
	private boolean backlight;

	public PanelChessView(Controller controller) {
		this.controller = controller;
		this.backlight = true;
		switchStyleChessboard(StyleChessboard.Brown);
		setPreferredSize(new Dimension(600, 600));
	}

	public void switchBackLight() {
		this.backlight = !this.backlight;
		updateUI();
	}

	public void switchStyleChessboard(StyleChessboard color) {
		switch (color) {
		case Classic:
			switchClassicStyle();
			break;
		case Brown:
			switchBrownStyle();
			break;
		default:
			break;
		}
		updateUI();
	}

	private void switchClassicStyle() {
		this.colorChessboard1 = new Color(0, 0, 0);
		this.colorChessboard2 = new Color(255, 255, 255);
	}

	private void switchBrownStyle() {
		this.colorChessboard1 = new Color(95, 55, 20);
		this.colorChessboard2 = new Color(220, 200, 130);
	}

	// private void switchGreenStyle() {
	// this.colorChessboard1 = new Color(30, 80, 30);
	// this.colorChessboard2 = new Color(120, 150, 120);
	// }
	//
	// private void switchSeaStyle2() {
	// this.colorChessboard1 = new Color(90, 50, 20);
	// this.colorChessboard2 = new Color(220, 200, 130);
	// }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		printChessboard(g2);
		if (this.backlight) {
			printAvailablePaths(g2);
		}
		printDangerChessmen(g2);
		printDangerPaths(g2);
		printActiveCell(g2);
		printActiveChessmen(g2);
		printChessmens(g2);
	}

	private Double getCellSquare(Cell cell) {
		return new Rectangle2D.Double(FrameMain.getCoord(cell.getX())
				* FrameMain.sizeCell, FrameMain.getCoord(cell.getY())
				* FrameMain.sizeCell, FrameMain.sizeCell, FrameMain.sizeCell);
	}

	private Double getSmallSquare(Cell cell) {
		return new Rectangle2D.Double(FrameMain.getCoord(cell.getX())
				* FrameMain.sizeCell + FrameMain.sizeCell * 0.05,
				FrameMain.getCoord(cell.getY()) * FrameMain.sizeCell + FrameMain.sizeCell
						* 0.05, FrameMain.sizeCell * 0.9,
				FrameMain.sizeCell * 0.9);
	}

	private void drawCell(Graphics2D g2, Cell cell, Color color) {
		g2.setColor(color);
		g2.fill(getCellSquare(cell));
		g2.setColor(Color.white);
		g2.draw(getSmallSquare(cell));
	}

	private void printChessboard(Graphics2D g2) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x + y) % 2 == 1) {
					g2.setColor(this.colorChessboard1);
				} else {
					g2.setColor(this.colorChessboard2);
				}
				g2.fill(getCellSquare(new Cell(x, y)));
			}
		}
	}

	private void printAvailablePaths(Graphics2D g2) {
		List<Cell> cells = controller.getAvailablePathsAtActiveChessmen();
		if (cells != null) {
			for (Cell cell : cells) {
				if (controller.getChessmen(cell) == null) {
					drawCell(g2, cell, new Color(80, 80, 200));
				} else {
					drawCell(g2, cell, new Color(200, 80, 80));
				}
			}
		}
	}

	private void printDangerPaths(Graphics2D g2) {
		Cell cell = controller.getDangerCell();
		if (cell != null) {
			drawCell(g2, cell, new Color(200, 30, 30));
		}
	}

	private void printDangerChessmen(Graphics2D g2) {
		Chessmen chessmen = controller.getCellDanger();
		if (chessmen != null) {
			drawCell(g2, chessmen.getCell(), new Color(200, 30, 30));
		}
	}

	private void printActiveCell(Graphics2D g2) {
		Cell cell = controller.getCellActive();
		if (cell != null) {
			drawCell(g2, cell, new Color(30, 200, 30));
		}
	}

	private void printActiveChessmen(Graphics2D g2) {
		Chessmen chessmen = controller.getChessmenActive();
		if (chessmen != null) {
			drawCell(g2, chessmen.getCell(), new Color(30, 30, 200));
		}
	}

	private void printChessmens(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (Chessmen chessmen : controller.getChessmens().getChessmens()) {
			Image image = null;
			switch (chessmen.isWho()) {
			case Pawn:
				image = drawPawn(chessmen);
				break;
			case Rook:
				image = drawRook(chessmen);
				break;
			case Horse:
				image = drawHorse(chessmen);
				break;
			case Officer:
				image = drawOfficer(chessmen);
				break;
			case Queen:
				image = drawQueen(chessmen);
				break;
			case King:
				image = drawKing(chessmen);
				break;
			}

			g2.drawImage(image, FrameMain.getCoord(chessmen.getX()) * FrameMain.sizeCell,
					FrameMain.getCoord(chessmen.getY()) * FrameMain.sizeCell,
					FrameMain.sizeCell, FrameMain.sizeCell, this);
		}
	}

	private Image drawPawn(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/PB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/PW.png"));
	}

	private Image drawRook(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/RB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/RW.png"));
	}

	private Image drawHorse(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/HB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/HW.png"));
	}

	private Image drawOfficer(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/OB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/OW.png"));
	}

	private Image drawQueen(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/QB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/QW.png"));
	}

	private Image drawKing(Chessmen chessmen) {
		if (chessmen.getColor() == ColorChessmen.black) {
			return Toolkit.getDefaultToolkit().getImage(
					PanelChessView.class.getResource("chessmens/KB.png"));
		}
		return Toolkit.getDefaultToolkit().getImage(
				PanelChessView.class.getResource("chessmens/KW.png"));
	}
}