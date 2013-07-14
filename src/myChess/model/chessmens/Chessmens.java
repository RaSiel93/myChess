package myChess.model.chessmens;

import java.util.ArrayList;
import java.util.List;

import myChess.types.Cell;
import myChess.types.ColorChessmen;
import myChess.types.TypeChessmen;

public class Chessmens {
	private List<Chessmen> chessmens;
	private Chessmen kingWhite;
	private Chessmen kingBlack;

	public Chessmens(List<Chessmen> chessmens) {
		if (chessmens == null) {
			this.chessmens = Chessmens.getStandartChessmens();
		} else {
			this.chessmens = chessmens;
		}
		updateKingPositions();
	}

	public static List<Chessmen> getStandartChessmens() {
		List<Chessmen> chessmens = new ArrayList<Chessmen>();
		for (int num = 0; num < 8; num++) {
			chessmens.add(new Pawn(new Cell(num, 6), ColorChessmen.white, 0));
			chessmens.add(new Pawn(new Cell(num, 1), ColorChessmen.black, 0));
		}

		chessmens.add(new Rook(new Cell(0, 7), ColorChessmen.white, 0));
		chessmens.add(new Rook(new Cell(7, 7), ColorChessmen.white, 0));
		chessmens.add(new Rook(new Cell(0, 0), ColorChessmen.black, 0));
		chessmens.add(new Rook(new Cell(7, 0), ColorChessmen.black, 0));
		chessmens.add(new Horse(new Cell(1, 7), ColorChessmen.white, 0));
		chessmens.add(new Horse(new Cell(6, 7), ColorChessmen.white, 0));
		chessmens.add(new Horse(new Cell(1, 0), ColorChessmen.black, 0));
		chessmens.add(new Horse(new Cell(6, 0), ColorChessmen.black, 0));
		chessmens.add(new Officer(new Cell(2, 7), ColorChessmen.white, 0));
		chessmens.add(new Officer(new Cell(5, 7), ColorChessmen.white, 0));
		chessmens.add(new Officer(new Cell(2, 0), ColorChessmen.black, 0));
		chessmens.add(new Officer(new Cell(5, 0), ColorChessmen.black, 0));
		chessmens.add(new Queen(new Cell(3, 7), ColorChessmen.white, 0));
		chessmens.add(new Queen(new Cell(3, 0), ColorChessmen.black, 0));
		chessmens.add(new King(new Cell(4, 7), ColorChessmen.white, 0));
		chessmens.add(new King(new Cell(4, 0), ColorChessmen.black, 0));

		return chessmens;
	}

	private void updateKingPositions() {
		for (Chessmen chessmen : this.chessmens) {
			if (chessmen.isWho() == TypeChessmen.King) {
				if (chessmen.getColor() == ColorChessmen.white) {
					this.kingWhite = chessmen;
				} else {
					this.kingBlack = chessmen;
				}
			}
		}
	}

	public Chessmen getChessmen(Cell cell) {
		Chessmen chessmen = null;
		for (Chessmen currentChessmen : this.chessmens) {
			if (currentChessmen.getX() == cell.getX()
					&& currentChessmen.getY() == cell.getY()) {
				chessmen = currentChessmen;
				break;
			}
		}
		return chessmen;
	}
	
	public List<Chessmen> getChessmens() {
		return this.chessmens;
	}

	public void addChessmen(Chessmen chessmen) {
		chessmens.add(chessmen);
	}

	public void removeChessmen(Chessmen chessmen) {
		chessmens.remove(chessmen);
	}

	public ColorChessmen[][] getChessboardStatus() {
		ColorChessmen chessboardStatus[][] = new ColorChessmen[8][8];
		for (Chessmen chessmen : this.chessmens) {
			chessboardStatus[chessmen.getX()][chessmen.getY()] = chessmen
					.getColor();
		}
		return chessboardStatus;
	}

	private boolean checkShah(ColorChessmen color) {
		return getChessmenMoveTo(getKing(color).getCell(),
				ColorChessmen.switchColor(color)) != null;
	}

	public Chessmen getChessmenDanger(ColorChessmen color) {
		return getChessmenMoveTo(getKing(color).getCell(),
				ColorChessmen.switchColor(color));
	}

	private Chessmen getChessmenMoveTo(Cell cell, ColorChessmen color) {
		Chessmen chessmen = null;
		for (Chessmen currentChessmen : this.chessmens) {
			if (currentChessmen.getColor() == color) {
				if (currentChessmen.checkMove(cell, getChessboardStatus())) {
					chessmen = currentChessmen;
					break;
				}
			}
		}
		return chessmen;
	}

	public boolean checkKingPath(ColorChessmen color) {
		boolean checkPath = false;

		Chessmen king = getKing(color);
		List<Cell> cells = king.getAvailablePaths(getChessboardStatus());

		for (Cell cell : cells) {
			Cell cellOriginal = king.getCell();
			Chessmen enemy = getChessmen(cell);
			if (enemy != null) {
				removeChessmen(enemy);
			}
			king.move(cell);
			if (!checkShah(color)) {
				checkPath = true;
			}
			if (enemy != null) {
				addChessmen(enemy);
			}
			king.unmove(cellOriginal);
		}

		return checkPath;
	}

	public Chessmen getKing(ColorChessmen color) {
		Chessmen chessmen;
		if (color == ColorChessmen.white) {
			chessmen = this.kingWhite;
		} else {
			chessmen = this.kingBlack;
		}
		return chessmen;
	}

	public boolean checkKiller(Chessmen chessmen) {
		boolean killer = false;
		Cell cell = chessmen.getCell();
		Chessmen chessmenKill = getChessmenMoveTo(cell,
				ColorChessmen.switchColor(chessmen.getColor()));
		if (chessmenKill != null) {
			Cell cellOriginal = chessmenKill.getCell();
			chessmens.remove(chessmen);
			chessmenKill.move(cell);
			if (!checkShah(chessmenKill.getColor())) {
				killer = true;
			}
			chessmens.add(chessmen);
			chessmenKill.unmove(cellOriginal);
		}
		return killer;
	}

	public boolean checkCover(Chessmen chessmen) {
		boolean cover = false;
		ColorChessmen color = ColorChessmen.switchColor(chessmen.getColor());
		Chessmen king = getKing(color);

		List<Cell> cells = chessmen.getAttackPath(king.getCell());

		for (Cell cell : cells) {
			Chessmen friend = getChessmenMoveTo(cell, color);
			if (friend != null) {
				Cell cellOriginal = friend.getCell();
				friend.move(cell);
				if (getChessmenDanger(color) == null) {
					cover = true;
				}
				friend.unmove(cellOriginal);
			}
		}

		return cover;
	}

	public boolean checkPad(ColorChessmen color) {
		boolean pad = true;
		for (Chessmen chessmen : this.chessmens) {
			if (chessmen.getColor() == color) {
				for (Cell cell : chessmen.getAvailablePaths(getChessboardStatus())) {
					Cell cellOriginal = chessmen.getCell();
					chessmen.move(cell);
					if (!checkShah(color)) {
						pad = false;
					}
					chessmen.unmove(cellOriginal);
				}
			}
		}
		return pad;
	}
}
