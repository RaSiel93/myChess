package Controller;

import java.awt.Color;
import java.util.List;

import Chessmens.Chessmen;
import Main.Cell;
import Model.Chess;
import Shell.FrameMain;

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
		status.startWith(Color.white);
	}

	public void move() {
		Cell cell = status.getCellActive();
		Chessmen chessmen = status.getChessmenActive();
		Chessmen enemy = getChessmen(cell.getX(), cell.getY());
		status.setComment("Игра");
		if (enemy != null) {
			chess.remove(enemy);
			status.setComment("Сбита фигура");
		}
		status.addHistory(chessmen.getX(), chessmen.getY(), cell.getX(),
				cell.getY(), enemy);
		chessmen.move(cell);

		Cell shah = checkShah();
		if (shah != null) {
			undoHistory();
			status.setCellDanger(shah);
			status.setComment("Ход под шах");
			status.deleteLastHistory();
		} else {
			status.setCellDanger(null);
		}
		status.nextMove();
		update();
	}

	public Cell checkShah() {
		return chess.checkShah(status.whoWalk());
	}

	public boolean checkGameOver() {
		return false;
	}

	public void update() {
		frameMain.update();
		frameMain.repaint();
	}

	public void undoHistory() {
		History history = status.getHistory();
		if (history != null) {
			history.getChessmen().move(history.getCellBegin());
			Chessmen chessmenRip = history.getChessmenRip();
			status.setComment("Игра");
			if (chessmenRip != null) {
				chess.addChessmen(chessmenRip);
				status.setComment("Фигура сбита");
			}
			status.prevHistory();
			status.nextMove();
			
			updateDanger();
			update();
		}
	}

	public void redoHistory() {
		status.nextHistory();
		History history = status.getHistory();
		if (history != null) {
			history.getChessmen().move(history.getCellEnd());
			Chessmen chessmenRip = history.getChessmenRip();
			status.setComment("Игра");
			if (chessmenRip != null) {
				chess.remove(chessmenRip);
			}
			status.nextMove();
			
			updateDanger();
			update();
		} else {
			status.prevHistory();
		}
	}
	
	private void updateDanger(){
		Cell shah = checkShah();
		if (shah != null) {
			status.setCellDanger(shah);
		} else {
			status.setCellDanger(null);
		}
	}

//	public Cell getCell(int x, int y) {
//		return chess.getCell(x, y);
//	}

	public Chessmen getChessmen(int x, int y) {
		return chess.getChessmen(x, y);
	}

	public Cell getCellActive() {
		return status.getCellActive();
	}

	public Cell getCellDanger() {
		return status.getCellDanger();
	}

	public void setCellDanger(int x, int y) {
		status.setCellDanger(new Cell(x, y));
		frameMain.repaint();
	}

	public void setCellActive(int x, int y) {
		status.setCellActive(new Cell(x, y));
		frameMain.repaint();
	}

	public void setChessmenActive() {
		Cell cell = status.getCellActive();
		status.setChessmenActive(chess.getChessmen(cell.getX(), cell.getY()));
		frameMain.repaint();
	}

	public Color getStatus() {
		return status.whoWalk();
	}

	public List<Chessmen> getChessmens() {
		return chess.getChessmens();
	}

	public Chessmen getChessmenActive() {
		return status.getChessmenActive();
	}

	public boolean checkFriendChessmen() {
		Chessmen chessmen = chess.getChessmen(status.getCellActive().getX(),
				status.getCellActive().getY());
		if (chessmen != null) {
			if (chessmen.getColor() == status.whoWalk()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkEnemyChessmen() {
		Chessmen chessmen = chess.getChessmen(status.getCellActive().getX(),
				status.getCellActive().getY());
		if (chessmen != null) {
			if (chessmen.getColor() != status.whoWalk()) {
				return true;
			}
		}
		return false;
	}

	// public boolean isFree(int x, int y) {
	// return chess.isFree(x, y);
	// }

	public boolean checkMove() {
		Chessmen chessmen = status.getChessmenActive();
		return chessmen.checkMove(status.getCellActive(),
				chess.getChessStatus(), checkEnemyChessmen());
	}

	public boolean isGame() {
		return this.status.isGame();
	}

	public void stop() {
		status.stop();
	}

	public void setCellDanger(Cell cell) {
		status.setCellDanger(cell);
	}

	public void setCommentShah() {
		status.setCommentShah();
	}

}
