/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class StudentAlreadyRegisteredException extends Exception {

	private String studentId;
	public StudentAlreadyRegisteredException(String id) {
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
		return "StudentId: " + studentId + " is already registered.";
	}
}
