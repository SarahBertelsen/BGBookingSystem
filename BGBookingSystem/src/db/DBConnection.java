package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection connection = null;
	private static DBConnection dbConnection;

	
		private static final String DBNAME = "DMA-CSD-V251_10665994";
		private static final String SERVERNAME = "hildur.ucn.dk";
		private static final String PORTNUMBER = "1433";
		private static final String USERNAME = "DMA-CSD-V251_10665994";
		private static final String PASSWORD = "Password1!";

//	// local login
//	private static final String DBNAME = "2ndSemester";
//	private static final String SERVERNAME = "BIGLSBIGDEVICE"; // replace with your device name
//	private static final String PORTNUMBER = "1433";
//	private static final String USERNAME = "sa";
//	private static final String PASSWORD = "secret2025*";

	// constructor - private because of singleton pattern
	private DBConnection() {
		String urlString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false;sendTimeAsDateTime=false", SERVERNAME, PORTNUMBER,
				DBNAME);
		try {
			connection = DriverManager.getConnection(urlString, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new DatabaseException("Could not establish database connection", e);
		}
	}

	/**
	 * Creates an instance of DBConnection. Checks whether or not an instance
	 * already exists. If not, a new instance is created and returned. If one exists,
	 * it is returned.
	 * Method is synchronized to ensure that only one thread at a time can access the method.
	 * 
	 * @return DBConnection The instance of DBConnection found or created.
	 */
	public static synchronized DBConnection getInstance()  {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}

	/**
	 * Sets autocommit to false while a transaction is running.
	 */
	public void startTransaction()  {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new TransactionException("Could not start transaction", e);
		}
	}

	/**
	 * Commits the changes made in the transaction and sets autocommit to true.
	 */
	public void commitTransaction() {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new TransactionException("Could not commit transaction", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new TransactionException("Could not reset autocommit after commit", e);
			}
		}
	}

	/**
	 * Rolls back the transaction if something goes wrong. Sets autocommit to true.
	 */
	public void rollBackTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new TransactionException("Could not roll back transaction", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new TransactionException("Could not reset autocommit after rollback", e);
			}
		}
	}

	/**
	 * Return the connection.
	 * @return Connection The Connection to the database.
	 */
	public Connection getConnection() {
		return connection;
	}

}
