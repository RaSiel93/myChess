package myChess.model.history;

import java.io.Serializable;

public abstract class HistoryType implements Serializable {
	private static final long serialVersionUID = 1L;
	static String[] coord = { "A", "B", "C", "D", "E", "G", "H", "I" };
	protected String comment;

	public String getCommentGame() {
		return comment;
	}

	public abstract String getCommentHistory();

	public abstract void undo();

	public abstract void execute();
}
