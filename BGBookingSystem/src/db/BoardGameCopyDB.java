package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.BoardGame;
import model.BoardGameCopy;

public class BoardGameCopyDB implements BoardGameCopyDAO {
	private static final String FIND_BY_ID_Q = "SELECT * FROM BoardGameCopy WHERE boardGameId = ?";
	private static final String FIND_ALL_Q = "SELECT * FROM BoardGameCopy";
	private static final String INSERT_COPY_Q = "INSERT INTO BoardGameCopy (boardGameId) VALUES (?)";
	private static final String INSERT_RESERVATION_Q = "INSERT INTO BoardGameCopyReservation VALUES (?, ?)";
	private static final String DELETE_RESERVATION_Q = """
						DELETE FROM BoardGameCopyReservation
			WHERE boardGameCopyId = ? AND date <= ? AND ? <= DATEADD(MINUTE, 10, date)
						""";

	private PreparedStatement findByIdPS;
	private PreparedStatement findAllPS;
	private PreparedStatement insertCopyPS;
	private PreparedStatement insertReservationPS;
	private PreparedStatement deleteReservationPS;

	public BoardGameCopyDB() {
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
			findByIdPS = connection.prepareStatement(FIND_BY_ID_Q);
			findAllPS = connection.prepareStatement(FIND_ALL_Q);
			insertCopyPS = connection.prepareStatement(INSERT_COPY_Q, Statement.RETURN_GENERATED_KEYS);
			insertReservationPS = connection.prepareStatement(INSERT_RESERVATION_Q);
			deleteReservationPS = connection.prepareStatement(DELETE_RESERVATION_Q);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * Find a BoardGameCopy from the given id. Uses the initialized Prepared
	 * Statements to find the BoardGameCopy in the database, sets it in a ResultSet
	 * and uses the buildObject() method to build an instance of the BoardGameCopy.
	 * 
	 * @param id The id used to search for the BoardGameCopy.
	 * @return BoardGameCopy The copy that matches the given id.
	 */
	@Override
	public BoardGameCopy findCopyById(int id) {
		BoardGameCopy bgCopy = null;
		try {
			findByIdPS.setInt(1, id);
			ResultSet rs = findByIdPS.executeQuery();
			if (rs.next()) {
				bgCopy = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_BOARDGAMECOPY, e);
		}
		return bgCopy;
	}

	/**
	 * Find all the BoardGameCopy objects in the database. Uses the buildObjects()
	 * method to create an instance of the list. Uses the initialized Prepared
	 * Statements to find the BoardGameCopies in the database, sets it in a
	 * ResultSet and uses the buildObjects() method to build an instance of
	 * List<BoardGameCopy>.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @return List<BoardGameCopy> The list of all the BoardGame in the database.
	 */
	@Override
	public List<BoardGameCopy> findAllCopies() {
		ResultSet rs;
		try {
			rs = findAllPS.executeQuery();
			List<BoardGameCopy> result = buildObjects(rs);
			return result;
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_BOARDGAMECOPIES, e);
		}
	}

	/**
	 * Build an instance of BoardGameCopy based on a ResultSet from the database.
	 * Get the parameters from the ResultSet and set them in local variables used to
	 * build the object.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @param rs The ResultSet of BoardGameCopy from the database.
	 */
	@Override
	public BoardGameCopy buildObject(ResultSet rs) {
		BoardGameCopy bgCopy = null;
		try {
			int boardGameCopyId = rs.getInt(1);
			int boardGameId = rs.getInt(2);

			BoardGameDB bgDb = new BoardGameDB();
			BoardGame game = bgDb.findBoardGameById(boardGameId);
			bgCopy = new BoardGameCopy(boardGameCopyId, game);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_BUILD_OBJECT, e);
		}
		return bgCopy;
	}

	/**
	 * Build an instance of List<BoardGameCopy> based on a ResultSet from the
	 * database. Uses the buildObject() method to build an instance and adds it to
	 * the list.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @param rs The ResultSet of BoardGameCopy from the database.
	 * @return List<BoardGameCopy> The list of the created instances of
	 *         BoardGameCopy.
	 */
	@Override
	public List<BoardGameCopy> buildObjects(ResultSet rs) {
		List<BoardGameCopy> result = new ArrayList<>();
		try {
			while (rs.next()) {
				result.add(buildObject(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_BUILD_OBJECTS, e);
		}
		return result;
	}

	/**
	 * Update the column isReserved in the BoardGameCopy database, when a
	 * BoardGameCopy is reserved in the GUI.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @param bgCopy     The BoardGameCopy that is reserved.
	 * @param isReserved True if the status must be changed to true (isReserved), or
	 *                   false if it must be changed to false.
	 */
	@Override
	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		int copyId = bgCopy.getCopyId();
		Timestamp timestamp = Timestamp.valueOf(date);
		try {
			insertReservationPS.setInt(1, copyId);
			insertReservationPS.setTimestamp(2, timestamp);

			int rowsUpdated = insertReservationPS.executeUpdate();

			if (rowsUpdated == 0) {
				throw new DatabaseException(DatabaseException.COULD_NOT_UPDATE_RESERVATION);
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_UPDATE_RESERVATION, e);
		}
	}

	/**
	 * Remove the reservation of the BoardGameCopy and update the column isReserved in the 
	 * BoardGameCopy database, when a BoardGameCopy is reserved in the GUI.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @param bgCopy The BoardGameCopy that is unreserved.
	 * @param date The date and time of the reservation being removed.
	 */
	@Override
	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		int copyId = bgCopy.getCopyId();
		Timestamp timestamp = Timestamp.valueOf(date);
		try {
			deleteReservationPS.setInt(1, copyId);
			deleteReservationPS.setTimestamp(2, timestamp);
			deleteReservationPS.setTimestamp(3, timestamp);

			deleteReservationPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_UPDATE_RESERVATION, e);
		}

	}

	/**
	 * Add the BoardGameCopy to the database.
	 * 
	 * @param noOfCopies The number of BoardGameCopy that is being inserted.
	 * @param boardGame The BoardGame, the copies belong to.
	 */
	@Override
	public void addCopiesToBoardGame(int noOfCopies, BoardGame boardGame) {
		BoardGameCopy bgCopy = new BoardGameCopy(boardGame);
		for (int i = 0; i < noOfCopies; i++) {
			addBoardGameCopy(bgCopy);
		}
	}
	
	/**
	 * Add the BoardGameCopy to the database.
	 * Finds the BoardGame, the copyId belongs to and updates the BoardGame with the
	 * number of copies added in the database.
	 * Generates a copyId for each copy added.
	 * Throw DatabaseException if something goes wrong.
	 * 
	 * @param boardGameCopy The copy being added to the database.
	 */
	@Override
	public void addBoardGameCopy(BoardGameCopy boardGameCopy) {
		int boardGameId = boardGameCopy.getBoardGame().getBoardGameId();
		try {
			insertCopyPS.setInt(1, boardGameId);
			insertCopyPS.executeUpdate();
			
			ResultSet rs = insertCopyPS.getGeneratedKeys();
			
			if(rs.next()) {
				int generatedCopyId = rs.getInt(1);
				boardGameCopy.setCopyId(generatedCopyId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(DatabaseException.COULD_NOT_ADD_BOARDGAMECOPIES, e);
		}
	}

}
