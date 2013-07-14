package myChess.model.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class History implements Serializable {
	private static final long serialVersionUID = 1L;
	private ListIterator<HistoryType> iterHistory;
	private List<HistoryType> listHistory;
	private HistoryType currentHistory;
	
	public List<HistoryType> getListIter(){
		return listHistory;
	}
	
	public History() {
		this.listHistory = new ArrayList<HistoryType>();
		this.iterHistory = this.listHistory.listIterator();
		this.currentHistory = null;
	}

	public String getCommentGame() {
		return this.currentHistory.getCommentGame();
	}

	public String getCommentHistory() {
		return this.currentHistory.getCommentHistory();
	}

	public void addHistory(HistoryType history) {
		removeOldHistory();
		this.currentHistory = history;
		this.listHistory.add(history);
		this.iterHistory = this.listHistory.listIterator();
		iterToLast();
	}

	private void removeOldHistory() {
		while (this.iterHistory.hasNext()) {
			this.iterHistory.next();
			this.iterHistory.remove();
		}
	}

	private void iterToLast() {
		while (this.iterHistory.hasNext()) {
			this.iterHistory.next();
		}
	}

	public boolean undo() {
		boolean undo = false;
		if (this.iterHistory.hasPrevious()) {
			this.currentHistory = iterHistory.previous();
			this.currentHistory.undo();
			undo = true;
		}
		return undo;
	}

	public boolean redo() {
		boolean redo = false;
		if (this.iterHistory.hasNext()) {
			this.currentHistory = iterHistory.next();
			this.currentHistory.execute();
			redo = true;
		}
		return redo;
	}
}
