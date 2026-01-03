package model;

import java.time.LocalDate;

public class Membership {

	private int customerId;
	private LocalDate startDate; 
	private LocalDate endDate; 
	
	/**
	 * Constructor for Membership using all attributes
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 */
	
	public Membership(int customerId, LocalDate startDate, LocalDate endDate) {
		this.customerId = customerId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
}
