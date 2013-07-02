package myChess.shell.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import myChess.controller.Status;

public class PanelStatus extends JPanel {
	private static final long serialVersionUID = 1L;
	private Status status;
	private JLabel labelComment;
	private JLabel labelHistory;
	private JLabel labelWhoWalk;

	public PanelStatus(Status status) {
		this.status = status;

		setPreferredSize(new Dimension(0, 24));
		setBackground(Color.lightGray);

		this.labelComment = new JLabel("-");
		this.labelHistory = new JLabel("-");
		this.labelWhoWalk = new JLabel("-");
		add(this.labelHistory);
		add(this.labelComment);
		add(this.labelWhoWalk);
	}

	public void update() {
		this.labelHistory.setText(this.status.getCommentHistory());
		this.labelComment.setText("  |  " + this.status.getCommentGame() + "  |  ");
		this.labelWhoWalk.setText(this.status.getWhoWalk());
	}
}
