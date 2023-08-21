/**
 * 
 */
package com.wibmo.dao;

import java.sql.SQLException;
import java.util.UUID;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;

/**
 *
 */
public interface NotificationDAOInterface {

	/**
	 * Method to Send Notification
	 * @param type
	 * @param studentName
	 * @param modeOfPayment
	 * @param amount
	 */
	public void sendNotification(NotificationTypeConstant type,String studentName,PaymentModeConstant modeOfPayment,double amount);

	public UUID addPayment(String studentId, PaymentModeConstant paymentMode, double amount) throws SQLException;
	
}
