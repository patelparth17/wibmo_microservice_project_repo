/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when course is not found 
 */
public class CourseNotFoundException extends Exception{
	private String courseCode;
	
	/**
	 * Constructor
	 * @param courseCode
	 */
	public CourseNotFoundException(String courseCode)
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
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() 
	{
		return "Course with courseCode: " + courseCode + " not found.";
	}

}
