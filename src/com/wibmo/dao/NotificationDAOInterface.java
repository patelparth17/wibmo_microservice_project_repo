/**
 * 
 */
package com.wibmo.dao;

import java.sql.SQLException;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;

/**
 *
 */
public interface NotificationDAOInterface {

	/**
	 * Send Notification using SQL commands
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: mode of payment used, defined in enum
	 * @param amount
	 * @throws SQLException
	 */
	public void sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,double amount);
	
}
