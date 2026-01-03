package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.BoardGameCopyCtrl;
import ctrl.BookingCtrl;
import ctrl.TableCtrl;
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
import model.Booking;
import model.Table;

class FindTableByCriteriaTest {
	BoardGameCopyDAO bgCopyDB = new BoardGameCopyDB();
	TableDAO tableDB = new TableDB();
	CustomerDAO customerDB = new CustomerDB();
	MembershipDAO membershipDao = new MembershipDB();
	BookingDAO bookingDB = new BookingDB(bgCopyDB, tableDB, customerDB, membershipDao);
	BookingCtrl bookingCtrl = new BookingCtrl(bookingDB, customerDB, tableDB, membershipDao);
	TableCtrl tableCtrl = new TableCtrl(tableDB, bookingDB);
	
	@BeforeEach
	void setUp() {
		new ResetData().resetData();
	}
	
	@Test
	void tableCtrlFindTableByCriteriaValidIntegrationTest() {
		//TableNo1001 har 4 pladser
		//Arrange
		int noOfGuests = 4;
		LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
		
		//Act
		Table table = tableCtrl.findTableByCriteria(dateTime, noOfGuests);
		
		//Assert
		assertNotNull(table);
	}
//
//	@Test
//	void tableCtrlFindTableByCriteriaFindNotExistingTableIntegrationTest() {
//		//Arrange
//		int noOfGuests = 50;
//		LocalDate date = LocalDate.parse("2025-12-14");
//		LocalTime time = LocalTime.parse("18:00");
//		LocalDateTime dateTime = LocalDateTime.of(date, time);
//		
//		//Act
//		Table table = tableCtrl.findTableByCriteria(dateTime, noOfGuests);
//		
//		//Assert
//		assertNull(table);
//	}
//	
//	@Test
//	void tableCtrlFindTableByCriteriaFindUnavailableTableIntegrationTest() {
//		//Only 2 table of 10 seats exists.
//		//Arrange
//		int noOfGuests = 10;
//		LocalDate date = LocalDate.parse("2025-12-14");
//		LocalTime time = LocalTime.parse("18:00");
//		LocalDateTime dateTime = LocalDateTime.of(date, time);
//		
//		String fName = "Ole";
//		String lName = "Olesen";
//		String phone = "+4512341234";
//		
//		//Act
//		bookingCtrl.createBooking(dateTime, noOfGuests);
//		bookingCtrl.addCustomerDetails(fName, lName, phone);
//		bookingCtrl.completeBooking();
//		
//		bookingCtrl.createBooking(dateTime, noOfGuests);
//		bookingCtrl.addCustomerDetails(fName, lName, phone);
//		bookingCtrl.completeBooking();
//		
//		Table table = tableCtrl.findTableByCriteria(dateTime, noOfGuests);
//		
//		//Assert
//		assertNull(table);
//	}
	
	@Test
	void findTableByCriteriaValidUnitTest() {
		//Arrange
		TableDAO tabledao = new TableDBStub();
		BookingDBStub bookingdao = new  BookingDBStub();
		TableCtrl tctrl = new TableCtrl(tabledao, bookingdao);
		LocalDate date = LocalDate.parse("2025-12-14");
		LocalTime time = LocalTime.parse("18:00");
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		int noOfGuests = 6;
		bookingdao.addTable(new Table(1,4));
		bookingdao.addTable(new Table(2,6));
		bookingdao.addTable(new Table(3,8));
		
		//Act
		Table table = tctrl.findTableByCriteria(dateTime, noOfGuests);
		
		//Assert
		assertNotNull(table);
	}
}
