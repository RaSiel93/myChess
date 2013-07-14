package myChess.model;

import java.io.Serializable;
import java.util.List;

import myChess.model.chessmens.*;
import myChess.model.history.History;
import myChess.model.history.HistoryCastling;
import myChess.model.history.HistoryMoveUponEmpty;
import myChess.model.history.HistoryMoveUponEnemy;
import myChess.model.history.HistoryPawnEdge;
import myChess.model.history.HistoryPawnEdgeUponEnemy;
import myChess.model.history.HistoryType;
import myChess.model.status.Status;
import myChess.types.Cell;
import myChess.types.ColorChessmen;
import myChess.types.TypeChessmen;

public class Chess implements Serializable {
	private static final long serialVersionUID = 4868976453407800601L;

	private Chessmens chessmens;
	private Status status;
	private History history;

	public Chess() {
		load(new Chessmens(Chessmens.getStandartChessmens()), new Status(),
				new History());
	}

	public void load(Chessmens chessmens, Status status, History history) {
		this.chessmens = chessmens;
		this.status = status;
		this.history = history;
	}

	public void start() {
		status.start();
	}

	public void stop() {
		status.stop();
	}

	public boolean isGame() {
		return status.isGame();
	}

	public Chessmen getChessmen(Cell cell) {
		return this.chessmens.getChessmen(cell);
	}

	public Chessmens getChessmens() {
		return this.chessmens;
	}

	public void addChessmen(Chessmen chessmen) {
		chessmens.addChessmen(chessmen);
	}

	public void removeChessmen(Chessmen chessmen) {
		chessmens.removeChessmen(chessmen);
	}

	public boolean isModeRead() {
		return status.isModeRead();
	}

	public Chessmen getChessmenActive() {
		return status.getChessmenActive();
	}

	public Cell getCellActive() {
		return status.getCellActive();
	}

	public void setCommentGame(String string) {
		status.setCommentGame(string);
	}

	public ColorChessmen whoWalk() {
		return status.whoWalk();
	}

	public void setChessmenDanger(Chessmen chessmenDanger) {
		status.setChessmenDanger(chessmenDanger);
	}

	public void setCellActive(Cell cell) {
		status.setCellActive(cell);
	}

	public Chessmen getCellDanger() {
		return status.getCellDanger();
	}

	public void setChessmenActive(Chessmen chessmen) {
		status.setChessmenActive(chessmen);
	}

	public Status getStatus() {
		return this.status;
	}

	public void switchWalk() {
		this.status.move();
	}

	public void switchModeRead() {
		this.status.switchModeRead();
	}

	public void addHistory(HistoryType history) {
		this.history.addHistory(history);
	}

	public void undoHistory() {
		if (this.history.undo()) {
			this.status.setCommentHistory(this.history.getCommentHistory());
			this.status.setCommentGame(this.history.getCommentGame());
		}
	}

	public void redoHistory() {
		if (this.history.redo()) {
			this.status.setCommentHistory(this.history.getCommentHistory());
			this.status.setCommentGame(this.history.getCommentGame());
		}
	}

	public void move(HistoryType history) {
		history.execute();
		addHistory(history);
		this.status.move();
		this.status.setCommentHistory(this.history.getCommentHistory());
		this.status.setCommentGame(this.history.getCommentGame());
	}

	public History getHistory() {
		return this.history;
	}

	public void action() {
		Chessmen chessmen = getChessmenActive();
		Cell cell = getCellActive();

		if (checkFriendChessmen(cell)) {
			updateChessmenActive();
			setCommentGame("Ходите");
		} else if (chessmen != null) {
			if (checkMove(chessmen, cell) && !checkAfterShah(chessmen, cell)) {
				move(chessmen, cell);
			} else {
				setCommentGame("Недопустимый ход");
			}
		} else {
			setCommentGame("Выберите фигуру");
		}
	}

	public void move(Chessmen chessmen, Cell cell) {
		HistoryType history = null;
		if (checkCastling(chessmen, cell)) {
			history = new HistoryCastling(chessmen, cell,
					getRookInCastling(cell), getCellRookCastling(cell));
		} else if (checkPawnEdge(chessmen, cell)) {
			Chessmen enemy = chessmens.getChessmen(cell);
			if (enemy != null) {
				history = new HistoryPawnEdgeUponEnemy(this, chessmen, enemy,
						cell);
			} else {
				history = new HistoryPawnEdge(this, chessmen, cell);
			}
		} else {
			Chessmen enemy = chessmens.getChessmen(cell);
			if (enemy != null) {
				history = new HistoryMoveUponEnemy(this, chessmen, enemy, cell);
			} else {
				history = new HistoryMoveUponEmpty(chessmen, cell);
			}
		}
		move(history);
		// pushListeners(history);
	}

	// private void pushListeners(HistoryType history) {
	//
	// }

	public boolean checkGameOver() {
		ColorChessmen colorChessmen = whoWalk();
		boolean gameOver = false;
		if (checkShah(colorChessmen)) {
			if (checkMat(colorChessmen)) {
				gameOver = true;
			}
		} else if (checkPad(colorChessmen)) {
			gameOver = true;
		}
		return gameOver;
	}

