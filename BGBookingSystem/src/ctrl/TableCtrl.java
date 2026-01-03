package ctrl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import db.BookingDAO;
import db.TableDAO;
import model.Table;

public class TableCtrl implements TableCtrlIF {
	private BookingDAO bookingDao;
	private TableDAO tableDao;
	
	// initialize DAOs
	public TableCtrl(TableDAO tableDao, BookingDAO bookingDao) {
		this.bookingDao = bookingDao;
		this.tableDao = tableDao;
	}

	/**
	 * Finds the Table with the fewest seats that matches the date and number of guests at the given data and time
	 * @param date The date of the booking.
	 * @param noOfGuests The number of guests attending.
	 * @return Table The matching table that is found.
	 */
	@Override
	public Table findTableByCriteria(LocalDateTime date, int noOfGuests) {
	List<Table> availableTables = bookingDao.findAvailableTables(date);
	Table table = null;
	if (availableTables != null) {
		table = availableTables.stream().filter(t -> t.getNoOfSeats() >= noOfGuests)
				.min(Comparator.comparingInt(t -> t.getNoOfSeats())).orElse(null);
	}
	return table;
	}
}
