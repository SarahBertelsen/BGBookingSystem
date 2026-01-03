package ctrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Customer;
import model.CustomerSaveStatus;

public interface CustomerCtrlIF {
	
	public Customer findOrCreateCustomer(String fName, String lName, String phone);
	
	public void saveCustomer(Customer customer);
	
	public boolean checkIfMember(Customer customer, LocalDate date);

}
