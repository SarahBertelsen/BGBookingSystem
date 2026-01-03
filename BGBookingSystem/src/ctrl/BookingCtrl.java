package ctrl;

import model.Booking;
import model.Customer;
import model.Table;
import model.BoardGameCopy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import db.BoardGameCopyDAO;
import db.BookingDAO;
import db.CustomerDAO;
import db.DBConnection;
import db.DatabaseException;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;

public class BookingCtrl implements BookingCtrlIF {
	private TableCtrl tableCtrl;
	private CustomerCtrl customerCtrl;
	private BoardGameCopyCtrl bgCopyCtrl;

	private BookingDAO bookingDao;
	private TableDAO tableDao;
	private CustomerDAO customerDao;
	private BoardGameCopyDAO bgCopyDao;
	private MembershipDAO membershipDAO;

	private DBConnection dbConnection;
	private Booking currentBooking;
	
	// Initialize Ctrls + DAOs + connection to database
	
	public BookingCtrl(BookingDAO bookingDao, CustomerDAO customerDao, TableDAO tableDao, MembershipDAO membershipDAO) {
		this.bookingDao = bookingDao;

		this.customerCtrl = new CustomerCtrl(customerDao, membershipDAO);
		this.tableCtrl = new TableCtrl(tableDao, bookingDao);
		this.bgCopyCtrl = new BoardGameCopyCtrl(bookingDao, bgCopyDao);
		this.membershipDAO = membershipDAO;

		dbConnection = DBConnection.getInstance();
	}

	/**
	 * Create a new Booking object. Checks whether or not the desired date is not in
	 * the past. If not, a new Booking is created and calls the findTableByCriteria
	 * method in the TableCtrl and the found table is added to the Booking.
	 * 
	 * @param date       The date the customer want to create a booking
	 * @param noOfGuests The number of guests that will arrive
	 * @return Booking The Booking that is created with the given date, number of
	 *         guests and the added table.
	 */
	@Override
	public Booking createBooking(LocalDateTime date, int noOfGuests) {
		currentBooking = null;
		if (isValidDate(date) && isValidNoOfGuests(noOfGuests)){
			currentBooking = new Booking(date, noOfGuests);
			Table table = tableCtrl.findTableByCriteria(date, noOfGuests);
			currentBooking.setTable(table);
		}
		return currentBooking;
	}

	/**
	 * Add a BoardGameCopy to the Booking object. Checks whether or not the current
	 * Booking has less than 3 BoardGameCopy added to it. If it does, the
	 * BoardGameCopy is added to the current Booking.
	 * 
	 * @param bgCopy The BoardGameCopy object that must be added to the booking.
	 * @return BoardGameCopy The added BoardGameCopy.
	 */
	@Override
	public void addBoardGameCopy(BoardGameCopy bgCopy) {
		if (currentBooking.getBoardGameCopies().size() < 3) {
			currentBooking.addBoardGameCopy(bgCopy);
		} else {
			throw new InvalidFormatException(InvalidFormatException.INVALID_NO_OF_GAMES);
		}
	}

	/**
	 * Create a Customer instance via CustomerCtrl and add it to the current
	 * booking.
	 * 
	 * @param fName The customer's first name.
	 * @param lName The customer's last name.
	 * @param phone The customer's telephone number.
	 * @return Booking The booking that has been updated with the Customer instance.
	 */
	@Override
	public Booking addCustomerDetails(String fName, String lName, String phone) {
		try {
			Customer customer = customerCtrl.findOrCreateCustomer(fName, lName, phone);
			currentBooking.addCustomer(customer);
			currentBooking.setPrice(calculatePrice(currentBooking));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (DatabaseException e2) {
			e2 = new DatabaseException(DatabaseException.COULD_NOT_ADD_CUSTOMER);
			e2.printStackTrace();
		}
		return currentBooking;
	}

	/**
	 * Complete the Booking and save it to the database. Calls the saveBooking and
	 * saveBoardGameBooking method in the BookingDB class to save the booking in the
	 * database.
	 * 
	 * @return Booking The booking object that is saved.
	 */
	@Override
	public Booking completeBooking() {
		bookingDao.saveBooking(currentBooking);
		return currentBooking;
	}

	/**
	 * Returns the current Booking that is being created.
	 * 
	 * @return Booking The current Booking.
	 */
	@Override
	public Booking getCurrentBooking() {
		return currentBooking;
	}

	/**
	 * Calculate the price of the booking. 
	 * @return double The price of the booking.
	 */
	@Override
	public double calculatePrice(Booking booking) {
		Customer customer = currentBooking.getCustomer();
		LocalDate date = currentBooking.getDate().toLocalDate();
		DayOfWeek day = date.getDayOfWeek();
		boolean isMember = new MembershipDB().checkIfMember(customer, date);
		int noOfGuests = currentBooking.getNoOfGuests();
		
		if(isMember) {
			noOfGuests = Math.max(noOfGuests-2, 0);
		}
		
		double weekendPrice = 40;
		double weekDayPrice = 25;
		double pricePerPerson = weekDayPrice;
		
		if(day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
			pricePerPerson = weekendPrice;
		}
		return noOfGuests*pricePerPerson;
	}
	
	/**
	 * Check if the date is not in the past and if the time is within opening hours.
	 * @param date
	 * @return boolean True if the date is valid, false if not.
	 */
	public boolean isValidDate(LocalDateTime date) {
		return date.isAfter(LocalDateTime.now()) && isValidTime(date);
	}
	
	/**
	 * Checks if the time is within opening hours.
	 * @param date
	 * @return boolean True if the time is valid, false if not.
	 */
	public boolean isValidTime(LocalDateTime date) {
		DayOfWeek day = date.getDayOfWeek();
		LocalTime time = date.toLocalTime();
		LocalTime startTime = LocalTime.of(9, 59);
		LocalTime endTime;
		
		if(day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
			endTime = LocalTime.of(21, 01);
		} else {
			endTime = LocalTime.of(19, 01);
		}
		return time.isAfter(startTime) && time.isBefore(endTime);
	}
	
	public boolean isValidNoOfGuests(int noOfGuests) {
		return noOfGuests > 0;
	}
}
