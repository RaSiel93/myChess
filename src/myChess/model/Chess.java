package myChess.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import myChess.model.chessmens.*;
import myChess.types.Cell;

public class Chess {
	private List<Chessmen> chessmens;
	private Chessmen kingWhite;
	private Chessmen kingBlack;

	public Chess() {
		this.chessmens = new ArrayList<Chessmen>();
		loadChessmens();
	}

	public void reset() {
		this.chessmens.clear();
		loadChessmens();
	}

	private void loadChessmens() {
		for (int num = 0; num < 8; num++) {
			chessmens.add(new Pawn(new Cell(num, 6), Color.white));
			chessmens.add(new Pawn(new Cell(num, 1), Color.black));
		}
		//chessmens.add(new Pawn(new Cell(1, 0), Color.black));
		
		chessmens.add(new Rook(new Cell(0, 7), Color.white));
		chessmens.add(new Rook(new Cell(7, 7), Color.white));
		chessmens.add(new Rook(new Cell(0, 0), Color.black));
		chessmens.add(new Rook(new Cell(7, 0), Color.black));
		chessmens.add(new Horse(new Cell(1, 7), Color.white));
		chessmens.add(new Horse(new Cell(6, 7), Color.white));
		chessmens.add(new Horse(new Cell(1, 0), Color.black));
		chessmens.add(new Horse(new Cell(6, 0), Color.black));
		chessmens.add(new Officer(new Cell(2, 7), Color.white));
		chessmens.add(new Officer(new Cell(5, 7), Color.white));
		chessmens.add(new Officer(new Cell(2, 0), Color.black));
		chessmens.add(new Officer(new Cell(5, 0), Color.black));
		chessmens.add(new Queen(new Cell(3, 7), Color.white));
		chessmens.add(new Queen(new Cell(3, 0), Color.black));

		this.kingWhite = new King(new Cell(4, 7), Color.white);
		this.kingBlack = new King(new Cell(4, 0), Color.black);
		//this.kingBlack = new King(new Cell(0, 0), Color.black);
		chessmens.add(this.kingWhite);
		chessmens.add(this.kingBlack);
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

	public boolean[][] getChessStatus() {
		boolean status[][] = new boolean[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				status[x][y] = checkChessmen(new Cell(x, y));
			}
		}
		return status;
	}

	public boolean checkChessmen(Cell cell) {
		if (getChessmen(cell) == null) {
			return true;
		}
		return false;
	}

	public Chessmen getChessmenDanger(Color color) {
		return getChessmenMoveTo(getKing(color).getCell(), switchColor(color));
	}

	private Chessmen getChessmenMoveTo(Cell cell, Color color) {
		for (Chessmen chessmen : chessmens) {
			if (chessmen.getColor() == color) {
				if (chessmen.checkMove(cell, getChessStatus(), checkEnemy(cell, color))) {
					return chessmen;
				}
			}
		}
		return null;
	}

	public boolean checkKingPath(Color color) {
		boolean checkPath = false;

		Chessmen chessmen = getKing(color);
		List<Cell> cells = getLikelyKingPath(chessmen);

		for (Cell cell : cells) {
			if (validateCell(cell)) {
				if (chessmen.checkMove(cell, getChessStatus(),
						checkEnemy(cell, color))) {
					Cell cellOriginal = chessmen.getCell();
					chessmen.move(cell);
					if (getChessmenDanger(color) == null) {
						checkPath = true;
					}
					chessmen.reMove(cellOriginal);
				}
			}
		}

		return checkPath;
	}

	private Chessmen getKing(Color color) {
		Chessmen chessmen;
		if (color == Color.white) {
			chessmen = this.kingWhite;
		} else {
			chessmen = this.kingBlack;
		}
		return chessmen;
	}

	private List<Cell> getLikelyKingPath(Chessmen chessmen) {
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

	private boolean checkEnemy(Cell cell, Color color) {
		boolean enemy = false;
		Chessmen chessmenEnemy = getChessmen(cell);
		if (chessmenEnemy != null) {
			if (chessmenEnemy.getColor() != color) {
				enemy = true;
			}
		}
		return enemy;
	}

	private boolean validateCell(Cell cell) {
		return (cell.getX() >= 0 && cell.getX() <= 7 && cell.getY() >= 0 && cell
				.getY() <= 7);
	}

	public boolean checkKill(Chessmen chessmen) {
		boolean kill = false;
		Cell cell = chessmen.getCell();
		Chessmen chessmenKill = getChessmenMoveTo(cell, switchColor(chessmen.getColor()));
		if(chessmenKill != null){
			Cell cellOriginal = chessmenKill.getCell();
			chessmens.remove(chessmen);
			chessmenKill.move(cell);
			if(getChessmenDanger(chessmenKill.getColor()) == null){
				kill = true;
			}
			chessmens.add(chessmen);
			chessmenKill.reMove(cellOriginal);
		}
		return kill;
	}

	public boolean checkCover(Chessmen chessmen) {
		boolean cover = false;
		Color color = switchColor(chessmen.getColor());
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
				friend.reMove(cellOriginal);
			}
		}

		return cover;
	}

	private Color switchColor(Color color) {
		if (color == Color.white) {
			color = Color.black;
		} else {
			color = Color.white;
		}
		return color;
	}

	public boolean checkPad(Color color) {
		// TODO Auto-generated method stub
		return false;
	}
}