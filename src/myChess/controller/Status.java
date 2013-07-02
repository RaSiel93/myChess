package myChess.controller;

import myChess.model.chessmens.Chessmen;
import myChess.controller.history.HistoryType;
import myChess.types.Cell;
import myChess.types.ColorChessmen;

public class Status {
	private boolean game;
	private ColorChessmen whoWalks;

	private String commentGame;
	private String commentHistory;

	private History history;

	private Cell cellActive;
	private Chessmen chessmenActive;
	private Chessmen chessmenDanger;

	public Status() {
		reset();
	}

	public void reset() {
		this.game = false;
		this.whoWalks = ColorChessmen.white;
		this.commentGame = "-";
		this.commentHistory = "Пустая история";
		this.history = new History();
		this.cellActive = null;
		this.chessmenDanger = null;
		this.chessmenActive = null;
	}

	public void start() {
		this.game = true;
		setCommentGame("Начало игры");
	}

	public void stop() {
		this.game = false;
		//setCommentGame("Конец игры");
	}

	public boolean isGame() {
		return this.game;
	}

	public ColorChessmen whoWalk() {
		return this.whoWalks;
	}

	private ColorChessmen whoNoWalk() {
		if (this.whoWalks == ColorChessmen.white) {
			return ColorChessmen.black;
		}
		return ColorChessmen.white;
	}

	public void switchWalk() {
		this.whoWalks = whoNoWalk();
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

	public String getWhoWalk() {
		String whoWalk;
		if (whoWalk() == ColorChessmen.white) {
			whoWalk = "Ходят белые";
		} else {
			whoWalk = "Ходят черные";
		}
		return whoWalk;
	}

	public void setCommentGame(String string) {
		this.commentGame = string;
	}

	public String getCommentGame() {
		return this.commentGame;
	}

	public void updateCommentGame() {
		setCommentGame(this.history.getCommentGame());
	}

	public void setCommentHistory(String string) {
		this.commentHistory = string;
	}

	public String getCommentHistory() {
		return this.commentHistory;
	}

	public void updateCommentHistory() {
		setCommentHistory(this.history.getCommentHistory());
	}

	public void addHistory(HistoryType history) {
		this.history.addHistory(history);
	}

	void update() {
		setChessmenActive(null);
		setChessmenDanger(null);
		updateCommentGame();
		updateCommentHistory();
	}
	
	public boolean undoHistory() {
		boolean undo = false;
		if (this.history.undo()) {
			update();
			undo = true;
		}
		return undo;
	}

	public boolean redoHistory() {
		boolean redo = false;
		if (this.history.redo()) {
			update();
			redo = true;
		}
		return redo;
	}

	public void move(HistoryType history) {
		history.redo();
		switchWalk();
		addHistory(history);
		update();
		setChessmenActive(null);
		setChessmenDanger(null);
	}
}
