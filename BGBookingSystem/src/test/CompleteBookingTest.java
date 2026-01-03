package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import ctrl.BookingCtrl;
import ctrl.CustomerCtrl;
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
import model.Customer;
import model.Table;

public class CompleteBookingTest {
	
	private BookingCtrl bookingCtrl;
	private CustomerCtrl customerCtrl;
	private BookingDAO bookingDAO;
	private CustomerDAO customerDAO;
	private MembershipDAO membershipDao;
	private TableDAO tableDAO;
	private BoardGameCopyDAO bGCDAO;
	private LocalDateTime date;
	private String fName;
	private String lName;
	private String phone;
	
	@BeforeEach
	void setUp() {
		ResetData resetData = new ResetData();
		resetData.resetData();
	}
	
	@Test 
	public void completeBookingTest() {
		
		//Arrange 
		customerDAO = new CustomerDB();
		tableDAO = new TableDB();
		bGCDAO = new BoardGameCopyDB();
		membershipDao = new MembershipDB();
		bookingDAO = new BookingDB(bGCDAO, tableDAO, customerDAO, membershipDao);
		
		date = LocalDateTime.of(2030,12,30,18,0);
		fName = "Ole";
		lName = "Olesen";
		phone = "+4577668899";
		
		bookingCtrl = new BookingCtrl(bookingDAO, customerDAO, tableDAO, membershipDao);
		customerCtrl = new CustomerCtrl(customerDAO, membershipDao);
		
		//Act
		bookingCtrl.createBooking(date, 4);
		bookingCtrl.addCustomerDetails(fName, lName, phone);
		
		//Assert
		assertDoesNotThrow(() -> bookingCtrl.completeBooking()); // checks that no exception is thrown
		
	}
}
