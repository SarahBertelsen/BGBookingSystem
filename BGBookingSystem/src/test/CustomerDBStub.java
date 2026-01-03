package test;

import java.sql.ResultSet;

import db.CustomerDAO;
import model.Customer;
import model.CustomerSaveStatus;

public class CustomerDBStub implements CustomerDAO{

	@Override
	public Customer findCustomer(String fName, String lName, String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
	}

	@Override
	public Customer buildObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
