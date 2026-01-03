package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Table;

public class TableDB implements TableDAO {

	private static final String SELECT_BY_TABLE_NO_Q = "SELECT * FROM Table WHERE CustomerId = ?";

	private PreparedStatement selectByNoPS;

	public TableDB() {
		initPreparedStatements();
	}
		
	/**
	 * Get connection to the database and prepare the statements.
	 * Throw DatabaseException if something goes wrong.
	 */
	public void initPreparedStatements() {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			selectByNoPS = connection.prepareStatement(SELECT_BY_TABLE_NO_Q);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * Find a Table from the given table number. Uses the initialized Prepared
	 * Statements to find the Table in the database, sets it in a ResultSet
	 * and uses the buildObject() method to build an instance of the Table.
	 * @param tableNo The number used to search for the table.
	 * @return Table The table that matches the number.
	 */
	@Override
	public Table findTableByNo(int tableNo) {
		Table table = null;
		try {
			selectByNoPS.setInt(1, tableNo);
			ResultSet rs = selectByNoPS.executeQuery();
			if(rs.next()) {
				table = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_TABLE, e);
		}
		return table;
	}

	/**
	 * Build an instance of Table based on a ResultSet from the database.
	 * @param rs The ResultSet of Table from the database.
	 * @return Table The Table that is built from the ResultSet.
	 */
	@Override
	public Table buildObject(ResultSet rs) {
		Table table = null;
		try {
			int tableNo = rs.getInt(1);
			int noOfSeats = rs.getInt(2);
			
			table = new Table(tableNo, noOfSeats);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_BUILD_OBJECT, e);
		}
		return table;
	}

}
