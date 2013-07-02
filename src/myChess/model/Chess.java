package myChess.model;

import java.util.ArrayList;
import java.util.List;

import myChess.model.chessmens.*;
import myChess.types.Cell;
import myChess.types.ColorChessmen;

public class Chess {
	private List<Chessmen> chessmens;
	private Chessmen kingWhite;
	private Chessmen kingBlack;

	public Chess() {
		this.chessmens = new ArrayList<Chessmen>();
	}

	public void reset() {
		this.chessmens.clear();
	}

	public void loadChessmens() {
		for (int num = 0; num < 8; num++) {
			chessmens.add(new Pawn(new Cell(num, 6), ColorChessmen.white));
			chessmens.add(new Pawn(new Cell(num, 1), ColorChessmen.black));
		}

		chessmens.add(new Rook(new Cell(0, 7), ColorChessmen.white));
		chessmens.add(new Rook(new Cell(7, 7), ColorChessmen.white));
		chessmens.add(new Rook(new Cell(0, 0), ColorChessmen.black));
		chessmens.add(new Rook(new Cell(7, 0), ColorChessmen.black));
		chessmens.add(new Horse(new Cell(1, 7), ColorChessmen.white));
		chessmens.add(new Horse(new Cell(6, 7), ColorChessmen.white));
		chessmens.add(new Horse(new Cell(1, 0), ColorChessmen.black));
		chessmens.add(new Horse(new Cell(6, 0), ColorChessmen.black));
		chessmens.add(new Officer(new Cell(2, 7), ColorChessmen.white));
		chessmens.add(new Officer(new Cell(5, 7), ColorChessmen.white));
		chessmens.add(new Officer(new Cell(2, 0), ColorChessmen.black));
		chessmens.add(new Officer(new Cell(5, 0), ColorChessmen.black));
		chessmens.add(new Queen(new Cell(3, 7), ColorChessmen.white));
		chessmens.add(new Queen(new Cell(3, 0), ColorChessmen.black));

		chessmens.add(this.kingWhite = new King(new Cell(4, 7),
				ColorChessmen.white));
		chessmens.add(this.kingBlack = new King(new Cell(4, 0),
				ColorChessmen.black));
	}

	public Chessmen getChessmen(Cell cell) {
		for (Chessmen chessmen : chessmens) {
			if (chessmen.getX() == cell.getX()
					&& chessmen.getY() == cell.getY()) {
				return chessmen;
			}
		}
		return null;
	}

	public List<Chessmen> getChessmens() {
		return chessmens;
	}

	public void addChessmen(Chessmen chessmen) {
		chessmens.add(chessmen);
	}

	public void removeChessmen(Chessmen chessmen) {
		chessmens.remove(chessmen);
	}

	public ColorChessmen[][] getChessStatus() {
		ColorChessmen status[][] = new ColorChessmen[8][8];
		for (Chessmen chessmen : getChessmens()) {
			status[chessmen.getX()][chessmen.getY()] = chessmen.getColor();
		}
		return status;
	}

	public boolean checkChessmen(Cell cell) {// �������� �� ��������
		return getChessmen(cell) == null;
	}

	public Chessmen getChessmenDanger(ColorChessmen color) {
		return getChessmenMoveTo(getKing(color).getCell(), switchColor(color));
	}

	private Chessmen getChessmenMoveTo(Cell cell, ColorChessmen color) {
		for (Chessmen chessmen : chessmens) {
			if (chessmen.getColor() == color) {
				if (chessmen.checkMove(cell, getChessStatus())) {
					return chessmen;
				}
			}
		}
		return null;
	}

	public boolean checkKingPath(ColorChessmen color) {
		boolean checkPath = false;

		Chessmen king = getKing(color);
		List<Cell> cells = getLikelyKingPath(king);

		for (Cell cell : cells) {
			if (validateCell(cell)) {
				if (king.checkMove(cell, getChessStatus())) {
					Cell cellOriginal = king.getCell();
					king.move(cell);
					if (getChessmenDanger(color) == null) {
						checkPath = true;
					}
					king.unmove(cellOriginal);
				}
			}
		}

		return checkPath;
	}

	public Chessmen getKing(ColorChessmen color) {
		Chessmen chessmen;
		if (color == ColorChessmen.white) {
			chessmen = this.kingWhite;
		} else {
			chessmen = this.kingBlack;
		}
		return chessmen;
	}

	private List<Cell> getLikelyKingPath(Chessmen chessmen) {// ������� �����
		Cell cellKing = chessmen.getCell();
		List<Cell> cells = new ArrayList<Cell>();

		cells.add(new Cell(cellKing.getX() + 1, cellKing.getY()));
		cells.add(new Cell(cellKing.getX(), cellKing.getY() + 1));
		cells.add(new Cell(cellKing.getX() + 1, cellKing.getY() + 1));
		cells.add(new Cell(cellKing.getX() - 1, cellKing.getY()));
		cells.add(new Cell(cellKing.getX(), cellKing.getY() - 1));
		cells.add(new Cell(cellKing.getX() - 1, cellKing.getY() - 1));
		cells.add(new Cell(cellKing.getX() + 1, cellKing.getY() - 1));
		cells.add(new Cell(cellKing.getX() - 1, cellKing.getY() + 1));
		return cells;
	}

//	private boolean checkEnemy(Cell cell, ColorChessmen color) {
//		boolean enemy = false;
//		Chessmen chessmenEnemy = getChessmen(cell);
//		if (chessmenEnemy != null) {
//			if (chessmenEnemy.getColor() != color) {
//				enemy = true;
//			}
//		}
//		return enemy;
//	}

	private boolean validateCell(Cell cell) {// ������� �����
		return (cell.getX() >= 0 && cell.getX() <= 7 && cell.getY() >= 0 && cell
				.getY() <= 7);
	}

	public boolean checkKill(Chessmen chessmen) {
		boolean kill = false;
		Cell cell = chessmen.getCell();
		Chessmen chessmenKill = getChessmenMoveTo(cell,
				switchColor(chessmen.getColor()));
		if (chessmenKill != null) {
			Cell cellOriginal = chessmenKill.getCell();
			chessmens.remove(chessmen);
			chessmenKill.move(cell);
			if (getChessmenDanger(chessmenKill.getColor()) == null) {
				kill = true;
			}
			chessmens.add(chessmen);
			chessmenKill.unmove(cellOriginal);
		}
		return kill;
	}

	public boolean checkCover(Chessmen chessmen) {
		boolean cover = false;
		ColorChessmen color = switchColor(chessmen.getColor());
		Chessmen king = getKing(color);

		List<Cell> cells = chessmen.getAttackPath(king.getCell());

		for (Cell cell : cells) {
			Chessmen friend = getChessmenMoveTo(cell, color);
			if (friend != null) {
				Cell cellOriginal = friend.getCell();
				friend.move(cell);
				if (getChessmenDanger(color) == null) {
					cover = true;
				}
				friend.unmove(cellOriginal);
			}
		}

		return cover;
	}

	private ColorChessmen switchColor(ColorChessmen color) {
		if (color == ColorChessmen.white) {
			color = ColorChessmen.black;
		} else {
			color = ColorChessmen.white;
		}
		return color;
	}

	public boolean checkPad(ColorChessmen color) {
		/*Chessmen chessmen = getChessmen(new Cell(1,0));
		List<Cell> cells1 = chessmen.getPaths();
		List<Cell> cells2 = chessmen.getAvailablePaths(getChessStatus());*/
		return false;
	}
}