package myChess.controller.history;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;

public class HistoryMove extends History {
	private Cell cellBegin;
	private Cell cellEnd;
	private Chessmen chessmen;

	public HistoryMove(Chessmen chessmen, Cell cellEnd) {
		this.cellBegin = chessmen.getCell();
		this.cellEnd = cellEnd;
		this.chessmen = chessmen;
		this.comment = "Õîä";
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
		chessmen.reMove(cellBegin);
	}

	@Override
	public void redo() {
		chessmen.move(cellEnd);
	}
}
