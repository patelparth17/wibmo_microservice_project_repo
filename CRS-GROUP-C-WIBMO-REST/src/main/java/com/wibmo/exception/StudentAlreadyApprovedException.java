/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when student is already approved
 */
public class StudentAlreadyApprovedException extends Exception{
	
	private String studentId;
	public StudentAlreadyApprovedException(String id) {
		studentId = id;
	}
	
	/***
	 * Getter function for studentId
	 * @return studentId
	 */
	
	public String getstudentId() {
		return studentId;
	}
	
	
	@Override
	public String getMessage() {
		return "StudentId: " + studentId + " is already approved.";
	}
}
