/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when course already registered
 */
public class CourseAlreadyRegisteredException extends Exception{
	
	private String courseId;
	
	/**
	 * Constructor
	 * @param courseId
	 */
	public CourseAlreadyRegisteredException(String courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * Getter Method
	 * @return course code
	 */
	public String getCourseId() {
		return courseId;
	}
	
	/**
	 * @return message for 
	 * courseAlreadyRegisteredException
	 */
	@Override
	public String getMessage() {
		return "Course: " + courseId + " already exists in catalog.";
	}
}
