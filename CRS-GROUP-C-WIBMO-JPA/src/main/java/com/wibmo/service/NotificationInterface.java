/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;

/**
 * 
 */

@Service
public interface NotificationInterface {
	/**
	 * Method to send notification
	 * @param type
	 * @param studentId
	 * @param modeOfPayment
	 * @param amount
	 */
	public void sendNotification(NotificationTypeConstant type, String studentId);
}
