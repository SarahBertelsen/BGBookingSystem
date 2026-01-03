package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BookingCtrl;
import ctrl.CustomerCtrl;
import db.BoardGameCopyDB;
import db.BookingDAO;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDB;
import model.Customer;
import model.CustomerSaveStatus;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AddCustomerFrame extends JFrame implements FrameIF {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fNameTxt;
	private JTextField lNameTxt;
	private JTextField telefonNummerTxt;
	private BookingCtrl bookingCtrl;
	private MainFrame mainFrame;
	private JLabel infoLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDB cusDb = new CustomerDB();
					TableDB tableDb = new TableDB();
					BoardGameCopyDB bgCopyDb = new BoardGameCopyDB();
					MembershipDAO memDb = new MembershipDB();
					BookingCtrl bookingCtrl = new BookingCtrl(new BookingDB(bgCopyDb, tableDb, cusDb, memDb), cusDb, tableDb, memDb);
					AddCustomerFrame frame = new AddCustomerFrame(bookingCtrl, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddCustomerFrame(BookingCtrl bookingCtrl, MainFrame mainFrame) {
		this.bookingCtrl = bookingCtrl;
		this.mainFrame = mainFrame;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		infoLbl = new JLabel("Indtast dine informationer");
		infoLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		infoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 0;
		contentPane.add(infoLbl, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Fornavn");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		fNameTxt = new JTextField();
		GridBagConstraints gbc_fNameTxt = new GridBagConstraints();
		gbc_fNameTxt.anchor = GridBagConstraints.WEST;
		gbc_fNameTxt.insets = new Insets(0, 0, 5, 0);
		gbc_fNameTxt.gridx = 1;
		gbc_fNameTxt.gridy = 2;
		contentPane.add(fNameTxt, gbc_fNameTxt);
		fNameTxt.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Efternavn");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 4;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lNameTxt = new JTextField();
		GridBagConstraints gbc_lNameTxt = new GridBagConstraints();
		gbc_lNameTxt.insets = new Insets(0, 0, 5, 0);
		gbc_lNameTxt.anchor = GridBagConstraints.WEST;
		gbc_lNameTxt.gridx = 1;
		gbc_lNameTxt.gridy = 5;
		contentPane.add(lNameTxt, gbc_lNameTxt);
		lNameTxt.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Telefonnummer");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 7;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		telefonNummerTxt = new JTextField();
		GridBagConstraints gbc_telefonNummerTxt = new GridBagConstraints();
		gbc_telefonNummerTxt.anchor = GridBagConstraints.WEST;
		gbc_telefonNummerTxt.insets = new Insets(0, 0, 5, 0);
		gbc_telefonNummerTxt.gridx = 1;
		gbc_telefonNummerTxt.gridy = 8;
		contentPane.add(telefonNummerTxt, gbc_telefonNummerTxt);
		telefonNummerTxt.setColumns(10);
		
		JButton btnDone = new JButton("Tilføj kunde til booking");
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_btnDone = new GridBagConstraints();
		gbc_btnDone.insets = new Insets(0, 0, 5, 0);
		gbc_btnDone.gridx = 1;
		gbc_btnDone.gridy = 9;
		contentPane.add(btnDone, gbc_btnDone);
		btnDone.addActionListener(e -> addCustomerClicked());
	}
	/**
	 * Uses the the inputs from the gui to save the customer and moves on to the overview ui.
	 * 
	 */
	private void addCustomerClicked() {
		String fName = fNameTxt.getText().trim();
	    String lName = lNameTxt.getText().trim();
	    String phone = telefonNummerTxt.getText().trim();
	    
	    if (!CustomerCtrl.isValidName(fName, lName)) {
	        JOptionPane.showMessageDialog(this, InvalidFormatException.INVALID_NAME);
	    }

	    if (!CustomerCtrl.isValidNumber(phone)) {
	        JOptionPane.showMessageDialog(this, InvalidFormatException.INVALID_NUMBER);
	    }
	    
		try {
			bookingCtrl.addCustomerDetails(fName, lName, phone);
			if(bookingCtrl.getCurrentBooking().getCustomer().getStatus() == CustomerSaveStatus.CUSTOMER_DOES_NOT_EXIST) {
				 JOptionPane.showMessageDialog(this, guiMessages.NEW_CUSTOMER);
			} else {
				JOptionPane.showMessageDialog(this, guiMessages.EXISTING_CUSTOMER);
			}
			mainFrame.completeBooking();
			
		} catch (Exception e){
			 JOptionPane.showMessageDialog(this, InvalidFormatException.ADD_CUSTOMER_ISSUE);
		}
		
	}

	@Override
	public void enter() {
		setVisible(true);
	}

	@Override
	public void exit() {
		setVisible(false);
	}

	@Override
	public void close() {
		exit();
		dispose();
	}

}
