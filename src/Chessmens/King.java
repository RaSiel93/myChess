package Chessmens;

import java.awt.Color;

import Main.Cell;
import Main.TypeChessmen;

public class King extends Chessmen {
	public King(Cell cell, Color color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.King;
	}

	@Override
	public boolean checkMove(Cell cell, boolean[][] chessboard, boolean enemy) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2)
				&& checkBlock(x2, y2, chessboard, enemy);
	}

	boolean checkPath(int x1, int y1, int x2, int y2) {
		if ((x1 + 1 == x2 || x1 - 1 == x2 || x1 == x2)
				&& (y1 + 1 == y2 || y1 - 1 == y2 || y1 == y2)) {
			return true;
		}
		return false;
	}

	private boolean checkBlock(int x2, int y2, boolean[][] chessboard,
			boolean enemy) {
		chessboard = preprocessing(x2, y2, chessboard, enemy);
		return chessboard[x2][y2];
	}
}
