package Controller;

import java.awt.Color;

import Chessmens.Chessmen;
import Main.Cell;

public class History {
	private Cell begin;
	private Cell end;
	private Chessmen chessmen;
	private Chessmen chessmenRip;
	static String[] coord = { "A", "B", "C", "D", "E", "G", "H", "I" };

	History(Chessmen chessmen, Cell begin, Cell end, Chessmen chessRip) {
		this.begin = begin;
		this.end = end;
		this.chessmen = chessmen;
		this.chessmenRip = chessRip;
	}
	
	public Chessmen getChessmen(){
		return chessmen;
	}

	public Chessmen getChessmenRip(){
		return chessmenRip;
	}
	
	public Cell getCellBegin(){
		return this.begin;
	}
	
	public Cell getCellEnd(){
		return this.end;
	}
	
	public String getHistory() {
		String result = "" + chessmen.isWho() + ": ";
		result += History.coord[7 - begin.getY()] + begin.getX();
		result += "->";
		result += History.coord[7 - end.getY()] + end.getX();
		if(chessmenRip != null){
			result += ": убит - " + chessmenRip.isWho();
		}
		return result;
	}
	
	public Color getColor(){
		return chessmen.getColor();
	}
}
