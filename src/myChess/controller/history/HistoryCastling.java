package myChess.controller.history;

import myChess.model.chessmens.Chessmen;
import myChess.types.Cell;

public class HistoryCastling extends History {
	private Cell cellBeginKing;
	private Cell cellEndKing;
	private Cell cellBeginRook;
	private Cell cellEndRook;
	private Chessmen king;
	private Chessmen rook;

	public HistoryCastling(Chessmen king, Cell cellEndKing, Chessmen rook,
			Cell cellEndRook) {
		this.cellBeginKing = king.getCell();
		this.cellEndKing = cellEndKing;
		this.cellBeginRook = rook.getCell();
		this.cellEndRook = cellEndRook;
		this.king = king;
		this.rook = rook;
		this.comment = "���������";
	}

	public String getHistory() {
		String result = "King: ";
		result += History.coord[7 - cellBeginKing.getY()]
				+ (cellBeginKing.getX() + 1);
		result += "->";
		result += History.coord[7 - cellEndKing.getY()]
				+ (cellEndKing.getX() + 1);
		result += "; Rook: ";
		result += History.coord[7 - cellBeginRook.getY()]
				+ (cellBeginRook.getX() + 1);
		result += "->";
		result += History.coord[7 - cellEndRook.getY()]
				+ (cellEndRook.getX() + 1);
		return result;
	}

	@Override
	public void undo() {
		king.reMove(cellBeginKing);
		rook.reMove(cellBeginRook);
	}

	@Override
	public void redo() {
		king.move(cellEndKing);
		rook.move(cellEndRook);
	}
}