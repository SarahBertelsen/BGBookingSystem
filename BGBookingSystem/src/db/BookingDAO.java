package db;

import java.time.LocalDateTime;
import java.util.List;

import model.BoardGameCopy;
import model.Booking;
import model.Category;
import model.Level;
import model.Table;

public interface BookingDAO {

	public Booking saveBooking(Booking booking);
	
	public Booking insertBooking(Booking booking);
	
	List<BoardGameCopy> findAvailableBoardGameCopies(LocalDateTime date, String name, int noOfPlayers, Level level, Category category,
			int duration);

	public void insertBoardGameBooking(Booking booking, BoardGameCopy bgCopy);

	List<Table> findAvailableTables(LocalDateTime date);
	
}
