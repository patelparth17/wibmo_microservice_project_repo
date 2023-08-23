/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOImpl;

@Service
public class NotificationImpl implements NotificationInterface{
	
	@Autowired
	private NotificationDAOImpl notificationDaoObject;
//	private NotificationImpl() {}
	
	/**
	 * Method to make NotificationImpl Singleton
	 * @return instance
	 */
//	public static NotificationImpl getInstance()
//	{
//		if(instance==null)
//		{
//			// This is a synchronized block, when multiple threads will access this instance
//			synchronized(NotificationImpl.class){
//				instance=new NotificationImpl();
//			}
//		}
//		return instance;
//	}

	@Override
	public void sendNotification(NotificationTypeConstant type, String studentName, PaymentModeConstant modeOfPayment,
			double amount) {
		notificationDaoObject.sendNotification(type, studentName, modeOfPayment, amount);
	}
}