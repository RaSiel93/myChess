package Chessmens;

import java.awt.Color;

import Main.Cell;
import Main.TypeChessmen;

public class Queen extends Chessmen {
	public Queen(Cell cell, Color color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Queen;
	}

	@Override
	public boolean checkMove(Cell cell, boolean[][] chessboard, boolean enemy) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2)
				&& checkBlock(x1, y1, x2, y2, chessboard, enemy);
	}

	private boolean checkPath(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 != y2) {
			return true;
		}
		if (y1 == y2 && x1 != x2) {
			return true;
		}
		if ((x2 - x1) == (y2 - y1)) {
			return true;
		}
		if ((y2 - y1) == (x1 - x2)) {
			return true;
		}
		return false;
	}

	private boolean checkBlock(int x1, int y1, int x2, int y2,
			boolean[][] chessboard, boolean enemy) {
		chessboard = preprocessing(x2, y2, chessboard, enemy);
		boolean block = true;
		if ((x2 - x1) == (y2 - y1)) {
			int correct = 0;
			if (y1 < y2) {
				for (int pos = y1 + 1; pos < y2; pos++) {
					if (!chessboard[x1 + ++correct][pos]) {
						block = false;
					}
				}
			} else {
				for (int pos = y1 - 1; pos > y2; pos--) {
					if (!chessboard[x1 + --correct][pos]) {
						block = false;
					}
				}
			}
		} else if ((y2 - y1) == (x1 - x2)) {
			int correct = 0;
			if (x1 < x2) {
				for (int pos = x1 + 1; pos < x2; pos++) {
					if (!chessboard[pos][y1 + --correct]) {
						block = false;
					}
				}
			} else {
				for (int pos = x1 - 1; pos > x2; pos--) {
					if (!chessboard[pos][y1 + ++correct]) {
						block = false;
					}
				}
			}
		} else if (x1 == x2) {
			if (y1 < y2) {
				for (int pos = y1 + 1; pos < y2; pos++) {
					if (!chessboard[x2][pos]) {
						block = false;
					}
				}
			} else {
				for (int pos = y1 - 1; pos > y2; pos--) {
					if (!chessboard[x2][pos]) {
						block = false;
					}
				}
			}
		} else if (y1 == y2) {
			if (x1 < x2) {
				for (int pos = x1 + 1; pos < x2; pos++) {
					if (!chessboard[pos][y2]) {
						block = false;
					}
				}
			} else {
				for (int pos = x1 - 1; pos > x2; pos--) {
					if (!chessboard[pos][y2]) {
						block = false;
					}
				}
			}
		}
		return block;
	}
}
