package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Customer;

public class MembershipDB implements MembershipDAO {
	private static final String SELECT_BY_MEMBERSHIP_Q = "SELECT * FROM Membership WHERE customerId = ? AND startDate <= ? AND endDate >= ?";

	private PreparedStatement selectMembershipPS;
	
	public MembershipDB(){
		initPreparedStatement();
	}
	
	/**
	 * Get connection to the database and prepare the statements.
	 * Throw DatabaseException if something goes wrong.
	 */
	private void initPreparedStatement() {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
		selectMembershipPS = connection.prepareStatement(SELECT_BY_MEMBERSHIP_Q);
		} catch (SQLException e){
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	/**
	 * Check if the customer is a member or not on the current date.
	 * @param customer The given customer.
	 * @param date The date of the booking.
	 * @return boolean True if the customer is member, false if not.
	 */
	@Override
	public boolean checkIfMember(Customer customer, LocalDate date) {
		boolean success = false;
		try {
			selectMembershipPS.setInt(1, customer.getCustomerId());
			selectMembershipPS.setObject(2, date);
			selectMembershipPS.setObject(3, date);
			
			ResultSet rs = selectMembershipPS.executeQuery();
			
			success = rs.next();
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.NOT_MEMBER, e);
		}
		return success;
	}
}
