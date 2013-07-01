package myChess.controller.history;

public abstract class History {
	static String[] coord = { "A", "B", "C", "D", "E", "G", "H", "I" };
	protected String comment;
	
	public String getComment(){
		return comment;
	}
	
	public abstract String getHistory();
	
	public abstract void undo();
	
	public abstract void redo();
}
