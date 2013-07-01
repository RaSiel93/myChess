package myChess.controller;

import java.awt.Color;
import java.util.List;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;
import myChess.types.TypeChessmen;
import myChess.model.Chess;
import myChess.shell.FrameMain;
import myChess.controller.history.*;

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
		status.start();
	}

	public void stop() {
		status.stop();
	}
	
	public void newGame(){
		chess.reset();
		status.reset();
		start();
		update();
	}

	public void action() {
		Chessmen chessmen = status.getChessmenActive();
		Cell cell = status.getCellActive();

		if (checkFriendChessmen(cell)) {
			setChessmenActive();
			status.setComment("Ходите");
		} else if (chessmen != null) {
			if (checkMove(chessmen, cell) && !checkAfterShah(chessmen, cell)) {
				move(chessmen, cell);
				if(checkGameOver(status.whoNoWalk())){
					//status.stop();
				}
			} else {
				status.setComment("Недопустимый ход");
			}
		} else {
			status.setComment("Выберите фигуру");
		}
		update();
	}
 
	public void move(Chessmen chessmen, Cell cell) {
		History history = null;
		if (checkCastling(chessmen, cell)) {
			Chessmen rook = getRookInCastling(cell);
			Cell cellRook = getCellRookCastling(cell);

			history = new HistoryCastling(chessmen, cell, rook, cellRook);
		} else if (checkPawnEdge(chessmen, cell)) {
			Chessmen enemy = chess.getChessmen(cell);
			if (enemy != null) {
				history = new HistoryPawnEdgeUponEnemy(chess, chessmen, enemy, cell);
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

		status.addHistory(history);
		status.setComment(history.getComment());
		history.redo();

		status.setChessmenActive(null);
		status.setChessmenDanger(null);
	}

	private boolean checkPawnEdge(Chessmen chessmen, Cell cell) {
		return (chessmen.isWho() == TypeChessmen.Pawn && (cell.getY() == 7 || cell
				.getY() == 0));
	}

	public void undoHistory() {
		if (status.undoHistory()) {
			status.setChessmenDanger(null);
			checkGameOver(status.whoNoWalk());
			update();
		}
	}

	public void redoHistory() {
		if (status.redoHistory()) {
			status.setChessmenDanger(null);
			checkGameOver(status.whoNoWalk());
			update();
		}
	}


	private boolean checkGameOver(Color color) {
		boolean gameOver = false;
		if(checkShah(color)){
			if(checkMat(color)){
				gameOver = true;
			}
		} else if(checkPad(color)){
			gameOver = true;
		}
		return gameOver;
	}
	
	private boolean checkPad(Color color) {
		boolean checkpad = false;
		if(chess.checkPad(color)){
			status.setComment("Пад");
			checkpad = true;
		}
		return checkpad;
	}

	private boolean checkMat(Color color) {
		boolean checkmat = false;
		if(!chess.checkKingPath(color)&& !chess.checkKill(chess.getChessmenDanger(color)) && !chess.checkCover(chess.getChessmenDanger(color))){
			status.setComment("Шах и Мат");
			checkmat = true;
		}
		return checkmat;
	}

	private boolean checkShah(Color color) {
		Chessmen chessmenDanger = chess.getChessmenDanger(color);
		if (chessmenDanger != null) {
			status.setChessmenDanger(chessmenDanger);
			status.setComment("Шах");
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

		chessmen.reMove(originalCell);
		if (enemy != null) {
			chess.addChessmen(enemy);
		}

		if (chessmenDanger != null) {
			status.setChessmenDanger(chessmenDanger);
			status.setComment("Недопустимый ход");
			shah = true;
		}
		return shah;
	}

	public boolean checkGameOver() {
		return false;
	}

	public void update() {
		status.update();
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

	public Color getStatus() {
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
		} else if (chessmen.checkMove(cell, chess.getChessStatus(),
				checkEnemyChessmen())) {
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
					&& rook.checkMove(cellRook, chess.getChessStatus(), false)) {
				castling = true;
			}
		}
		return castling;
	}

	private Chessmen getRookInCastling(Cell cell) {
		Chessmen rook = null;
		if (status.whoWalk() == Color.white) {
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
		if (status.whoWalk() == Color.white) {
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
}
