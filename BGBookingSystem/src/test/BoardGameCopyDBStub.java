package test;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.BoardGameCopyDAO;
import model.BoardGame;
import model.BoardGameCopy;

public class BoardGameCopyDBStub implements BoardGameCopyDAO{
	List<BoardGameCopy> bgCopies = new ArrayList<>();
	
	public BoardGameCopyDBStub() {
		
	}

	public void addBoardGameCopy(BoardGameCopy bgCopy) {
		bgCopies.add(bgCopy);
	}
	
	@Override
	public BoardGameCopy findCopyById(int id) {
		return null;
	}

	@Override
	public List<BoardGameCopy> findAllCopies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardGameCopy buildObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardGameCopy> buildObjects(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCopiesToBoardGame(int noOfCopies, BoardGame boardgame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		// TODO Auto-generated method stub
		
	}

}
