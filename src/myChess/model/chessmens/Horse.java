package myChess.model.chessmens;

import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class Horse extends Chessmen {
	public Horse(Cell cell, ColorChessmen color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Horse;
	}

	@Override
	public List<Cell> getAttackPath(Cell cell) {
		return new ArrayList<Cell>();
	}

	@Override
	public boolean checkMove(Cell cell, ColorChessmen[][] chessboard) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2) && !checkBlock(x2, y2, chessboard);
	}

	private boolean checkPath(int x1, int y1, int x2, int y2) {
		if (x1 + 2 == x2 && ((y1 + 1 == y2) || (y1 - 1 == y2))) {
			return true;
		}
		if (x1 - 2 == x2 && ((y1 + 1 == y2) || (y1 - 1 == y2))) {
			return true;
		}
		if (y1 + 2 == y2 && ((x1 + 1 == x2) || (x1 - 1 == x2))) {
			return true;
		}
		if (y1 - 2 == y2 && ((x1 + 1 == x2) || (x1 - 1 == x2))) {
			return true;
		}
		return false;
	}

	private boolean checkBlock(int x2, int y2, ColorChessmen[][] chessboard) {
		return chessboard[x2][y2] == getColor();
	}

	@Override
	public List<Cell> getPaths() {
		List<Cell> paths = new ArrayList<Cell>();
		int x1 = this.getX();
		int y1 = this.getY();

		paths.add(new Cell(x1 + 2, y1 + 1));
		paths.add(new Cell(x1 + 2, y1 - 1));
		paths.add(new Cell(x1 - 2, y1 + 1));
		paths.add(new Cell(x1 - 2, y1 - 1));
		paths.add(new Cell(x1 + 1, y1 + 2));
		paths.add(new Cell(x1 + 1, y1 - 2));
		paths.add(new Cell(x1 - 1, y1 + 2));
		paths.add(new Cell(x1 - 1, y1 - 2));

		return validateCells(paths);
	}
}
