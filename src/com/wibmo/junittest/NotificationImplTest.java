/**
 * 
 */
package com.wibmo.junittest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Payment;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOImpl;
import com.wibmo.dao.NotificationDAOInterface;

/**
 * 
 */
public class NotificationImplTest {

	private NotificationDAOInterface notificationOperation;
	private static Logger logger = Logger.getLogger(NotificationImplTest.class);
	
	@Before
	public void setUp() throws Exception {
		notificationOperation = NotificationDAOImpl.getInstance();
	}
	
	@Test
	public void testAddPayment() {
		Payment payment = new Payment(440.00, 1, true, PaymentModeConstant.UPI);
		try {
			assertNotNull(notificationOperation.addPayment("raj", payment.getPaymentMode(), payment.getAmount()));
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}


}
