package myChess.model.chessmens;

import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class King extends Chessmen {
	public King(Cell cell, ColorChessmen color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.King;
	}

	@Override
	public List<Cell> getAttackPath(Cell cell) {
		return null;
	}

	@Override
	public boolean checkMove(Cell cell, ColorChessmen[][] chessboard) {
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		return checkPath(x1, y1, x2, y2) && !checkBlock(x2, y2, chessboard);
	}

	boolean checkPath(int x1, int y1, int x2, int y2) {
		if ((x1 + 1 == x2 || x1 - 1 == x2 || x1 == x2)
				&& (y1 + 1 == y2 || y1 - 1 == y2 || y1 == y2)) {
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

		paths.add(new Cell(x1, y1 + 1));
		paths.add(new Cell(x1, y1 - 1));
		paths.add(new Cell(x1 + 1, y1));
		paths.add(new Cell(x1 - 1, y1));
		paths.add(new Cell(x1 + 1, y1 + 1));
		paths.add(new Cell(x1 + 1, y1 - 1));
		paths.add(new Cell(x1 - 1, y1 + 1));
		paths.add(new Cell(x1 - 1, y1 - 1));

		return validateCells(paths);
	}
}
