package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

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
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.BoardGameCopy;

class ReservationTest {
	
	@BeforeEach
	void setUp() {
		new ResetData().resetData();
	}
	
	@Test
	void AddReservationTest() {
		BoardGameCopyDAO bGCDB = new BoardGameCopyDB();
		TableDAO tDB = new TableDB();
		CustomerDAO cDB = new CustomerDB();
		MembershipDAO membershipDao = new MembershipDB();
		BoardGameCopyCtrl bGCCtrl = new BoardGameCopyCtrl(new BookingDB(bGCDB, tDB, cDB, membershipDao), bGCDB);
		
		
		LocalDateTime checkTime = LocalDateTime.now().plusDays(1);
		
		List<BoardGameCopy> foundBoardGames = bGCCtrl.findAvailableBoardGameCopies(checkTime, null, 0, null, null, 0);
		int preReserveSize = foundBoardGames.size();
		
		bGCCtrl.addReservation(foundBoardGames.get(0), checkTime);
		
		int postReserveSize = bGCCtrl.findAvailableBoardGameCopies(checkTime, null, 0, null, null, 0).size();
		
		assertTrue(preReserveSize == postReserveSize + 1);
	}
	
	@Test
	void RemoveReservationTest() {
		BoardGameCopyDAO bGCDB = new BoardGameCopyDB();
		TableDAO tDB = new TableDB();
		CustomerDAO cDB = new CustomerDB();
		MembershipDAO membershipDao = new MembershipDB();
		BoardGameCopyCtrl bGCCtrl = new BoardGameCopyCtrl(new BookingDB(bGCDB, tDB, cDB, membershipDao), bGCDB);
		
		
		LocalDateTime checkTime = LocalDateTime.now().plusDays(1);
		
		List<BoardGameCopy> foundBoardGames = bGCCtrl.findAvailableBoardGameCopies(checkTime, null, 0, null, null, 0);
		BoardGameCopy checkCopy = foundBoardGames.get(0);
		bGCCtrl.addReservation(checkCopy, checkTime);
		foundBoardGames = bGCCtrl.findAvailableBoardGameCopies(checkTime, null, 0, null, null, 0);
		int preUnreserveSize = foundBoardGames.size();
		bGCCtrl.removeReservation(checkCopy, checkTime);
		int postUnreserveSize = bGCCtrl.findAvailableBoardGameCopies(checkTime, null, 0, null, null, 0).size();
		
		assertTrue(preUnreserveSize == postUnreserveSize - 1);
	}

}
