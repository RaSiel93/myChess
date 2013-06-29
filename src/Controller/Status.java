package Controller;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import Chessmens.Chessmen;
import Main.Cell;

public class Status {
	private Color whoWalks;
	private Cell cellActive;
	private Cell cellDanger;
	private Chessmen chessmenActive;
	private int positionHistory;
	private List<History> history;
	private boolean game;
	
	private String comment;
	
	public Status() {
		this.whoWalks = null;
		this.cellActive = null;
		this.cellDanger = null;
		this.chessmenActive = null;
		this.history = new ArrayList<History>();
		this.positionHistory = -1;
		this.game = false;
		this.comment = "-";
	}

	public void startWith(Color color) {
		this.whoWalks = color;
		this.game = true;
		setComment("Начало игры");
	}

	public void stop(){
		this.game = false;
		setComment("Конец игры");
	}
	
	public boolean isGame(){
		return this.game;
	}
	
	public Color whoWalk() {
		return this.whoWalks;
	}

	public void nextMove() {
		if (whoWalk() == Color.white) {
			this.whoWalks = Color.black;
		} else {
			this.whoWalks = Color.white;
		}
		chessmenDeactive();
	}

	public Cell getCellActive() {
		return cellActive;
	}

	public void setCellActive(Cell cell) {
		cellActive = cell;
	}

	public Cell getCellDanger() {
		return cellDanger;
	}
	
	public void setCellDanger(Cell cell) {
		cellDanger = cell;
	}
	
	public Chessmen getChessmenActive() {
		return chessmenActive;
	}

	public void setChessmenActive(Chessmen chessmen) {
		this.chessmenActive = chessmen;
	}

	public void chessmenDeactive() {
		chessmenActive = null;
	}

	public void addHistory(int x, int y, int x2, int y2, Chessmen chessmen) {
		if (history.size() != 0) {
			while (this.positionHistory != history.size() - 1) {
				this.history.remove(history.size() - 1);
			}
		}
		this.history.add(new History(getChessmenActive(), new Cell(x, y),
				new Cell(x2, y2), chessmen));
		this.positionHistory++;
	}

	public History getHistory() {
		if (this.positionHistory != -1
				&& this.positionHistory != this.history.size()) {
			return this.history.get(positionHistory);
		}
		return null;
	}

	public void nextHistory() {
		this.positionHistory++;
	}

	public void prevHistory() {
		this.positionHistory--;
	}

	public void deleteLastHistory() {
		this.history.remove(this.history.size() - 1);
	}

	public void setComment(String string) {
		this.comment = string;
	}
	
	public String getComment(){
		return this.comment;
	}

	public void setCommentShah() {
		if(whoWalk() == Color.white){
			setComment("Шах белым");
		} else {
			setComment("Шах черным");
		}
	}
}
