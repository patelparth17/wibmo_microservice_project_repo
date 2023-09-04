/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class StudentAlreadyApprovedException extends Exception{
	
	private String studentId;
	public StudentAlreadyApprovedException(String id) {
		studentId = id;
	}
	
	/***
	 * Getter function for studentId
	 * @param studentId
	 */
	
	public String getstudentId() {
		return studentId;
	}
	
	
	@Override
	public String getMessage() {
		return "StudentId: " + studentId + " is already approved.";
	}
}
