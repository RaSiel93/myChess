package myChess.types;

import java.io.Serializable;

public class Cell  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
