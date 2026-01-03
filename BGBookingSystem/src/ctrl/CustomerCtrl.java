package ctrl;

import java.time.LocalDate;

import java.util.regex.Pattern;

import javax.swing.JFrame;

import db.CustomerDAO;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.TransactionException;
import model.Customer;

public class CustomerCtrl implements CustomerCtrlIF {

	private JFrame customerFrameGUI;
	private CustomerDAO customerDao;
	private MembershipDAO membershipDao;
	private static final String PHONE_PATTERN = "^\\+45[0-9]{8}+$";
	private static final String NAME_PATTERN = "^[A-Z]+[a-z]+$";
	
	// Initialize DAOSs
	public CustomerCtrl(CustomerDAO customerDao, MembershipDAO membershipDao) {
		this.customerDao = customerDao;
		this.membershipDao = membershipDao;
	}
	
//	public CustomerCtrl(CustomerDAO customerDao, JFrame customerFrameGUI) {
//		this.customerDao = customerDao;
//		this.customerFrameGUI = customerFrameGUI;
//	}
	
	/**
	 * Find the customer, if it exists in the database. Otherwise, a new Customer is
	 * created.
	 * 
	 * @param fName The customer's first name.
	 * @param lName The customer's last name.
	 * @param phone The customer's telephone number.
	 * @return Customer The customer that is found or created.
	 */
	@Override
	public Customer findOrCreateCustomer(String fName, String lName, String phone) {
		if(!isValidName(fName, lName)) {
			throw new InvalidFormatException(InvalidFormatException.INVALID_NAME);}
		if(!isValidNumber(phone)) {
			throw new InvalidFormatException(InvalidFormatException.INVALID_NUMBER);}
		
		Customer customer = customerDao.findCustomer(fName, lName, phone);
		if (customer == null) {
			customer = new Customer(fName, lName, phone);
		}
		return customer;
	}

	/**
	 * Save the customer that has been created to the Booking object.
	 * 
	 * @param customer The customer that is saved.
	 * @return CustomerSaveStatus Return whether a new customer was saved to the database
	 * or it already exists in the database.
	 */
	@Override
	public void saveCustomer(Customer customer) throws TransactionException{
		customerDao.saveCustomer(customer);
	}

	/**
	 * Checks whether or not the customer has an active membership on the current
	 * date.
	 * 
	 * @param customer The customer that is creating the booking.
	 * @param date     The current date when the booking is created.
	 */
	@Override
	public boolean checkIfMember(Customer customer, LocalDate date) {
		return membershipDao.checkIfMember(customer, date);
	}
	
	// checks if the given phone number matches +45 followed by 8 digits
	public static boolean isValidNumber(String phone) {
		if (Pattern.matches(PHONE_PATTERN, phone)) {
			return true;
		}
		return false;
	}
	
	// checks if the given first and last name starts with a capital letter and only contains letters from the english alphabet
	public static boolean isValidName(String fName, String lName) {
		if (Pattern.matches(NAME_PATTERN, fName) && Pattern.matches(NAME_PATTERN, lName)) {
			return true;
		} else {
			System.out.println(fName + " " + lName + " The name is not valid. "
					+ "Make sure that both your first name and last name start with an upper case letter. Cannot contain digits.");
		}
		return false;
	}

}
