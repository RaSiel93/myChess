package Chessmens;

import java.awt.Color;

import Main.Cell;
import Main.TypeChessmen;

public class Rook extends Chessmen {
	public Rook(Cell cell, Color color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Rook;
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

	boolean checkPath(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 != y2) {
			return true;
		}
		if (y1 == y2 && x1 != x2) {
			return true;
		}
		return false;
	}

	private boolean checkBlock(int x1, int y1, int x2, int y2,
			boolean[][] chessboard, boolean enemy) {
		chessboard = preprocessing(x2, y2, chessboard, enemy);
		boolean block = true;
		if (x1 == x2) {
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
