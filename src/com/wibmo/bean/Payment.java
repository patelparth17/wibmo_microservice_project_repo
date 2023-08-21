/**
 * 
 */
package com.wibmo.bean;

import com.wibmo.constants.PaymentModeConstant;

/**
 * 
 */
public class Payment {
	
	private double amount;
	private int transcationId;
	private boolean paymentStatus;
	private PaymentModeConstant paymentMode;
	

	/**
	 * Parametrized Constructor
	 * @param amount
	 * @param transcationId
	 * @param paymentStatus
	 * @param paymentMode
	 */
	public Payment(double amount, int transcationId, boolean paymentStatus, PaymentModeConstant paymentMode) {
		super();
		this.amount = amount;
		this.transcationId = transcationId;
		this.paymentStatus = paymentStatus;
		this.paymentMode = paymentMode;
	}
	
	/**
	 * Method to get the payment fee
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Method to set the payment fee
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Method to get the transaction ID of payment
	 * @return transcationId
	 */
	public int getTranscationId() {
		return transcationId;
	}
	
	/**
	 * Method to set the transaction ID of payment
	 * @param transcationId
	 */
	public void setTranscationId(int transcationId) {
		this.transcationId = transcationId;
	}
	
	/**
	 * Method to get the payment status
	 * @return payment status
	 */
	public boolean getPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * Method to set the payment status
	 * @param paymentStatus
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	

	/**
	 * Method to get the payment mode
	 * @return payment mode
	 */
	public PaymentModeConstant getPaymentMode() {
		return paymentMode;
	}
	

	/**
	 * Method to set the payment mode
	 * @param paymentMode
	 */
	public void setPaymentMode(PaymentModeConstant paymentMode) {
		this.paymentMode = paymentMode;
	}
}
