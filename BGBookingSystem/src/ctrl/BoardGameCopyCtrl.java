package ctrl;

import model.BoardGameCopy;
import model.Category;
import model.Level;

import java.time.LocalDateTime;
import java.util.List;

import db.BoardGameCopyDAO;
import db.BookingDAO;

public class BoardGameCopyCtrl implements BoardGameCopyCtrlIF {
	private BoardGameCopyDAO bgCopyDao;
	
	private BookingDAO bookingDao;

	// initialize DAOs
	public BoardGameCopyCtrl(BookingDAO bookingDao, BoardGameCopyDAO bgCopyDao) {
		this.bookingDao = bookingDao;
		this.bgCopyDao = bgCopyDao;
	}
	
	/**
	 * Find a list of the BoardGameCopy objects that are not currently booked.
	 * @param date The date of which the customer is wanting to book the boardgame.
	 * @return List<BoardGameCopy> The list of copies that are currently available on the given date.
	 */
	@Override
	public List<BoardGameCopy> findAvailableBoardGameCopies(LocalDateTime date, String name, int noOfPlayers,
			Level level, Category category, int duration) {
		return bookingDao.findAvailableBoardGameCopies(date, name, noOfPlayers, level, category, duration);
	}
	
	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		bgCopyDao.addReservation(bgCopy, date);
		
	}

	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		bgCopyDao.removeReservation(bgCopy, date);
	}

	@Override
	public void addBoardGameCopy(BoardGameCopy bgCopy) {
		bgCopyDao.addBoardGameCopy(bgCopy);
	}

}
