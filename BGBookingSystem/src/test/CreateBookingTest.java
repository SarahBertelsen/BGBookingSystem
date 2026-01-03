package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import ctrl.BookingCtrl;
import db.MembershipDAO;
import db.MembershipDB;
import model.Booking;

class CreateBookingTest {

	@BeforeEach
	void setUp() {
		ResetData resetData = new ResetData();
		resetData.resetData();
	}
	
	@Test
	void createBookingDateInPastUnitTest() {
		//Arrange
		CustomerDBStub customerDbStub = new CustomerDBStub();
		TableDBStub tableDbStub = new TableDBStub();
		BookingDBStub bookingDbStub = new BookingDBStub();
		MembershipDAO membershipDao = new MembershipDB();
		BookingCtrl bookingCtrl = new BookingCtrl(bookingDbStub, customerDbStub, tableDbStub, membershipDao);
		
		LocalDate date = LocalDate.parse("2023-12-30");
		LocalTime time = LocalTime.parse("18:00");
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		int noOfGuests = 6;
		
		//Act
		Booking booking = bookingCtrl.createBooking(dateTime, noOfGuests);
		
		//Assert
		assertNull(booking);
	}
}
