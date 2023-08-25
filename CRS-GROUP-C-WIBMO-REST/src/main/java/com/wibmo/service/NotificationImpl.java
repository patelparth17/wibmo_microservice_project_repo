/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOImpl;

/**
 * Implementation of methods which calls Notification DAO methods 
 */
@Service
public class NotificationImpl implements NotificationInterface{
	
	@Autowired
	private NotificationDAOImpl notificationDaoObject;

	@Override
	public void sendNotification(NotificationTypeConstant type, String studentName, PaymentModeConstant modeOfPayment,
			double amount) {
		notificationDaoObject.sendNotification(type, studentName, modeOfPayment, amount);
	}
}