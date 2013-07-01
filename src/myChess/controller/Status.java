package myChess.controller;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import myChess.model.chessmens.Chessmen;
import myChess.controller.history.History;
import myChess.types.Cell;

public class Status {
	private Cell cellActive;
	private Chessmen chessmenDanger;
	private Chessmen chessmenActive;

	private int positionHistory;
	private List<History> listHistory;

	private boolean game;
	private Color whoWalks;
	private String comment;

	public Status() {
		this.whoWalks = null;
		this.cellActive = null;
		this.chessmenDanger = null;
		this.chessmenActive = null;
		this.listHistory = new ArrayList<History>();
		this.positionHistory = -1;
		this.game = false;
		this.comment = "-";
	}


	public void reset() {
		this.whoWalks = null;
		this.cellActive = null;
		this.chessmenDanger = null;
		this.chessmenActive = null;
		this.listHistory.clear();
		this.positionHistory = -1;
		this.game = false;
		this.comment = "-";
	}
	
	public void start() {
		this.game = true;
		update();
		setComment("Начало игры");
	}

	public void stop() {
		this.game = false;
		//setComment("Конец игры");
	}

	public boolean isGame() {
		return this.game;
	}

	public Color whoWalk() {
		return this.whoWalks;
	}

	public Color whoNoWalk() {
		if (this.whoWalks == Color.white) {
			return Color.black;
		}
		return Color.white;
	}

	public void update() {
		if (this.positionHistory % 2 == 0) {
			this.whoWalks = Color.black;
		} else {
			this.whoWalks = Color.white;
		}
	}
	
	public void updateComment(){
		History history = getCurrentHistory();
		if (history != null) {
			setComment(history.getComment());
		} else if (checkBeginPositionHistory()) {
			setComment("Начало игры");
		}
	}

	public Cell getCellActive() {
		return cellActive;
	}

	public void setCellActive(Cell cell) {
		cellActive = cell;
	}

	public Chessmen getCellDanger() {
		return chessmenDanger;
	}

	public void setChessmenDanger(Chessmen chessmen) {
		chessmenDanger = chessmen;
	}

	public Chessmen getChessmenActive() {
		return chessmenActive;
	}

	public void setChessmenActive(Chessmen chessmen) {
		this.chessmenActive = chessmen;
	}

	public void addHistory(History history) {
		removeOldHistory();
		this.listHistory.add(history);
		this.positionHistory++;
	}

	void removeOldHistory() {
		while (this.positionHistory != listHistory.size() - 1) {
			this.listHistory.remove(listHistory.size() - 1);
		}
	}

	public History getCurrentHistory() {
		if (checkBeginPositionHistory() || checkEndPositionHistory()) {
			return null;
		}
		return this.listHistory.get(positionHistory);
	}

	public boolean checkBeginPositionHistory() {
		return this.positionHistory == -1;
	}

	public boolean checkEndPositionHistory() {
		return this.positionHistory == listHistory.size();
	}

	public void nextHistory() {
		this.positionHistory++;
	}

	public void prevHistory() {
		this.positionHistory--;
	}

	public void setComment(String string) {
		this.comment = string;
	}

	public String getComment() {
		return this.comment;
	}

	public String getHistory() {
		History history = getCurrentHistory();
		if (history != null) {
			return history.getHistory();
		}
		return "Пустая история";
	}

	public boolean undoHistory() {
		boolean undo = false;
		History history = getCurrentHistory();
		if (history != null) {
			history.undo();
			undo = true;
			prevHistory();
			setChessmenActive(null);
			updateComment();
		}
		return undo;
	}

	public boolean redoHistory() {
		boolean redo = false;
		nextHistory();
		History history = getCurrentHistory();
		if (history != null) {
			history.redo();
			redo = true;
			setChessmenActive(null);
			updateComment();
		} else {
			prevHistory();
		}
		return redo;
	}
}
