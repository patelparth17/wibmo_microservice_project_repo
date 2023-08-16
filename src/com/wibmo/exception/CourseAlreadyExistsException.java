/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseAlreadyExistsException extends Exception{
	private String courseId;
	
	 /**
	  * 
	  * @param courseId
	  */
	public CourseAlreadyExistsException(String courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * 
	 * @return courseId 
	 */
	public String getCourseId() {
		return courseId;
	}
	
	/**
	 * @return message having 
	 * course and courseId
	 */
	public String getMessage() {
		return "Course: "+ courseId + " already exists in database.";
	}

}
