/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class StudentNotFoundForApprovalException extends Exception{
	
	private String StudentId;
	
	public StudentNotFoundForApprovalException(String Id) {
		StudentId = Id;
	}
	
	/**
	 * Getter function for professorId
	 * @return
	 */
	public String getUserId() {
		return StudentId;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "StudentId: " + StudentId + " not registered!";
	}
}
