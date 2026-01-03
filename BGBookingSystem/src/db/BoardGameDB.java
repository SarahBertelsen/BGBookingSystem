package db;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.BoardGame;
import model.Category;
import model.Level;

public class BoardGameDB implements BoardGameDAO {
	private static final String FIND_BY_ID_Q = "SELECT * FROM BoardGame WHERE boardGameId = ?";
	private static final String FIND_ALL_Q = "SELECT * FROM BoardGame";
	private static final String CREATE_BOARDGAME_Q = "INSERT INTO BoardGame (name, level, noOfPlayers, category, duration, description) VALUES (?, ?, ?, ?, ?, ?)";

	private PreparedStatement findByIdPS;
	private PreparedStatement findAllPS;
	private PreparedStatement insertBgPS;

	public BoardGameDB() {
		initPreparedStatements();
	}

	/**
	 * Get connection to the database and prepare the statements.
	 * Throw DatabaseException if something goes wrong.
	 */
	public void initPreparedStatements() {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			findByIdPS = connection.prepareStatement(FIND_BY_ID_Q);
			findAllPS = connection.prepareStatement(FIND_ALL_Q);
			insertBgPS = connection.prepareStatement(CREATE_BOARDGAME_Q,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * Find the BoardGames matching the given id. Uses the initialized Prepared
	 * Statements to find the BoardGame in the database, sets it in a ResultSet and
	 * uses the buildObject() method to build an instance of the BoardGame.
	 * 
	 * @param gameId The id used to search for the BoardGame.
	 * @return BoardGame The BoardGame matching the given id.
	 */
	@Override
	public BoardGame findBoardGameById(int gameId) {
		BoardGame game = null;
		try {
			findByIdPS.setInt(1, gameId);
			ResultSet rs = findByIdPS.executeQuery();
			if (rs.next()) {
				game = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_BOARDGAME, e);
		}
		return game;
	}

	/**
	 * Find all the BoardGame objects in the database. Uses the buildObjects()
	 * method to create an instance of the list. Uses the initialized Prepared
	 * Statements to find the BoardGames in the database, sets it in a ResultSet and
	 * uses the buildObjects() method to build an instance of List<BoardGame>.
	 * 
	 * @return List<BoardGame> The list of all the BoardGame in the database.
	 *         Returns an empty list if not succeeded.
	 */
	@Override
	public List<BoardGame> findAllBoardGames() {
		ResultSet rs;
		try {
			rs = findAllPS.executeQuery();
			List<BoardGame> result = buildObjects(rs);
			return result;

		} catch(SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_FETCH_BOARDGAMES, e);
		}
	}

	/**
	 * Build an instance of BoardGame based on a ResultSet from the database. Get
	 * the parameters from the ResultSet and set them in local variables used to
	 * build the object.
	 * 
	 * @param rs The ResultSet of BoardGame from the database.
	 * @return BoardGame The BoardGame that is built from the ResultSet.
	 */
	@Override
	public BoardGame buildObject(ResultSet rs) {
		BoardGame game = null;

		try {
			int boardGameId = rs.getInt(1);
			String name = rs.getString(2);
			int levelInt = rs.getInt(3);
			Level level = Level.fromInt(levelInt);
			int noOfPlayers = rs.getInt(4);
			String categoryString = rs.getString(5);
			Category category = Category.valueOf(categoryString);
			int duration = rs.getInt(6);
			String description = rs.getString(7);

			game = new BoardGame(boardGameId, name, level, noOfPlayers, category, duration, description);
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_NOT_BUILD_OBJECT, e);
		}
		return game;
	}

	/**
	 * Build an instance of List<BoardGame> based on a ResultSet from the database.
	 * Uses the buildObject() method to build an instance and adds it to the list.
	 * 
	 * @param rs The ResultSet of BoardGame from the database.
	 * @return List<BoardGame> The list of the created instances of BoardGame.
	 */
	@Override
	public List<BoardGame> buildObjects(ResultSet rs) {
		List<BoardGame> result = new ArrayList<>();
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
	 * Create a new BoardGame in the database.
	 * Uses the insertBgPS PreparedStatement to set the columns in the BoardGame table.
	 * 
	 * @param name The name of the BoardGame.
	 * @param level The Level of the BoardGame.
	 * @param noOfPlayers The number of players needed to play the game.
	 * @param category The Category of the BoardGame.
	 * @param durationInMinutes The duration of the BoardGame in minutes.
	 * @param description The description of the BoardGame.
	 * @return int The boardGameId
	 */
	@Override
	public int createBoardGame(String name, Level level, int noOfPlayers, Category category, int durationInMinutes,
			String description) throws SQLException {
		try {
			insertBgPS.setString(1, name);
			insertBgPS.setInt(2, level.getValue());
			insertBgPS.setInt(3, noOfPlayers);
			insertBgPS.setString(4, category.toSqlString());
			insertBgPS.setInt(5, durationInMinutes);
			insertBgPS.setString(6, description);

			insertBgPS.executeUpdate();
			try (ResultSet rs = insertBgPS.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            } else {
	                throw new DatabaseException(DatabaseException.NO_BOARDGAMEID_FOUND);
	            }
			}
		} catch (SQLException e) {
			throw new DatabaseException(DatabaseException.COULD_ADD_BOARDGAME, e);
		}
	}

	/**
	 * Create a BoardGame and add it to the database.
	 * 
	 * @param bg The BoardGame
	 */
	@Override
	public void createBoardGame(BoardGame bg) throws SQLException {
		String name = bg.getName();
		Level level = bg.getLevel();
		int noOfPlayers = bg.getNoOfPlayers();
		Category category = bg.getCategory();
		int durationInMinutes = bg.getDurationInMinutes();
		String description = bg.getDescription();
		
		int generatedId = createBoardGame(name, level, noOfPlayers, category, durationInMinutes, description);
		bg.setBoardGameId(generatedId);
	}
}
