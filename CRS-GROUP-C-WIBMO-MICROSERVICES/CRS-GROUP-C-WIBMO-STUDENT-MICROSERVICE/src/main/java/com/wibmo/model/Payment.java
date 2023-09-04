/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wibmo.constants.PaymentModeConstant;

/**
 * 
 */

@Entity
@Table(name="payment")
public class Payment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="referenceId")
	private String transcationId;
	
	@Column(name="paymentStatus")
	private String paymentStatus;
	
	@Column(name="modeOfPayment")
	private String paymentMode;
	
	@Id
	@Column(name="studentName")
	private String studentName;

	/**
	 * Parametrized Constructor
	 * @param amount
	 * @param transcationId
	 * @param paymentStatus
	 * @param paymentMode
	 */
	public Payment(double amount, String transcationId, String paymentStatus, PaymentModeConstant paymentMode) {
		this.amount = amount;
		this.transcationId = transcationId;
		this.paymentStatus = paymentStatus;
		this.paymentMode = paymentMode.toString();
	}
	
	public Payment() {
		
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
	public String getTranscationId() {
		return transcationId;
	}
	
	/**
	 * Method to set the transaction ID of payment
	 * @param transcationId
	 */
	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}
	
	/**
	 * Method to get the payment status
	 * @return payment status
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * Method to set the payment status
	 * @param paymentStatus
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	

	/**
	 * Method to get the payment mode
	 * @return payment mode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}
	

	/**
	 * Method to set the payment mode
	 * @param paymentMode
	 */
	public void setPaymentMode(PaymentModeConstant paymentMode) {
		this.paymentMode = paymentMode.toString();
	}
	
	/**
	 * Method to get the student name 
	 * @return studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * Method to set the student name
	 * @param studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
