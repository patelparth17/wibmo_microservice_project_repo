/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when course is not deleted
 */
public class CourseNotDeletedException extends Exception{
	private String courseCode;
	
	/**
	 * Constructor
	 * @param courseCode : String
	 */
	public CourseNotDeletedException(String courseCode)
	{	
		this.courseCode = courseCode;
	}

	/**
	 * Getter function for course code
	 * @return courseCode
	 */
	public String getCourseCode()
	{
		return courseCode;
	}
	
	/**
	 * Message thrown by exception
	 */
	@Override
	public String getMessage() 
	{
		return "Course with courseCode: " + courseCode + " can't be deleted.";
	}

}
