/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.constants.PaymentModeConstant;

/**
 * 
 */

@Service
public interface PaymentInterface {

	/**
	 * Method to add payment
	 * @param name
	 * @param mode
	 * @param type
	 * @param amount
	 * @return referenceID
	 */
	public String addPayment(String name,PaymentModeConstant mode, String type, double amount);
	
	/**
	 * Method to find paymentID by staudent name
	 * @param name
	 * @return paymentID
	 */
	public String findByStudentName(String name);
}
