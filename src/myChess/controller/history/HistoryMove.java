package myChess.controller.history;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;

public class HistoryMove extends HistoryType {
	private Cell cellBegin;
	private Cell cellEnd;
	private Chessmen chessmen;

	public HistoryMove(Chessmen chessmen, Cell cellEnd) {
		this.cellBegin = chessmen.getCell();
		this.cellEnd = cellEnd;
		this.chessmen = chessmen;
		this.comment = "���";
	}

	public String getCommentHistory() {
		String result = "" + chessmen.isWho() + ": ";
		result += HistoryType.coord[7 - cellBegin.getY()] + (cellBegin.getX() + 1);
		result += "->";
		result += HistoryType.coord[7 - cellEnd.getY()] + (cellEnd.getX() + 1);
		return result;
	}

	@Override
	public void undo() {
		chessmen.unmove(cellBegin);
	}

	@Override
	public void redo() {
		chessmen.move(cellEnd);
	}
}
