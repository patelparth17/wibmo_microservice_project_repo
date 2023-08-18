/**
 * 
 */
package com.wibmo.business;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOImpl;
import com.wibmo.dao.NotificationDAOInterface;

public class NotificationImpl implements NotificationInterface{
	
	private static volatile NotificationImpl instance=null;
	NotificationDAOInterface notificationDaoObject = NotificationDAOImpl.getInstance();
	private NotificationImpl() {}
	
	/**
	 * Method to make NotificationDaoOperation Singleton
	 * @return
	 */
	public static NotificationImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(NotificationImpl.class){
				instance=new NotificationImpl();
			}
		}
		return instance;
	}
	
	
//
//	/**
//	 * Method to return UUID for a transaction
//	 * @param notificationId: notification id added in the database
//	 * @return transaction id of the payment
//	 */
//	@Override
//	public UUID getReferenceId(int notificationId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * Method to send notification
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: payment mode used
	 * @return notification id for the record added in the database
	 */

	@Override
	public void sendNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment,
			double amount) {
		notificationDaoObject.sendNotification(type, studentId, modeOfPayment, amount);
	}
	

	
	
	
}