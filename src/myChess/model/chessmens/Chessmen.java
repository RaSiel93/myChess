package myChess.model.chessmens;

import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public abstract class Chessmen {
	protected Cell cell;
	protected ColorChessmen color;
	protected int countMove;
	
	public Chessmen(Cell cell, ColorChessmen color) {
		this.cell = cell;
		this.color = color;
		this.countMove = 0;
	}

	public int getCountMove(){
		return this.countMove;
	}
	
	public int getX() {
		return cell.getX();
	}

	public int getY() {
		return cell.getY();
	}

	public Cell getCell() {
		return this.cell;
	}
	
	public ColorChessmen getColor() {
		return color;
	}

	public void unmove(Cell cell){
		this.cell = cell;
		this.countMove--;
	}
	
	public void move(Cell cell){
		this.cell = cell;
		this.countMove++;
	}

	public abstract TypeChessmen isWho();

	abstract public List<Cell> getAttackPath(Cell cell);

	abstract public List<Cell> getPaths();
		
	abstract public boolean checkMove(Cell cell, ColorChessmen[][] checkboard);
	
	protected List<Cell> validateCells(List<Cell> cells) {
		List<Cell> validateCells = new ArrayList<Cell>();
		for (Cell cell : cells) {
			if (cell.getX() < 0 || cell.getY() < 0 || cell.getX() > 7
					|| cell.getY() > 7) {
				validateCells.add(cell);
			}
		}
		for (Cell cell : validateCells) {
			cells.remove(cell);
		}
		return cells;
	}

	public List<Cell> getAvailablePaths(ColorChessmen[][] chessboard) {
		List<Cell> availablePaths = getPaths();
		List<Cell> badCells = new ArrayList<Cell>();
		for (Cell cell : availablePaths) {
			if (!checkMove(cell, chessboard)) {
				badCells.add(cell);
			}
		}
		for (Cell cell : badCells) {
			availablePaths.remove(cell);
		}
		return availablePaths;
	}
}
