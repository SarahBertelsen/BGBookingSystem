package ctrl;

import java.time.LocalDateTime;

import model.BoardGameCopy;
import model.Booking;

public interface BookingCtrlIF {
	
	public Booking createBooking(LocalDateTime date, int noOfGuests);
	
	public void addBoardGameCopy(BoardGameCopy bgCopy);
	
	public Booking addCustomerDetails(String fName, String lName, String phone);
	
	public Booking completeBooking();
	
	public Booking getCurrentBooking();
	
	public double calculatePrice(Booking booking);

}
