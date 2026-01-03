package test;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.BookingDAO;
import db.TableDAO;
import model.BoardGameCopy;
import model.Booking;
import model.Category;
import model.Level;
import model.Table;

public class BookingDBStub implements BookingDAO{
	List<Table> tables = new ArrayList<>();
	
	public void addTable(Table table) {
		tables.add(table);
	}
	
	@Override
	public Booking saveBooking(Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardGameCopy> findAvailableBoardGameCopies(LocalDateTime date, String name, int noOfPlayers,
			Level level, Category category,  int duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking insertBooking(Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBoardGameBooking(Booking booking, BoardGameCopy bgCopy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Table> findAvailableTables(LocalDateTime date) {
		// TODO Auto-generated method stub
		return tables;
	}
	
}
