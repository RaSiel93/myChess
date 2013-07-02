package myChess.model.chessmens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class Pawn extends Chessmen {
	public Pawn(Cell cell, ColorChessmen color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Pawn;
	}

	@Override
	public List<Cell> getAttackPath(Cell cell) {
		return new ArrayList<Cell>();
	}

	@Override
	public boolean checkMove(Cell cell, boolean[][] chessboard, boolean enemy) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2, chessboard, enemy);
	}

	private boolean checkPath(int x1, int y1, int x2, int y2,
			boolean[][] chessboard, boolean enemy) {
		boolean path = false;
		if (getColor() == ColorChessmen.black) {
			if (x1 == x2 && chessboard[x2][y2]) {
				if (y1 + 1 == y2) {
					path = true;
				}
				if (y1 + 2 == y2 && y1 == 1 && chessboard[x1][y1 + 1]) {
					path = true;
				}
			}
			if (y1 + 1 == y2 && (x1 - 1 == x2 || x1 + 1 == x2) && enemy) {
				path = true;
			}
		} else if (getColor() == ColorChessmen.white) {
			if (x1 == x2 && chessboard[x2][y2]) {
				if (y1 - 1 == y2 && chessboard[x2][y2]) {
					path = true;
				}
				if (y1 - 2 == y2 && y1 == 6 && chessboard[x1][y1 - 1]
						&& chessboard[x2][y2]) {
					path = true;
				}
			}
			if (y1 - 1 == y2 && (x1 - 1 == x2 || x1 + 1 == x2) && enemy) {
				path = true;
			}
		}
		return path;
	}
}
