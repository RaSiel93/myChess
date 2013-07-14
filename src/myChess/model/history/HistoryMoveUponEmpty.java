package myChess.model.history;

import java.io.Serializable;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;

public class HistoryMoveUponEmpty extends HistoryType  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Cell cellBegin;
	private Cell cellEnd;
	private Chessmen chessmen;

	public HistoryMoveUponEmpty(Chessmen chessmen, Cell cellEnd) {
		this.cellBegin = chessmen.getCell();
		this.cellEnd = cellEnd;
		this.chessmen = chessmen;
		this.comment = "Õîä";
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
	public void execute() {
		chessmen.move(cellEnd);
	}
}
