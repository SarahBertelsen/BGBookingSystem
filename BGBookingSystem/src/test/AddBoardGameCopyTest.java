package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.BoardGameCopyCtrl;
import ctrl.BookingCtrl;
import db.BoardGameCopyDAO;
import db.BoardGameCopyDB;
import db.BookingDAO;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.BoardGameCopy;

class AddBoardGameCopyTest {

	@BeforeEach
	void setUp() {
		new ResetData().resetData();
	}

	@Test
	void addBoardGameCopyTooManyAddedIntegrationTest() {
		// Arrange
		int expectedCopyAmount = 3;

		BoardGameCopy bgCopy1 = new BoardGameCopy(0, null);
		BoardGameCopy bgCopy2 = new BoardGameCopy(0, null);
		BoardGameCopy bgCopy3 = new BoardGameCopy(0, null);
		BoardGameCopy bgCopy4 = new BoardGameCopy(0, null);

		TableDAO tableDao = new TableDB();
		BoardGameCopyDAO bgCopyDao = new BoardGameCopyDB();
		CustomerDAO customerDB = new CustomerDB();
		MembershipDAO membershipDao = new MembershipDB();
		BookingDAO bookingDao = new BookingDB(bgCopyDao, tableDao, customerDB, membershipDao);
		CustomerDAO customerDao = new CustomerDB();
		BookingCtrl bookingCtrl = new BookingCtrl(bookingDao, customerDao, tableDao, membershipDao);

		LocalDateTime bookingDate = LocalDateTime.now().plusDays(1);
		int bookingPlayerCount = 2;

		// Act
		bookingCtrl.createBooking(bookingDate, bookingPlayerCount);

		assertThrows(InvalidFormatException.class, () -> {
			bookingCtrl.addBoardGameCopy(bgCopy1);
			bookingCtrl.addBoardGameCopy(bgCopy2);
			bookingCtrl.addBoardGameCopy(bgCopy3);
			bookingCtrl.addBoardGameCopy(bgCopy4);
		});
	}
}
