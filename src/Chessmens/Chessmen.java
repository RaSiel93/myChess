package Chessmens;

import java.awt.Color;

import Main.Cell;
import Main.TypeChessmen;

public abstract class Chessmen {
	protected Cell cell;
	protected Color color;

	public Chessmen(Cell cell, Color color) {
		this.cell = cell;
		this.color = color;
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
	
	public void move(Cell cell){
		this.cell = cell;
	}

	public abstract TypeChessmen isWho();

	abstract public boolean checkMove(Cell cell, boolean[][] checkboard, boolean enemy);

	protected boolean[][] preprocessing(int x2, int y2, boolean[][] chessboard,
			boolean enemy) {
		if (enemy == true) {
			chessboard[x2][y2] = enemy;
		}
		return chessboard;
	}
}
