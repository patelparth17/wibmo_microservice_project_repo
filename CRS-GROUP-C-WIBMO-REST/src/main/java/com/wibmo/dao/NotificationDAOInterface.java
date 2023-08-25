/**
 * 
 */
package com.wibmo.dao;

import org.springframework.stereotype.Repository;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;

/**
 * Interface of methods which performs read and write operations of Notification in Database
 */
@Repository
public interface NotificationDAOInterface {

	/**
	 * Method to Send Notification
	 * @param type
	 * @param studentName
	 * @param modeOfPayment
	 * @param amount
	 */
	public void sendNotification(NotificationTypeConstant type,String studentName,PaymentModeConstant modeOfPayment,double amount);
	
}
