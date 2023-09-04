/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseAlreadyExistsException extends Exception{
	
	private static final long serialVersionUID = 1L;
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
