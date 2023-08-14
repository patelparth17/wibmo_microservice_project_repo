/**
 * 
 */
package com.wibmo.business;

/**
 * 
 */
public interface PaymentOperationInterface {
	
	/**
	 * 
	 * @param studentId
	 */
	public void showPaymentStatus(String studentId);
	
	/**
	 * 
	 * @param studentId
	 */
	public void showBill(String studentId);
	
	/**
	 * 
	 * @param studentId
	 */
	public void makePayment(String studentId);

}
