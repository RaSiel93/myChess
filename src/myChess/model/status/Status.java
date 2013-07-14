package myChess.model.status;

import java.io.Serializable;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;
import myChess.types.ColorChessmen;

public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean modeRead;
	private boolean game;
	private ColorChessmen whoWalks;

	private String commentGame;
	private String commentHistory;

	private Cell cellActive;
	private Chessmen chessmenActive;
	private Chessmen chessmenDanger;

	public Status() {
		this.game = false;
		this.modeRead = false;
		this.whoWalks = ColorChessmen.white;
		this.commentGame = "-";
		this.commentHistory = "Пустая история";
		this.cellActive = null;
		this.chessmenDanger = null;
		this.chessmenActive = null;
	}

	public void start() {
		this.game = true;
	}

	public void stop() {
		this.game = false;
	}

	public boolean isGame() {
		return this.game;
	}

	public boolean isModeRead() {
		return this.modeRead;
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

	public void move() {
		this.whoWalks = whoNoWalk();
		this.chessmenDanger = null;
		this.chessmenActive = null;
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

	public void setCommentHistory(String string) {
		this.commentHistory = string;
	}

	public String getCommentHistory() {
		return this.commentHistory;
	}

	public void switchModeRead() {
		this.modeRead = !modeRead;
		this.chessmenActive = null;
	}
}
