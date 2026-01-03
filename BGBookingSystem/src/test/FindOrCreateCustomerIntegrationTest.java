package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.CustomerCtrl;
import db.CustomerDAO;
import db.CustomerDB;
import db.MembershipDAO;
import db.MembershipDB;
import model.Customer;

class FindOrCreateCustomerIntegrationTest {
	CustomerDAO cusDao = new CustomerDB();
	MembershipDAO membershipDao = new MembershipDB();
	CustomerCtrl cusCtrl = new CustomerCtrl(cusDao, membershipDao);

	@BeforeEach
	void setUp() {
		new ResetData().resetData();
	}
	
	
	@Test
	void CustomerCtrlFindExistingCustomer() {
		//Arrange and Act
		Customer customer = cusCtrl.findOrCreateCustomer("Ole", "Olesen", "+4577668899");
		
		//Assert
		assertTrue(customer.getCustomerId() >= 0);
	}

	
	@Test
	void CustomerCtrlCreateNotExistingCustomer() {
		//Arrange and Act
		Customer customer = cusCtrl.findOrCreateCustomer("Mads", "Jensen", "+4512341234");
		
		//Assert
		assertFalse(customer.getCustomerId() >= 0);
	}
	
}
