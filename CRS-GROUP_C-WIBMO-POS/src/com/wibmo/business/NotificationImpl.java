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
	 * Method to make NotificationImpl Singleton
	 * @return instance
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

	@Override
	public void sendNotification(NotificationTypeConstant type, String studentName, PaymentModeConstant modeOfPayment,
			double amount) {
		notificationDaoObject.sendNotification(type, studentName, modeOfPayment, amount);
	}
}