package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.BoardGame;
import model.Category;
import model.Level;


public interface BoardGameDAO {
	
	public BoardGame findBoardGameById(int gameId);
	
	public List<BoardGame> findAllBoardGames();
	
	public BoardGame buildObject(ResultSet rs);
	
	public List<BoardGame> buildObjects(ResultSet rs);
	
	public int createBoardGame(String name, Level level, int noOfPlayers, Category category, int durationInMinutes,
			String description) throws SQLException;

	public void createBoardGame(BoardGame bg) throws SQLException;


}
