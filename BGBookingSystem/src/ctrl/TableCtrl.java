package ctrl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import db.BookingDAO;
import db.TableDAO;
import model.Booking;
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
	 * Finds the Table with the fewest seats that matches the date and number of
	 * guests at the given data and time
	 * 
	 * @param date       The date of the booking.
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

	public Table findAvailableTable(LocalDateTime date, int noOfGuests) {
		List<Booking> bookings = bookingDao.getAllBookings();
		List<Table> tables = tableDao.getAllTables();
		tables = tables.stream().filter(t -> t.getNoOfSeats() >= noOfGuests).collect(Collectors.toList());

		Iterator<Table> it = tables.iterator();
		while (it.hasNext()) {
			Table table = it.next();
			if (isTableReserved(table, bookings, date)) {
				it.remove();
			}
		}

		return tables.stream().min(Comparator.comparingInt(t -> t.getNoOfSeats())).orElse(null);
		;
	}

	private boolean isTableReserved(Table table, List<Booking> bookings, LocalDateTime startDate) {
		boolean success = false;
		int i = 0;

		while (!success && i < bookings.size()) {
			Booking booking = bookings.get(i);
			Table bookingTable = booking.getTable();
			if (bookingTable.getTableNo() == table.getTableNo()) {
				LocalDateTime bookingStartDate = booking.getDate();
				LocalDateTime bookingEndDate = bookingStartDate.plusHours(3);

				LocalDateTime endDate = startDate.plusHours(3);

				if (!(startDate.isAfter(bookingEndDate) && endDate.isBefore(bookingStartDate)))
					;
				success = true;
			}

			i++;
		}
		return success;
	}

}
