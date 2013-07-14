package myChess.model.chessmens;

import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class Rook extends Chessmen {
	private static final long serialVersionUID = 1L;
	public Rook(Cell cell, ColorChessmen color, int countMove) {
		super(cell, color, countMove);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Rook;
	}

	@Override
	public List<Cell> getAttackPath(Cell cell) {
		List<Cell> cells = new ArrayList<Cell>();
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();

		if (cell.getX() == getX() && cell.getY() != getY()) {
			if (y1 < y2) {
				for (int pos = 1; pos < y2 - y1; pos++) {
					cells.add(new Cell(x1, y1 + pos));
				}
			} else {
				for (int pos = 1; pos < y1 - y2; pos++) {
					cells.add(new Cell(x1, y1 - pos));
				}
			}
		} else if (cell.getY() == getY() && cell.getX() != getX()) {
			if (x1 < x2) {
				for (int pos = 1; pos < x2 - x1; pos++) {
					cells.add(new Cell(x1 + pos, y1));
				}
			} else {
				for (int pos = 1; pos < x1 - x2; pos++) {
					cells.add(new Cell(x1 - pos, y1));
				}
			}
		}
		return cells;
	}

	@Override
	public boolean checkMove(Cell cell, ColorChessmen[][] chessboard) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2)
				&& !checkBlock(x1, y1, x2, y2, chessboard);
	}

	private boolean checkPath(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 != y2) {
			return true;
		} else if (y1 == y2 && x1 != x2) {
			return true;
		}
		return false;
	}

	private boolean checkBlock(int x1, int y1, int x2, int y2,
			ColorChessmen[][] chessboard) {
		boolean block = false;
		if (chessboard[x2][y2] == getColor()) {
			block = true;
		}
		ColorChessmen color = chessboard[x2][y2];
		chessboard[x2][y2] = null;
				
		if (x1 == x2) {
			if (y1 < y2) {
				for (int pos = y1 + 1; pos <= y2; pos++) {
					if (chessboard[x2][pos] != null) {
						block = true;
					}
				}
			} else {
				for (int pos = y1 - 1; pos >= y2; pos--) {
					if (chessboard[x2][pos] != null) {
						block = true;
					}
				}
			}
		} else if (y1 == y2) {
			if (x1 < x2) {
				for (int pos = x1 + 1; pos <= x2; pos++) {
					if (chessboard[pos][y2] != null) {
						block = true;
					}
				}
			} else {
				for (int pos = x1 - 1; pos >= x2; pos--) {
					if (chessboard[pos][y2] != null) {
						block = true;
					}
				}
			}
		}
		chessboard[x2][y2] = color;
		return block;
	}

	@Override
	public List<Cell> getPaths() {
		List<Cell> paths = new ArrayList<Cell>();
		int x1 = this.getX();
		int y1 = this.getY();

		for (int pos = 0; pos <= 7; pos++) {
			paths.add(new Cell(x1, pos));
			paths.add(new Cell(pos, y1));
		}

		return validateCells(paths);
	}
}
