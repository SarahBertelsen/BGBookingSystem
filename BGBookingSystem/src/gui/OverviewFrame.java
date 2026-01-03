package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BookingCtrl;
import ctrl.CustomerCtrl;
import db.BoardGameCopyDB;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDB;
import model.BoardGame;
import model.BoardGameCopy;
import model.Booking;
import model.Customer;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OverviewFrame extends JFrame implements FrameIF {

	private static final long serialVersionUID = 1L;
	private GameBasketTableModel gbtm;
	private JTable boardGameTable;
	private BookingCtrl bookingCtrl;
	private List<BoardGameCopy> copies;
	private JLabel lblDate;
	private JLabel lblfName;
	private JLabel lbllName;
	private JLabel lblPhone;
	private JLabel lblNoOfGuests;
	private JLabel lblMembership;
	private JLabel lblTotalPrice;
	private CustomerDAO customerDAO;
	private MembershipDAO membershipDAO;
	private MainFrame mainFrame;

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
					MembershipDAO membershipDAO = new MembershipDB();
					BookingCtrl bookingCtrl = new BookingCtrl(new BookingDB(bgCopyDb, tableDb, cusDb, membershipDAO), cusDb, tableDb, membershipDAO);
					bookingCtrl.createBooking(LocalDateTime.now().plusHours(12), 4);
					bookingCtrl.addBoardGameCopy(new BoardGameCopy(1, new BoardGame(1, "Horse Race", null, 0, null, 0, null)));
					bookingCtrl.addBoardGameCopy(new BoardGameCopy(2, new BoardGame(2, "Partners", null, 0, null, 0, null)));
					bookingCtrl.addBoardGameCopy(new BoardGameCopy(3, new BoardGame(3, "Bingo", null, 0, null, 0, null)));
					bookingCtrl.addCustomerDetails("Thomas", "Murray", "+4553537923");
					OverviewFrame frame = new OverviewFrame(bookingCtrl, cusDb, membershipDAO, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OverviewFrame(BookingCtrl bookingCtrl, CustomerDAO customerDAO, MembershipDAO membershipDAO, MainFrame mainFrame) {
		this.bookingCtrl = bookingCtrl;
		this.copies = new ArrayList<>();
		this.gbtm= new GameBasketTableModel(copies); 
		this.customerDAO = customerDAO;
		this.membershipDAO = membershipDAO;
		this.mainFrame = mainFrame;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel lblTitle = new JLabel("Oversigt");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		getContentPane().add(lblTitle, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblGuestsInfo = new JLabel("Du har booket til");
		lblGuestsInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblGuestsInfo = new GridBagConstraints();
		gbc_lblGuestsInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblGuestsInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblGuestsInfo.gridx = 0;
		gbc_lblGuestsInfo.gridy = 0;
		panel.add(lblGuestsInfo, gbc_lblGuestsInfo);
		
		lblDate = new JLabel("New label");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 0);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 1;
		panel.add(lblDate, gbc_lblDate);
		
		lblfName = new JLabel("Navn");
		GridBagConstraints gbc_lblfName = new GridBagConstraints();
		gbc_lblfName.insets = new Insets(0, 0, 5, 0);
		gbc_lblfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblfName.gridx = 0;
		gbc_lblfName.gridy = 2;
		panel.add(lblfName, gbc_lblfName);
		
		lbllName = new JLabel("New label");
		GridBagConstraints gbc_lbllName = new GridBagConstraints();
		gbc_lbllName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbllName.insets = new Insets(0, 0, 5, 0);
		gbc_lbllName.gridx = 0;
		gbc_lbllName.gridy = 3;
		panel.add(lbllName, gbc_lbllName);
		
		lblPhone = new JLabel("New label");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 0);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 4;
		panel.add(lblPhone, gbc_lblPhone);
		
		lblNoOfGuests = new JLabel("New label");
		GridBagConstraints gbc_lblNoOfGuests = new GridBagConstraints();
		gbc_lblNoOfGuests.insets = new Insets(0, 0, 5, 0);
		gbc_lblNoOfGuests.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNoOfGuests.gridx = 0;
		gbc_lblNoOfGuests.gridy = 5;
		panel.add(lblNoOfGuests, gbc_lblNoOfGuests);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnPayment = new JButton("Continue");
		btnPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				completeBooking();
			}
		});
		panel_1.add(btnPayment, BorderLayout.EAST);
		
		lblTotalPrice = new JLabel("New label");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblTotalPrice, BorderLayout.CENTER);
		
		lblMembership = new JLabel("New label");
		panel_1.add(lblMembership, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		boardGameTable = new JTable(gbtm);
		scrollPane.setViewportView(boardGameTable);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		updateGameList(bookingCtrl.getCurrentBooking().getBoardGameCopies());
		setupLabels();
		
	}
	
	/**
	 * Updates the list of boardgames to be booked.
	 * 
	 * @param bgCopies The boardgames that will populate the list of booked boardgames.
	 */
	public void updateGameList(List<BoardGameCopy> bgCopies) {
		gbtm= new GameBasketTableModel(bgCopies);
		boardGameTable.setModel(gbtm);
	}
	
	/**
	 * Updates all labels to display the information from the booking.
	 * 
	 */
	public void setupLabels() {
		Booking booking = bookingCtrl.getCurrentBooking();
		Customer customer = booking.getCustomer();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		lblDate.setText("Booking dato: " + booking.getDate().format(formatter));
		lblfName.setText("Fornavn: " + customer.getfName());
		lbllName.setText("Efternavn: " + customer.getlName());
		lblPhone.setText("Telfonnummer: " + customer.getPhone());
		lblNoOfGuests.setText("Antal gæster: " + Integer.toString(booking.getNoOfGuests()));
		
		
		lblMembership.setText(getMembershipString(customer, booking));
		lblTotalPrice.setText("Pris i kr.:" + Double.toString(booking.getPrice()));
	}
	
	/**
	 * Gets one of two strings depending on whether or not a customer has a membership on the date of a booking.
	 * 
	 * @param customer The customer we check the membership on.
	 * @param booking The date the customer needs to be a member on for the membership to count.
	 * @return Returns either a confirmation string or an empty one depending on if the given customer is a member on the given date.
	 */
	public String getMembershipString(Customer customer, Booking booking) {
		boolean isMember = new CustomerCtrl(customerDAO, membershipDAO).checkIfMember(customer, booking.getDate().toLocalDate());
		String membershipString = "";
		if(isMember) {
			membershipString = "Medlemsrabat tilføjet";
		}
		return membershipString;
	}
	
	/**
	 * Finalizes the booking, saving it in the database, and returns back to the main menu.
	 * 
	 */
	public void completeBooking() {
		bookingCtrl.completeBooking();
		mainFrame.returnToMain();
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
