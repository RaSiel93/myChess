package myChess.controller;

import java.util.List;

import myChess.controller.history.*;
import myChess.model.chessmens.Chessmen;
import myChess.model.Chess;
import myChess.shell.FrameMain;
import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.types.ColorChessmen;

public class Controller {
	private Chess chess;
	private FrameMain frameMain;
	private Status status;

	public Controller() {
	}

	public void setFrame(FrameMain frameMain) {
		this.frameMain = frameMain;
	}

	public void setModel(Chess chess) {
		this.chess = chess;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isGame() {
		return this.status.isGame();
	}

	public void start() {
		chess.reset();
		chess.loadChessmens();
		status.reset();
		status.start();
	}

	public void stop() {
		status.stop();
	}

	public void newGame() {
		start();
		update();
	}

	public void action() {
		Chessmen chessmen = status.getChessmenActive();
		Cell cell = status.getCellActive();

		if (checkFriendChessmen(cell)) {
			setChessmenActive();
			status.setCommentGame("Ходите");
		} else if (chessmen != null) {
			if (checkMove(chessmen, cell)) {
				if (checkAfterShah(chessmen, cell)) {
					status.setCommentGame("Ход под шах");
				} else {
					move(chessmen, cell);
					if (checkGameOver(status.whoWalk())) {
						status.stop();
					}
				}
			} else {
				status.setCommentGame("Недопустимый ход");
			}
		} else {
			status.setCommentGame("Выберите фигуру");
		}
		update();
	}

	public void move(Chessmen chessmen, Cell cell) {
		HistoryType history = null;
		if (checkCastling(chessmen, cell)) {
			history = new HistoryCastling(chessmen, cell,
					getRookInCastling(cell), getCellRookCastling(cell));
		} else if (checkPawnEdge(chessmen, cell)) {
			Chessmen enemy = chess.getChessmen(cell);
			if (enemy != null) {
				history = new HistoryPawnEdgeUponEnemy(chess, chessmen, enemy,
						cell);
			} else {
				history = new HistoryPawnEdge(chess, chessmen, cell);
			}
		} else {
			Chessmen enemy = chess.getChessmen(cell);
			if (enemy != null) {
				history = new HistoryMoveUponEnemy(chess, chessmen, enemy, cell);
			} else {
				history = new HistoryMove(chessmen, cell);
			}
		}
		status.move(history);
	}

	private boolean checkPawnEdge(Chessmen chessmen, Cell cell) {
		return (chessmen.isWho() == TypeChessmen.Pawn && (cell.getY() == 7 || cell
				.getY() == 0));
	}

	private boolean checkGameOver(ColorChessmen color) {
		boolean gameOver = false;
		if (checkShah(color)) {
			if (checkMat(color)) {
				gameOver = true;
			}
		} else if (checkPad(color)) {
			gameOver = true;
		}
		return gameOver;
	}

	private boolean checkPad(ColorChessmen color) {
		boolean checkpad = false;
		if (chess.checkPad(color)) {
			status.setCommentGame("Пад");
			checkpad = true;
		}
		return checkpad;
	}

	private boolean checkMat(ColorChessmen color) {
		boolean checkmat = false;
		if (!chess.checkKingPath(color)
				&& !chess.checkKill(chess.getChessmenDanger(color))
				&& !chess.checkCover(chess.getChessmenDanger(color))) {
			status.setCommentGame("Шах и Мат");
			checkmat = true;
		}
		return checkmat;
	}

	private boolean checkShah(ColorChessmen color) {
		Chessmen chessmenDanger = chess.getChessmenDanger(color);
		if (chessmenDanger != null) {
			status.setChessmenDanger(chessmenDanger);
			status.setCommentGame("Шах");
			return true;
		}
		return false;
	}

	private boolean checkAfterShah(Chessmen chessmen, Cell cell) {
		boolean shah = false;

		if (checkCastling(chessmen, cell)) {
			if (checkShah(status.whoWalk())) {
				shah = true;
			}
		}

		Cell originalCell = chessmen.getCell();

		Chessmen enemy = chess.getChessmen(cell);
		chess.removeChessmen(enemy);

		chessmen.move(cell);

		Chessmen chessmenDanger = chess.getChessmenDanger(status.whoWalk());

		chessmen.unmove(originalCell);
		if (enemy != null) {
			chess.addChessmen(enemy);
		}

		if (chessmenDanger != null) {
			status.setChessmenDanger(chessmenDanger);
			status.setCommentGame("Недопустимый ход");
			shah = true;
		}
		return shah;
	}

	public boolean checkGameOver() {
		return false;
	}

	public void update() {
		frameMain.update();
		frameMain.repaint();
	}

	public Cell getCellActive() {
		return status.getCellActive();
	}

	public void setCellActive(int x, int y) {
		status.setCellActive(new Cell(x, y));
		frameMain.repaint();
	}

	public Chessmen getCellDanger() {
		return status.getCellDanger();
	}

	public void setCellDanger(Chessmen chessmen) {
		status.setChessmenDanger(chessmen);
		frameMain.repaint();
	}

	public Chessmen getChessmenActive() {
		return status.getChessmenActive();
	}

	public void setChessmenActive() {
		status.setChessmenActive(chess.getChessmen(status.getCellActive()));
	}

	public ColorChessmen getStatus() {
		return status.whoWalk();
	}

	public List<Chessmen> getChessmens() {
		return chess.getChessmens();
	}

	public boolean checkFriendChessmen(Cell cell) {
		Chessmen chessmen = chess.getChessmen(cell);
		if (chessmen != null) {
			if (chessmen.getColor() == status.whoWalk()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkEnemyChessmen() {
		Chessmen chessmen = chess.getChessmen(status.getCellActive());
		if (chessmen != null) {
			if (chessmen.getColor() != status.whoWalk()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkMove(Chessmen chessmen, Cell cell) {
		boolean move = false;
		if (checkCastling(chessmen, cell)) {
			move = true;
		} else if (chessmen.checkMove(cell, chess.getChessStatus())) {
			move = true;
		}
		return move;
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
					&& rook.checkMove(cellRook, chess.getChessStatus())) {
				castling = true;
			}
		}
		return castling;
	}

	private Chessmen getRookInCastling(Cell cell) {
		Chessmen rook = null;
		if (status.whoWalk() == ColorChessmen.white) {
			if (cell.getX() < 4) {
				rook = chess.getChessmen(new Cell(0, 7));
			} else {
				rook = chess.getChessmen(new Cell(7, 7));
			}
		} else {
			if (cell.getX() < 4) {
				rook = chess.getChessmen(new Cell(0, 0));
			} else {
				rook = chess.getChessmen(new Cell(7, 0));
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

	public void setChessmenDanger(Chessmen chessmen) {
		status.setChessmenDanger(chessmen);
	}

	public void undoHistory() {
		if (status.undoHistory()) {
			status.switchWalk();
			checkGameOver(status.whoWalk());
			update();
		}
	}

	public void redoHistory() {
		if (status.redoHistory()) {
			status.switchWalk();
			checkGameOver(status.whoWalk());
			update();
		}
	}

	public List<Cell> getAvailablePathsAtActiveChessmen() {
		List<Cell> availablePaths = null;
		Chessmen chessmen = status.getChessmenActive();
		if (chessmen != null) {
			availablePaths = chessmen.getAvailablePaths(chess.getChessStatus());
		}
		return availablePaths;
	}

	public Cell getDangerCell() {
		Cell cell = null;
		if (status.getCellDanger() != null) {
			cell = chess.getKing(status.whoWalk()).getCell();
		}
		return cell;
	}

	public Chessmen getChessmen(Cell cell) {
		return chess.getChessmen(cell);
	}
}
