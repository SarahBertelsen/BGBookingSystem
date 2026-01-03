package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

	private int bookingId;
	private LocalDateTime date;
	private int noOfGuests;
	private Customer customer;
	private List<BoardGameCopy> bgCopies = new ArrayList<>();
	private Table table;
	private double price;
	
	/**
	 * Constructor for Booking
	 * @param date
	 * @param noOfGuests
	 */

	public Booking(LocalDateTime date, int noOfGuests) {
		this.date = date;
		this.noOfGuests = noOfGuests;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getNoOfGuests() {
		return noOfGuests;
	}

	public void setNoOfGuests(int noOfGuests) {
		this.noOfGuests = noOfGuests;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<BoardGameCopy> getBoardGameCopies() {
		return bgCopies;
	}

	public void addBoardGameCopy(BoardGameCopy bgCopy) {
		bgCopies.add(bgCopy);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
