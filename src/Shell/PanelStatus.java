package Shell;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.History;
import Controller.Status;

public class PanelStatus extends JPanel {
	private static final long serialVersionUID = 1L;
	private Status status;
	private JLabel labelComment;
	private JLabel labelActivePosition;
	private JLabel labelWhoWalk;

	PanelStatus(Status status) {
		this.status = status;
		
		setPreferredSize(new Dimension(0, 24));
		setBackground(Color.lightGray);

		this.labelComment = new JLabel("-");
		this.labelActivePosition = new JLabel("-");
		this.labelWhoWalk = new JLabel("-");
		add(this.labelActivePosition);
		add(this.labelComment);
		add(this.labelWhoWalk);
	}

	public void update() {
		History history = this.status.getHistory();
		if (history != null) {
			this.labelActivePosition.setText(history.getHistory());
		}

		this.labelComment.setText("  |  " + this.status.getComment() + "  |  ");
		
		if(status.whoWalk() == Color.white){
			this.labelWhoWalk.setText("Ходят белые");
		} else {
			this.labelWhoWalk.setText("Ходят черные");
		}
	}

	public void setLabel(String string) {
	}
}
