/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseAlreadyRegisteredException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private String courseId;
	
	/**
	 * 
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
