package model;

public class Table {
	private int tableNo;
	private int noOfSeats;
	
	/**
	 * Constructor for Table using all attributes
	 * @param tableNo
	 * @param noOfSeats
	 */
	
	public Table(int tableNo, int noOfSeats) {
		this.tableNo = tableNo;
		this.noOfSeats = noOfSeats;
	}
	
	public int getTableNo() {
		return tableNo;
	}
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

}
