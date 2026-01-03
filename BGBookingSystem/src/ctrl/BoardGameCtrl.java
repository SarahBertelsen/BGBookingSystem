package ctrl;

import java.sql.SQLException;

import db.BoardGameCopyDAO;
import db.BoardGameDAO;
import db.DBConnection;
import model.BoardGame;
import model.Category;
import model.Level;

public class BoardGameCtrl implements BoardGameCtrlIF {

	private BoardGame currentBoardGame;
	private BoardGameCopyDAO bgcopyDAO;
	private BoardGameDAO bgDAO;
	private DBConnection dbConnection;
//	

	// initialize DAOs + connection to database
	public BoardGameCtrl(BoardGameCopyDAO bgcopyDAO, BoardGameDAO bgDAO) {
		this.bgcopyDAO = bgcopyDAO;
		this.bgDAO = bgDAO;
		dbConnection = DBConnection.getInstance();

	}

//	public BoardGame saveBoardGame (BoardGame boardGame) {
//	DBConnection db = DBConnection.getInstance();
//	db.startTransaction();
//}
//
//try { 
//	BoardGame boardGame = addCopiesToBoardGame(boardGame);
//	
//}
//

	// creates a connection to database + start transaction
	// Trys to create boardgame in the database and attach a id
	// If that works then the transaction is completed and saved + boardGame with id
	// gets returned
	// If that fails then we go back to the beginning of the process and try again

	public int createBoardGameWithCopies(String name, Level level, int noOfPlayers, Category category,
			int durationInMinutes, String description, int noOfCopies) throws SQLException {
		DBConnection db = DBConnection.getInstance();
		db.startTransaction();

		try {
			int boardGameId = bgDAO.createBoardGame(name, level, noOfPlayers, category, durationInMinutes, description);

			BoardGame bg = new BoardGame(boardGameId, name, level, noOfPlayers, category, durationInMinutes,
					description);
			bg.setBoardGameId(boardGameId);

			bgcopyDAO.addCopiesToBoardGame(noOfCopies, bg);

			db.commitTransaction();

			return boardGameId;

		} catch (SQLException e) {
			if (db != null) {
				db.rollBackTransaction();
			}
			throw e;
		}
	}
}
