package db;

import java.sql.ResultSet;

import model.Customer;
import model.CustomerSaveStatus;

public interface CustomerDAO {
	
	public Customer findCustomer(String fName, String lName, String phone);
	
	public Customer findCustomer (Customer customer);
	
	public void saveCustomer(Customer customer);
	
	public Customer buildObject(ResultSet rs);

}
