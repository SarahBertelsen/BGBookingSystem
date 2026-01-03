package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ctrl.CustomerCtrl;
import model.BoardGameCopy;
import model.Booking;
import model.Category;
import model.Customer;
import model.Level;
import model.Table;

public class BookingDB implements BookingDAO {
	private static final String INSERT_INTO_BOOKING_Q = "INSERT INTO Booking (customerId, noOfGuests, date, tableNo) VALUES (?,?,?,?)";
	private static final String INSERT_INTO_BOARDGAMEBOOKING_Q = "INSERT INTO BoardGameBooking (bookingId, boardGameCopyId) VALUES (?, ?)";
	private static final String SELECT_TABLE_BY_SEATS_AND_DATE_Q = """
			SELECT * FROM [Table]
			WHERE tableNo NOT IN(
			SELECT [Table].tableNo FROM [Table]
			JOIN Booking ON Booking.tableNo = [Table].tableNo
			WHERE DATEADD(HOUR, -3, Booking.date) <= ? AND ? <= DATEADD(HOUR, 3, Booking.date))
			""";

	static private final String GET_AVAILABLE_COPIES_START = """
			SELECT * FROM BoardGameCopy
			JOIN BoardGame ON BoardGame.boardGameId = boardGameCopy.BoardGameId
			WHERE
			""";

	static private final String GET_AVAILABLE_COPIES_END = """
			BoardGameCopyId NOT IN(
			SELECT boardGameCopyId FROM Booking
			JOIN BoardGameBooking ON Booking.bookingId = BoardGameBooking.bookingId
			WHERE DATEADD(HOUR, -3, Booking.date) <= ? AND ? <= DATEADD(HOUR, 3, Booking.date)
			)
			AND boardGameCopyId NOT IN(
			SELECT boardGameCopyId FROM BoardGameCopyReservation
			WHERE DATEADD(MINUTE, -10, BoardGameCopyReservation.date) <= ? AND ? <= DATEADD(MINUTE, 10, BoardGameCopyReservation.date)
			)
			""";

	static private final String NAME_Q = " [name] LIKE ? AND ";
	static private final String NO_OF_PLAYERS_Q = " noOfPlayers >= ? AND ";
	static private final String LEVEL_Q = " level = ? AND ";
	static private final String DURATION_Q = " duration <= ? AND ";
	static private final String CATEGORY_Q = " category LIKE ? AND ";

	private BoardGameCopyDAO bGCDAO;
	private TableDAO tableDAO;
	private CustomerDAO customerDao;
	private MembershipDAO membershipDao;

	private PreparedStatement selectTableBySeatsAndDatePS;
	private PreparedStatement insertBookingPS;
	private PreparedStatement insertBoardGameBookingPS;

	public BookingDB(BoardGameCopyDAO bGCDAO, TableDAO tableDAO, CustomerDAO customerDao, MembershipDAO membershipDao) {
		this.bGCDAO = bGCDAO;
		this.tableDAO = tableDAO;
		this.customerDao = customerDao;
		this.membershipDao = membershipDao;
		initPreparedStatements();
	}

	/**
	 * Get connection to the database and prepare the statements.
	 * Throw DatabaseException if something goes wrong.
	 */
	public void initPreparedStatements() {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			selectTableBySeatsAndDatePS = connection.prepareStatement(SELECT_TABLE_BY_SEATS_AND_DATE_Q);
			insertBookingPS = connection.prepareStatement(INSERT_INTO_BOOKING_Q, Statement.RETURN_GENERATED_KEYS);
			insertBoardGameBookingPS = connection.prepareStatement(INSERT_INTO_BOARDGAMEBOOKING_Q);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * Save the current booking in the database.
	 * 
	 * @param booking The current booking.
	 * @return Booking The current booking with the bookingId.
	 */
	@Override
	public Booking insertBooking(Booking booking) {
		Timestamp timestamp = Timestamp.valueOf(booking.getDate());
		
		int noOfGuests = booking.getNoOfGuests();
		int customerId = booking.getCustomer().getCustomerId();
		int tableNo = booking.getTable().getTableNo();

		try {
			insertBookingPS.setInt(1, customerId);
			insertBookingPS.setInt(2, noOfGuests);
			insertBookingPS.setTimestamp(3, timestamp);
			insertBookingPS.setInt(4, tableNo);
			insertBookingPS.executeUpdate();

			ResultSet bookingId = insertBookingPS.getGeneratedKeys();
			if (bookingId.next()) {
				booking.setBookingId(bookingId.getInt(1));
			}

		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_INSERT, e);
		}
		return booking;
	}

