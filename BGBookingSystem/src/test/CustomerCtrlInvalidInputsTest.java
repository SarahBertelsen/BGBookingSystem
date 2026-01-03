package test;

import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import ctrl.CustomerCtrl;

class CustomerCtrlInvalidInputsTest {

	@Test
	void isValidNumberUnitTest() {
		//Arrange and Act
		boolean result = CustomerCtrl.isValidNumber("+1812341234");
		
		//Assert
		assertFalse(result);
	}

	@Test
	void isValidNameUnitTest() {
		//Arrange and Act
		boolean result = CustomerCtrl.isValidName("ol3", "Ol3sen");
		
		//Assert
		assertFalse(result);
	}
}
