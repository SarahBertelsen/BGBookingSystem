package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Customer;
import model.CustomerSaveStatus;

public class CustomerDB implements CustomerDAO {

	private static final String SELECT_CUSTOMER_BY_DETAILS_Q = "SELECT * FROM Customer WHERE fName = ? AND lName = ? AND phone = ?";
	private static final String SELECT_CUSTOMER_BY_ID_Q = "SELECT * FROM Customer WHERE customerId = ?";
	private static final String INSERT_CUSTOMER_Q = "INSERT INTO Customer (fName, lName, phone) VALUES (?,?,?)";

	private PreparedStatement selectCustomerByDetailsPS;
	private PreparedStatement selectCustomerByIdPS;
	private PreparedStatement insertCustomerPS;

	public CustomerDB() {
		initPreparedStatements();
	}
	
	/**
	 * Get connection to the database and prepare the statements.
	 * Throw DatabaseException if something goes wrong.
	 */
	public void initPreparedStatements() {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			selectCustomerByDetailsPS = connection.prepareStatement(SELECT_CUSTOMER_BY_DETAILS_Q);
			selectCustomerByIdPS = connection.prepareStatement(SELECT_CUSTOMER_BY_ID_Q);
			insertCustomerPS = connection.prepareStatement(INSERT_CUSTOMER_Q, PreparedStatement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * Find the Customer in the database.
	 * 
	 * @param fName The first name of the Customer.
	 * @param lName The last name of the Customer.
	 * @param phone The phone number of the Customer.
	 * @return Customer The Customer found in the database.
	 */
	@Override
	public Customer findCustomer(String fName, String lName, String phone) {
		Customer customer = null;
		ResultSet rs;

		try {
			selectCustomerByDetailsPS.setString(1, fName);
			selectCustomerByDetailsPS.setString(2, lName);
			selectCustomerByDetailsPS.setString(3, phone);
			rs = selectCustomerByDetailsPS.executeQuery();
			if (rs.next()) {
				customer = buildObject(rs);
			}
		} catch (SQLException e) {
		    throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_CUSTOMER_NAMES, e);
		}
		return customer;
	}

	/**
	 * Find the Customer in the database.
	 * 
	 * @param Customer The Customer searched for.
	 * @return Customer The matching Customer found in the database.
	 */
	@Override
	public Customer findCustomer(Customer customer) {
		Customer c = null;
		int customerId = customer.getCustomerId();
		ResultSet rs;

		try {
			selectCustomerByIdPS.setInt(1, customerId);
			rs = selectCustomerByIdPS.executeQuery();
			if (rs.next()) {
				c = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_CUSTOMER_ID, e);
		}
		return c;
	}

	/**
	 * Check if the customer exists in database, if not, the new customer is saved
	 * in the database.
	 * 
	 * @param customer The customer that is creating the current booking.
	 * @return CustomerSaveStatus The status - either the customer is saved, or the customer already exists.
	 */
	@Override
	public void saveCustomer(Customer customer) throws TransactionException{
		if (customer.getStatus() == CustomerSaveStatus.CUSTOMER_DOES_NOT_EXIST) {
			String fName = customer.getfName();
			String lName = customer.getlName();
			String phone = customer.getPhone();
			try {
				insertCustomerPS.setString(1, fName);
				insertCustomerPS.setString(2, lName);
				insertCustomerPS.setString(3, phone);
				insertCustomerPS.executeUpdate();
				
				ResultSet customerId = insertCustomerPS.getGeneratedKeys();
				if (customerId.next()) {
					customer.setCustomerId(customerId.getInt(1));
					customer.setStatus(CustomerSaveStatus.CUSTOMER_ALREADY_EXISTS);
				}
				
			} catch (SQLException e) {
				throw new TransactionException(DatabaseException.COULD_NOT_INSERT, e);
			}
		}
	}

	/**
	 * Build an instance of Customer based on a ResultSet from the database.
	 * 
	 * @param rs The ResultSet of Customer from the database.
	 * @return Customer The Customer that is built from the ResultSet.
	 */
	@Override
	public Customer buildObject(ResultSet rs) {
		Customer customer = null;
		try {
			int customerId = rs.getInt("customerId");
			String fName = rs.getString("fName");
			String lName = rs.getString("lName");
			String phone = rs.getString("phone");

			customer = new Customer(customerId, fName, lName, phone);

		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_BUILD_OBJECT, e);
		}
		return customer;
	}

}
