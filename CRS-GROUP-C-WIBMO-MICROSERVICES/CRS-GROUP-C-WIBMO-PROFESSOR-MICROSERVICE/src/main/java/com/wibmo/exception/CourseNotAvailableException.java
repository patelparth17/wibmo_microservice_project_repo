/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when course is not available
 */
public class CourseNotAvailableException extends Exception{
	private static final long serialVersionUID = 1L;
	private String courseCode;
	
	public CourseNotAvailableException(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * Getter function for professorId
	 * @return
	 */
	public String getcourseCode() {
		return this.courseCode;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "Professor didn't sign up for the course : " + courseCode;
	}
}
