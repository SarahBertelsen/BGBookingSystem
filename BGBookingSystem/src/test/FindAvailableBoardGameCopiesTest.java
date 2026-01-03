package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.BoardGameCopyCtrl;
import db.BoardGameCopyDB;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.BoardGame;
import model.BoardGameCopy;
import model.Category;
import model.Level;
import db.BoardGameCopyDAO;

class FindAvailableBoardGameCopiesTest {
	BoardGameCopyDAO bGCDB = new BoardGameCopyDB();
	TableDAO tDB = new TableDB();
	CustomerDAO cDB = new CustomerDB();
	MembershipDAO membershipDao = new MembershipDB();
	BoardGameCopyCtrl bGCCtrl = new BoardGameCopyCtrl(new BookingDB(bGCDB, tDB, cDB, membershipDao), bGCDB);
	
	@BeforeEach
	void setUp() {
		bGCDB = new BoardGameCopyDB();
		tDB = new TableDB();
		CustomerDAO cDB = new CustomerDB();
		MembershipDAO membershipDao = new MembershipDB();
		bGCCtrl = new BoardGameCopyCtrl(new BookingDB(bGCDB, tDB, cDB, membershipDao), bGCDB);
		
		new ResetData().resetData();
	}

	@Test
	void BookingDBsearchForAvailableGameByNameIntegrationTest() {
		// We assume that 2 copies of Horse Race exist
		//Arrange
		String horseRaceName = "Horse Race";
		int expectedCopyAmount = 2;
		LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
		
		//Act
		List<BoardGameCopy> bGCopies = bGCCtrl.findAvailableBoardGameCopies(dateTime, horseRaceName, 0, null, null, 0);
		
		//Assert
		assertTrue(bGCopies.size() == expectedCopyAmount);
	}
	
	@Test
	void BookingDBSearchForAvailableGameByAboveZeroNoOfPlayersIntegrationTest() {
		//Arrange
		int amountOfPlayers = 6;
		int expectedCopyAmount = 6;
		LocalDate date = LocalDate.parse("2025-12-14");
		LocalTime time = LocalTime.parse("18:00");
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		
		//Act
		List<BoardGameCopy> bGCopies = bGCCtrl.findAvailableBoardGameCopies(dateTime, null, amountOfPlayers, null, null, 0);
		
		//Assert
		assertTrue(bGCopies.size() == expectedCopyAmount);
	}
	
	@Test
	void BookingDBSearchForAvailableGameByLevelIntegrationTest() {
		//Arrange
		Level searchedLevel = Level.INTERMEDIATE;
		int expectedCopyAmount = 8;
		LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
		
		//Act
		List<BoardGameCopy> bGCopies = bGCCtrl.findAvailableBoardGameCopies(dateTime, null, 0, searchedLevel, null, 0);
		
		//Assert
		assertTrue(bGCopies.size() == expectedCopyAmount);
	}
	

	@Test
	void BookingDBSearchNoCriteriaIntegrationTest() {
		//Arrange
		LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
		
		//Act
		List<BoardGameCopy> bGCopies = bGCCtrl.findAvailableBoardGameCopies(dateTime, null, 0, Level.NONE, Category.NONE, 0);
		
		//Assert
		assertTrue(bGCopies.size() > 0);
	}
	
	@Test
	void BookingDBSearchForAvailableGameByCategoryIntegrationTest() {
		//Arrange
		Category searchedCategory = Category.FAMILY;
		int expectedCopyAmount = 7;
		LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
		
		//Act
		List<BoardGameCopy> bGCopies = bGCCtrl.findAvailableBoardGameCopies(dateTime, null, 0, null, searchedCategory, 0);
		
		//Assert
		assertTrue(bGCopies.size() == expectedCopyAmount);
	}
	
	
}
