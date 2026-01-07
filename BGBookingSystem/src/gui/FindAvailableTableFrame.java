package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BookingCtrl;
import db.BoardGameCopyDAO;
import db.BoardGameCopyDB;
import db.BookingDAO;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.Booking;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindAvailableTableFrame extends JFrame implements FrameIF {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField indtastDatoTxt;
	private JTextField indtastStarttidspunkt;
	private JTextField indtastDeltagere;
	private BookingCtrl bookingCtrl;
	private MainFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				BookingDAO bookingDao = null;
				CustomerDAO customerDao = null;
				TableDAO tableDao = null;
				MembershipDAO membershipDao = null;
				BoardGameCopyDAO bgcDao = null;

				try {
					customerDao = new CustomerDB();
					tableDao = new TableDB();
					membershipDao = new MembershipDB();
					bgcDao = new BoardGameCopyDB();
					bookingDao = new BookingDB(bgcDao, tableDao, customerDao, membershipDao);

					BookingCtrl bookingCtrl = new BookingCtrl(bookingDao, customerDao, tableDao, membershipDao);

					FindAvailableTableFrame frame = new FindAvailableTableFrame(bookingCtrl, null);
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
	public FindAvailableTableFrame(BookingCtrl bookingCtrl, MainFrame mainFrame) {
		this.bookingCtrl = bookingCtrl;
		this.mainFrame = mainFrame;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		
		JButton tilbageButton = new JButton("Tilbage");
		tilbageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.back();
			}
		});
		tilbageButton.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_tilbageButton = new GridBagConstraints();
		gbc_tilbageButton.anchor = GridBagConstraints.WEST;
		gbc_tilbageButton.insets = new Insets(0, 0, 5, 0);
		gbc_tilbageButton.gridx = 1;
		gbc_tilbageButton.gridy = 0;
		contentPane.add(tilbageButton, gbc_tilbageButton);

		JLabel lblBookingInfo = new JLabel("BookingInfo");
		GridBagConstraints gbc_lblBookingInfo = new GridBagConstraints();
		gbc_lblBookingInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblBookingInfo.gridx = 1;
		gbc_lblBookingInfo.gridy = 1;
		contentPane.add(lblBookingInfo, gbc_lblBookingInfo);

		JLabel lblDato = new JLabel("Indtast Dato");
		GridBagConstraints gbc_lblDato = new GridBagConstraints();
		gbc_lblDato.anchor = GridBagConstraints.WEST;
		gbc_lblDato.insets = new Insets(0, 0, 5, 0);
		gbc_lblDato.gridx = 1;
		gbc_lblDato.gridy = 2;
		contentPane.add(lblDato, gbc_lblDato);

		indtastDatoTxt = new JTextField();
		GridBagConstraints gbc_indtastDatoTxt = new GridBagConstraints();
		gbc_indtastDatoTxt.insets = new Insets(0, 0, 5, 0);
		gbc_indtastDatoTxt.anchor = GridBagConstraints.WEST;
		gbc_indtastDatoTxt.gridx = 1;
		gbc_indtastDatoTxt.gridy = 3;
		contentPane.add(indtastDatoTxt, gbc_indtastDatoTxt);
		indtastDatoTxt.setColumns(10);

		JLabel lblStarttidspunkt = new JLabel("Indtast Starttidspunkt");
		GridBagConstraints gbc_lblStarttidspunkt = new GridBagConstraints();
		gbc_lblStarttidspunkt.anchor = GridBagConstraints.WEST;
		gbc_lblStarttidspunkt.insets = new Insets(0, 0, 5, 0);
		gbc_lblStarttidspunkt.gridx = 1;
		gbc_lblStarttidspunkt.gridy = 5;
		contentPane.add(lblStarttidspunkt, gbc_lblStarttidspunkt);

		indtastStarttidspunkt = new JTextField();
		GridBagConstraints gbc_indtastStarttidspunkt = new GridBagConstraints();
		gbc_indtastStarttidspunkt.insets = new Insets(0, 0, 5, 0);
		gbc_indtastStarttidspunkt.anchor = GridBagConstraints.WEST;
		gbc_indtastStarttidspunkt.gridx = 1;
		gbc_indtastStarttidspunkt.gridy = 6;
		contentPane.add(indtastStarttidspunkt, gbc_indtastStarttidspunkt);
		indtastStarttidspunkt.setColumns(10);

		JLabel lblDeltager = new JLabel("Indtast Deltagere");
		GridBagConstraints gbc_lblDeltager = new GridBagConstraints();
		gbc_lblDeltager.anchor = GridBagConstraints.WEST;
		gbc_lblDeltager.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeltager.gridx = 1;
		gbc_lblDeltager.gridy = 8;
		contentPane.add(lblDeltager, gbc_lblDeltager);

		indtastDeltagere = new JTextField();
		GridBagConstraints gbc_indtastDeltagere = new GridBagConstraints();
		gbc_indtastDeltagere.insets = new Insets(0, 0, 5, 0);
		gbc_indtastDeltagere.anchor = GridBagConstraints.WEST;
		gbc_indtastDeltagere.gridx = 1;
		gbc_indtastDeltagere.gridy = 9;
		contentPane.add(indtastDeltagere, gbc_indtastDeltagere);
		indtastDeltagere.setColumns(10);

		JButton btnBookBord = new JButton("Book Bord");
		GridBagConstraints gbc_btnBookBord = new GridBagConstraints();
		gbc_btnBookBord.insets = new Insets(0, 0, 5, 0);
		gbc_btnBookBord.anchor = GridBagConstraints.WEST;
		gbc_btnBookBord.gridx = 1;
		gbc_btnBookBord.gridy = 13;
		contentPane.add(btnBookBord, gbc_btnBookBord);

		btnBookBord.addActionListener(e -> createBooking());

	}

	/**
	 * Gets the values from the ui to create a booking and moves on the addBoardGameCopy frame if successful.
	 * 
	 */
	private void createBooking() {
		try {
			LocalDateTime dateTime = getDateTime();
			String guestsString = indtastDeltagere.getText().trim();
			int guests = Integer.parseInt(guestsString);

			if (!bookingCtrl.isValidDate(dateTime)) {
				JOptionPane.showMessageDialog(this, InvalidFormatException.INVALID_DATE_TIME);
			}

			Booking booking = bookingCtrl.createBooking(dateTime, guests);

			if (booking == null) {
				JOptionPane.showMessageDialog(this, InvalidFormatException.NO_AVAILABLE_TABLES);
			} else {
				JOptionPane.showMessageDialog(this, guiMessages.TABLE_BOOKED + "\n" + booking.getTable().getTableNo());
				mainFrame.addBoardGameCopy();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, InvalidFormatException.UNEXPECTED_BOOKING_ISSUE);
		}
	}

	/**
	 * Gets the date and time used in the gui.
	 * 
	 * @return Returns the date and time combined as LocalDateTime.
	 */
	private LocalDateTime getDateTime() {
		String dateString = indtastDatoTxt.getText().trim();
		String startTimeString = indtastStarttidspunkt.getText().trim();

		DateTimeFormatter dateConverter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // MM is months
		DateTimeFormatter timeConverter = DateTimeFormatter.ofPattern("HH:mm"); // HH for 24 hour clock. mm is minutes

		LocalDate date = LocalDate.parse(dateString, dateConverter);
		LocalTime time = LocalTime.parse(startTimeString, timeConverter);
		LocalDateTime dateTime = LocalDateTime.of(date, time);

		System.out.println(dateTime);

		return dateTime;
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
