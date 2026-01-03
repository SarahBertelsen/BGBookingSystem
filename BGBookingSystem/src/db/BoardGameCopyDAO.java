package db;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.util.List;

import model.BoardGame;
import model.BoardGameCopy;

public interface BoardGameCopyDAO {
	
	public BoardGameCopy findCopyById(int id);
	
	public List<BoardGameCopy> findAllCopies();
	
	public BoardGameCopy buildObject(ResultSet rs);
	
	public List<BoardGameCopy> buildObjects(ResultSet rs);
	
	public void addCopiesToBoardGame(int noOfCopies, BoardGame boardgame);

	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date);
	
	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date);
	
	public void addBoardGameCopy(BoardGameCopy bgCopy);
	
}
