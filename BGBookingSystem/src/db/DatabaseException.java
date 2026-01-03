package db;

//Unchecked exception
public class DatabaseException extends RuntimeException{
	public static final String COULD_NOT_PREPARE_STATEMENT = "The statements could not be prepared.";

	public static final String COULD_NOT_INSERT = "Could not insert the object into the database.";
	public static final String COULD_NOT_FETCH = "Could not fetch the object(s)";
	
	public static final String COULD_NOT_FETCH_CUSTOMER_NAMES = "Could not fetch the customer from the database with the given first- and lastname.";
	public static final String COULD_NOT_FETCH_CUSTOMER_ID = "Could not fetch the customer from the database with the given customer id";
	public static final String COULD_NOT_ADD_CUSTOMER = "The customer could not be added to the booking.";
	public static final String NOT_MEMBER = "The customer is not a member.";
	
	public static final String COULD_NOT_FETCH_TABLE = "Could not fetch the matching table from the database";
	public static final String COULD_NOT_FETCH_TABLES = "Could not fetch the list of tables from the database";
	
	public static final String COULD_NOT_FETCH_BOARDGAMECOPY = "Could not fetch the board game copy from the database with the board game copy id";
	public static final String COULD_NOT_FETCH_BOARDGAMECOPIES = "Could not fetch the board game copies from the database";
	public static final String COULD_NOT_ADD_BOARDGAMECOPIES = "Could not add the board game copies to the database";
	
	public static final String COULD_NOT_FETCH_BOARDGAME = "Could not fetch the board game from the database with the board game id";
	public static final String COULD_NOT_FETCH_BOARDGAMES = "Could not fetch the board games from the database";
	public static final String COULD_ADD_BOARDGAME = "Could not add the board game to the database";
	public static final String NO_BOARDGAMEID_FOUND = "Could not find board game id";
	
	public static final String COULD_NOT_BUILD_OBJECT = "Could not build the object from the ResultSet";
	public static final String COULD_NOT_BUILD_OBJECTS = "Could not build the list of objects from the ResultSet";
	
	public static final String COULD_NOT_UPDATE_RESERVATION = "Could not update the reservation status of the board game copy.";
	
	public static final String COULD_NOT_UPDATE_BUFFER = "Could not update buffer...";

	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(String message, Throwable clause) {
		super(message, clause);
	}

}
