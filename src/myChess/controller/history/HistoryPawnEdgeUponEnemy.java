package myChess.controller.history;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;
import myChess.model.Chess;
import myChess.model.chessmens.Queen;

public class HistoryPawnEdgeUponEnemy extends History {
	private Chess chess;
	private Cell cellBegin;
	private Cell cellEnd;
	private Chessmen chessmen;
	private Chessmen enemy;
	private Chessmen queen;

	public HistoryPawnEdgeUponEnemy(Chess chess, Chessmen chessmen, Chessmen enemy, Cell cellEnd) {
		this.chess = chess;
		this.cellBegin = chessmen.getCell();
		this.cellEnd = cellEnd;
		this.chessmen = chessmen;
		this.enemy = enemy;
		this.queen = new Queen(cellEnd, chessmen.getColor());
		this.comment = "Пешка превратилась";
	}

	public String getHistory() {
		String result = "" + chessmen.isWho() + ": ";
		result += History.coord[7 - cellBegin.getY()] + (cellBegin.getX() + 1);
		result += "->";
		result += History.coord[7 - cellEnd.getY()] + (cellEnd.getX() + 1);
		return result;
	}

	@Override
	public void undo() {
		chess.removeChessmen(queen);
		chess.addChessmen(chessmen);
		chessmen.reMove(cellBegin);
		chess.addChessmen(enemy);
	}

	@Override
	public void redo() {
		chess.removeChessmen(enemy);
		chessmen.move(cellEnd);
		chess.removeChessmen(chessmen);
		chess.addChessmen(queen);
	}
}
