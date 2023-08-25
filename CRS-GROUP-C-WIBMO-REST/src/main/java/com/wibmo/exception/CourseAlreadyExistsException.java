/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when course already exists
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
		return "Course code: "+ courseId + " already exists in database.";
	}

}
