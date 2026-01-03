package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BoardGameCopyCtrl;
import ctrl.BookingCtrl;
import db.BoardGameCopyDB;
import db.BookingDB;
import db.CustomerDB;
import db.MembershipDB;
import db.TableDB;
import model.BoardGame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayBoardGameInfoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public DisplayBoardGameInfoDialog(JFrame FindAvailableBoardGameFrame, BoardGame game) {
		//modality is set to true
		super(FindAvailableBoardGameFrame, game.getName(), true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel nameLbl = new JLabel(game.getName());
			nameLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
			nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(nameLbl, BorderLayout.NORTH);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				JTextArea textArea = new JTextArea(game.getDescription());
				scrollPane.setViewportView(textArea);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeBtn = new JButton("Luk vindue");
				closeBtn.setFont(new Font("Tahoma", Font.BOLD, 10));
				closeBtn.setActionCommand("Cancel");
				buttonPane.add(closeBtn);
				closeBtn.addActionListener(e -> dispose());
			}
		}
		
        setSize(400, 300);
        setLocationRelativeTo(FindAvailableBoardGameFrame);
	}

}
