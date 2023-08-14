/**
 * 
 */
package com.wibmo.bean;

/**
 * 
 */
public class Payment {
	
	private double amount;
	private int transcationId;
	private boolean paymentStatus;
	
	/**
	 * 
	 * @param amount
	 * @param transcationId
	 * @param paymentStatus
	 */
	public Payment(double amount, int transcationId, boolean paymentStatus) {
		super();
		this.amount = amount;
		this.transcationId = transcationId;
		this.paymentStatus = paymentStatus;
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
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * @return payment status
	 * set payment status
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
}