	private boolean checkPad(ColorChessmen color) {
		boolean checkpad = false;
		if (chessmens.checkPad(color)) {
			status.setCommentGame("Пад");
			checkpad = true;
		}
		return checkpad;
	}

	private boolean checkMat(ColorChessmen color) {
		boolean checkmat = false;
		if (!chessmens.checkKingPath(color)
				&& !chessmens.checkKiller(chessmens.getChessmenDanger(color))
				&& !chessmens.checkCover(chessmens.getChessmenDanger(color))) {
			status.setCommentGame("Шах и Мат");
			checkmat = true;
		}
		return checkmat;
	}

	private boolean checkShah(ColorChessmen color) {
		boolean shah = false;
		Chessmen chessmenDanger = chessmens.getChessmenDanger(color);
		if (chessmenDanger != null) {
			status.setChessmenDanger(chessmenDanger);
			status.setCommentGame("Шах");
			shah = true;
		}
		return shah;
	}

	private boolean checkAfterShah(Chessmen chessmen, Cell cell) {
		boolean shah = false;
		if (checkCastling(chessmen, cell)) {
			if (checkShah(status.whoWalk())) {
				shah = true;
			}
		}
		Cell originalCell = chessmen.getCell();
		Chessmen enemy = chessmens.getChessmen(cell);
		chessmens.removeChessmen(enemy);
		chessmen.move(cell);
		shah = checkShah(status.whoWalk());
		chessmen.unmove(originalCell);
		if (enemy != null) {
			chessmens.addChessmen(enemy);
		}
		if (shah) {
			status.setCommentGame("Недопустимый ход");
		}
		return shah;
	}

	public void updateChessmenActive() {
		setChessmenActive(chessmens.getChessmen(getCellActive()));
	}

	public boolean checkFriendChessmen(Cell cell) {
		boolean friend = false;
		Chessmen chessmen = chessmens.getChessmen(status.getCellActive());
		if (chessmen != null) {
			if (chessmen.getColor() == status.whoWalk()) {
				friend = true;
			}
		}
		return friend;
	}

	public boolean checkEnemyChessmen(Cell cell) {
		boolean enemy = false;
		Chessmen chessmen = chessmens.getChessmen(status.getCellActive());
		if (chessmen != null) {
			if (chessmen.getColor() != status.whoWalk()) {
				enemy = true;
			}
		}
		return enemy;
	}

	public boolean checkMove(Chessmen chessmen, Cell cell) {
		boolean move = false;
		if (checkCastling(chessmen, cell)) {
			move = true;
		} else if (chessmen.checkMove(cell, chessmens.getChessboardStatus())) {
			move = true;
		}
		return move;
	}

	private boolean checkPawnEdge(Chessmen chessmen, Cell cell) {
		return (chessmen.isWho() == TypeChessmen.Pawn && (cell.getY() == 7 || cell
				.getY() == 0));
	}

	private boolean checkCastling(Chessmen chessmen, Cell cell) {
		boolean castling = false;
		if (chessmen.isWho() == TypeChessmen.King
				&& chessmen.getCountMove() == 0
				&& chessmen.getY() == cell.getY()
				&& (chessmen.getX() == cell.getX() - 2 || chessmen.getX() == cell
						.getX() + 2) && checkMoveCastling(cell)) {
			castling = true;
		}
		return castling;
	}

	private boolean checkMoveCastling(Cell cell) {
		boolean castling = false;
		Chessmen rook = getRookInCastling(cell);
		Cell cellRook = getCellRookCastling(cell);
		if (rook != null) {
			if (rook.getCountMove() == 0
					&& rook.checkMove(cellRook, chessmens.getChessboardStatus())) {
				castling = true;
			}
		}
		return castling;
	}

	private Chessmen getRookInCastling(Cell cell) {
		Chessmen rook = null;
		if (status.whoWalk() == ColorChessmen.white) {
			if (cell.getX() < 4) {
				rook = chessmens.getChessmen(new Cell(0, 7));
			} else {
				rook = chessmens.getChessmen(new Cell(7, 7));
			}
		} else {
			if (cell.getX() < 4) {
				rook = chessmens.getChessmen(new Cell(0, 0));
			} else {
				rook = chessmens.getChessmen(new Cell(7, 0));
			}
		}
		return rook;
	}

	private Cell getCellRookCastling(Cell cell) {
		Cell cellRook = null;
		if (status.whoWalk() == ColorChessmen.white) {
			if (cell.getX() < 4) {
				cellRook = new Cell(3, 7);
			} else {
				cellRook = new Cell(5, 7);
			}
		} else {
			if (cell.getX() < 4) {
				cellRook = new Cell(3, 0);
			} else {
				cellRook = new Cell(5, 0);
			}
		}
		return cellRook;
	}

	public List<Cell> getAvailablePathsAtActiveChessmen() {
		List<Cell> availablePaths = null;
		Chessmen chessmen = status.getChessmenActive();
		if (chessmen != null) {
			availablePaths = chessmen.getAvailablePaths(chessmens
					.getChessboardStatus());
		}
		return availablePaths;
	}

	public Cell getDangerCell() {
		Cell cell = null;
		if (status.getCellDanger() != null) {
			cell = chessmens.getKing(status.whoWalk()).getCell();
		}
		return cell;
	}
}