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
	 * 
	 * @param amount
	 * @param transcationId
	 * @param paymentStatus
	 */
	public Payment(double amount, int transcationId, boolean paymentStatus, PaymentModeConstant paymentMode) {
		super();
		this.amount = amount;
		this.transcationId = transcationId;
		this.paymentStatus = paymentStatus;
		this.paymentMode = paymentMode;
	}
	
	/**
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * @param amount
	 * set amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * 
	 * @return transcationId
	 */
	public int getTranscationId() {
		return transcationId;
	}
	
	/**
	 * @param transcationId
	 * set transcationId
	 */
	public void setTranscationId(int transcationId) {
		this.transcationId = transcationId;
	}
	
	/**
	 * 
	 * @return payment status
	 */
	public boolean getPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * @return payment status
	 * set payment status
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	

	/**
	 * @return payment status
	 * get payment mode
	 */
	public PaymentModeConstant getPaymentMode() {
		return paymentMode;
	}
	

	/**
	 * @return payment status
	 * set payment mode
	 */
	public void setPaymentMode(PaymentModeConstant paymentMode) {
		this.paymentMode = paymentMode;
	}
}
