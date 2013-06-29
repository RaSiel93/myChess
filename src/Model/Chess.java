package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Chessmens.Chessmen;
import Chessmens.Horse;
import Chessmens.King;
import Chessmens.Rook;
import Chessmens.Officer;
import Chessmens.Pawn;
import Chessmens.Queen;
import Main.Cell;

public class Chess {
	private List<Chessmen> chessmens;
	private Chessmen kingWhite;
	private Chessmen kingBlack;

	public Chess() {
		chessmens = new ArrayList<Chessmen>();
		loadChessmens();
	}

	private void loadChessmens() {
		for (int num = 0; num < 8; num++) {
			chessmens.add(new Pawn(new Cell(num, 6), Color.white));
			chessmens.add(new Pawn(new Cell(num, 1), Color.black));
		}
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
		chessmens.add(this.kingWhite);
		chessmens.add(this.kingBlack);
	}

	public Chessmen getChessmen(int x, int y) {
		for (Chessmen chessmen : chessmens) {
			if (chessmen.getX() == x && chessmen.getY() == y) {
				return chessmen;
			}
		}
		return null;
	}

	public boolean checkChessmen(int x, int y) {
		if (getChessmen(x, y) == null) {
			return true;
		}
		return false;
	}

	public boolean checkFriendChessmen(int x, int y, Color color) {
		if (getChessmen(x, y).getColor() == color) {
			return true;
		}
		return false;
	}


	public void addChessmen(Chessmen chessmen) {
		chessmens.add(chessmen);
	}

	public void remove(Chessmen chessmen) {
		chessmens.remove(chessmen);
	}
	
	public List<Chessmen> getChessmens() {
		return chessmens;
	}

	public Cell checkShah(Color color) {
		Cell cell;
		if (color == Color.white) {
			cell = new Cell(this.kingWhite.getX(), this.kingWhite.getY());
		} else {
			cell = new Cell(this.kingBlack.getX(), this.kingBlack.getY());
		}

		for (Chessmen chessmen : chessmens) {
			if (chessmen.getColor() != color) {
				if (chessmen.checkMove(cell, getChessStatus(), true)) {
					return new Cell(chessmen.getX(), chessmen.getY());
				}
			}
		}
		return null;
	}

	public boolean[][] getChessStatus() {
		boolean status[][] = new boolean[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (getChessmen(x, y) != null) {
					status[x][y] = false;
				} else {
					status[x][y] = true;
				}
			}
		}
		return status;
	}
}