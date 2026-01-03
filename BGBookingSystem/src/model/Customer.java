package model;

public class Customer {
	private String fName;
	private String lName;
	private String phone;
	private int customerId;
	private CustomerSaveStatus status;
	
	/**
	 * Constructor used when customer exists in database
	 * @param customerId
	 * @param fName
	 * @param lName
	 * @param phone
	 */
	
	public Customer(int customerId, String fName, String lName, String phone) {
		this.customerId = customerId;
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
		this.status = CustomerSaveStatus.CUSTOMER_ALREADY_EXISTS;
	}
	
	/**
	 * Constructor used when customer does not exist in database
	 * @param fName
	 * @param lName
	 * @param phone
	 */

	public Customer(String fName, String lName, String phone) {
		this.customerId = -1; //Bruges kun hvis vi ikke kan finde Customer i databasen og har derfor ikke et id endnu.
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
		this.status = CustomerSaveStatus.CUSTOMER_DOES_NOT_EXIST;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public CustomerSaveStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerSaveStatus status) {
		this.status = status;
	}
	
}
