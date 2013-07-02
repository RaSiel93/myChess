package myChess.shell;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogMessage extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton btnClose;

	public DialogMessage(JFrame frame, String header, String message) {
		super(frame, header, true);
		//setPreferredSize(new Dimension(200, 100));
		//JPanel panel = new JPanel(new BorderLayout());		
		setLayout(new BorderLayout());
		//panel.add();

		btnClose = new JButton("Закрыть");
		btnClose.addActionListener(new ListnerClose());

//		JPanel bp = new JPanel();
//		bp.add(btnClose);

		getContentPane().add(new JLabel(message), BorderLayout.CENTER);
		getContentPane().add(btnClose, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setLocationRelativeTo(frame);
	}

	private class ListnerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}