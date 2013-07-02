package myChess.controller.history;

public abstract class HistoryType {
	static String[] coord = { "A", "B", "C", "D", "E", "G", "H", "I" };
	protected String comment;
	
	public String getCommentGame(){
		return comment;
	}
	
	public abstract String getCommentHistory();
	
	public abstract void undo();
	
	public abstract void redo();
}
