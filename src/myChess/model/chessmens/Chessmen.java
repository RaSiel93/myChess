package myChess.model.chessmens;

import java.awt.Color;
import java.util.List;

import myChess.types.Cell;
import myChess.types.TypeChessmen;

public abstract class Chessmen {
	protected Cell cell;
	protected Color color;
	protected int countMove;
	
	public Chessmen(Cell cell, Color color) {
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
	
	public Color getColor() {
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
	
	//abstract public List<Cell> getPath();
	
	abstract public boolean checkMove(Cell cell, boolean[][] checkboard, boolean enemy);
	
	protected boolean[][] preprocessing(int x2, int y2, boolean[][] chessboard,
			boolean enemy) {
		if (enemy == true) {
			chessboard[x2][y2] = enemy;
		}
		return chessboard;
	}
}