	/**
	 * Inserts the booking into the Booking table, the BoardGameCopies related to
	 * the booking in the BoardGameBooking table and the Customer to the Customer
	 * table (if it does not already exist). We use transaction to make sure all
	 * steps are successful before committing the transaction to assure ACID.
	 * 
	 * @param Booking The current booking
	 * @return booking The current booking
	 */
	public Booking saveBooking(Booking booking) {
		DBConnection db = DBConnection.getInstance();
		
		db.startTransaction();
		
		try {
			Customer customer = booking.getCustomer();
			new CustomerCtrl(customerDao, membershipDao).saveCustomer(customer);
			booking = insertBooking(booking);
			for (BoardGameCopy bgCopy : booking.getBoardGameCopies()) {
				insertBoardGameBooking(booking, bgCopy);
			}
			
			DBConnection.getInstance().commitTransaction();

		} catch (DatabaseException e) {
			db.rollBackTransaction();
			e.printStackTrace();
			throw new TransactionException(TransactionException.COULD_NOT_COMPLETE, e);

		} catch (Exception e) {
			// Catch anything unexpected
			db.rollBackTransaction();
			throw new TransactionException(TransactionException.UNEXPECTED_ERROR, e);
		}
		
		return booking;
	}

	/**
	 * Save the booking in the BoardGameBooking table. Uses a for-each loop to
	 * iterate over each copy, getting the id and setting it in relation to the
	 * bookingId.
	 * 
	 * @param booking The current booking.
	 */
	public void insertBoardGameBooking(Booking booking, BoardGameCopy bgCopy) {
		int bookingId = booking.getBookingId();
		int copyId = bgCopy.getCopyId();

		try {
			insertBoardGameBookingPS.setInt(1, bookingId);
			insertBoardGameBookingPS.setInt(2, copyId);
			insertBoardGameBookingPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_INSERT, e);
		}
	}

	/**
	 * Find the BoardGameCopies that are available on the given date.
	 * 
	 * @param date The date of the booking.
	 * @return List<BoardGameCopy> A list of the available BoardGameCopy.
	 */
	@Override
	public List<BoardGameCopy> findAvailableBoardGameCopies(LocalDateTime date, String name, int noOfPlayers,
			Level level, Category category, int duration) {
		List<BoardGameCopy> availableBoardGames = null;
		ArrayList<Object> parameters = new ArrayList<>();

		String queryString = GET_AVAILABLE_COPIES_START;
		if (name != null) {
			queryString += NAME_Q;
			parameters.add("%" + name + "%");
		}
		if (noOfPlayers > 0) {
			queryString += NO_OF_PLAYERS_Q;
			parameters.add(noOfPlayers);
		}
		if (level != null && level != Level.NONE) {
			queryString += LEVEL_Q;
			parameters.add(level.getValue());
		}
		if (duration > 0) {
			queryString += DURATION_Q;
			parameters.add(duration);
		}
		if (category != null && category != Category.NONE) {
			queryString += CATEGORY_Q;
			parameters.add(category.toSqlString());
		}

		queryString += GET_AVAILABLE_COPIES_END;

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			PreparedStatement pS = connection.prepareStatement(queryString);
			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof Integer) {
					pS.setInt(i + 1, (int) parameters.get(i));
				} else if (parameters.get(i) instanceof String) {
					pS.setString(i + 1, (String) parameters.get(i));
				}
			}

			Timestamp timestamp = Timestamp.valueOf(date);

			pS.setTimestamp(parameters.size() + 1, timestamp);
			pS.setTimestamp(parameters.size() + 2, timestamp);
			pS.setTimestamp(parameters.size() + 3, timestamp);
			pS.setTimestamp(parameters.size() + 4, timestamp);
			

			ResultSet resultSet = pS.executeQuery();
			availableBoardGames = new ArrayList<BoardGameCopy>();
			while (resultSet.next()) {
				availableBoardGames.add(bGCDAO.buildObject(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_BOARDGAMECOPIES, e);
		}
		
		return availableBoardGames;
	}

	/**
	 * Find the Tables that are available on the given date.
	 * 
	 * @param date The date of the booking.
	 * @return List
	 *         <Table>
	 *         A list of the available Table.
	 */
	
	@Override
	public List<Table> findAvailableTables(LocalDateTime date) {
		List<Table> availableTables = new ArrayList<>();
		Timestamp timestamp = Timestamp.valueOf(date);
		
		try {
			selectTableBySeatsAndDatePS.setTimestamp(1, timestamp);
			selectTableBySeatsAndDatePS.setTimestamp(2, timestamp);

			ResultSet resultSet = selectTableBySeatsAndDatePS.executeQuery();

			while (resultSet.next()) {
				availableTables.add(tableDAO.buildObject(resultSet));
			}

		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_TABLES, e);
		}

		return availableTables;
	}

}
