package db;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Customer;

public interface MembershipDAO {
	
	public boolean checkIfMember(Customer customer, LocalDate date);

}
