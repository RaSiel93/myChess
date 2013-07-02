package myChess.model.chessmens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class Officer extends Chessmen {
	public Officer(Cell cell, ColorChessmen color) {
		super(cell, color);
	}

	@Override
	public TypeChessmen isWho() {
		return TypeChessmen.Officer;
	}

	@Override
	public List<Cell> getAttackPath(Cell cell) {
		List<Cell> cells = new ArrayList<Cell>();
		int x1 = this.getX();
		int y1 = this.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		
		if ((x2 - x1) == (y2 - y1)) {
			if (y1 < y2) {
				for(int pos = 1; pos < y2 - y1; pos++){
					if(x1 + pos < 7){
						cells.add(new Cell(x1 + pos, y1 + pos));
					}
				}
			} else {
				for(int pos = 1; pos < y1 - y2; pos++){
					if(x1 - pos > 0){
						cells.add(new Cell(x1 - pos, y1 - pos));
					}
				}
			}
		} else if ((y2 - y1) == (x1 - x2)) {
			if (x1 < x2) {
				for(int pos = 1; pos < x2 - x1; pos++){
					if(y1 - pos < 7){
						cells.add(new Cell(x1 + pos, y1 - pos));
					}
				}
			} else {
				for(int pos = 1; pos < x1 - x2; pos++){
					if(y1 + pos > 0){
						cells.add(new Cell(x1 - pos, y1 + pos));
					}
				}
			}
		}
		return cells;
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
		}
		return block;
	}

}
