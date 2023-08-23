/**
 * 
 */
package com.wibmo.exception;

/**
 * @author Parth
 *
 */
public class CourseLimitExceededException extends Exception {
	
	private int num;

	/**
	 * Constructor
	 * @param num number of courses
 	 */
	public CourseLimitExceededException(int num )
	{	
		this.num = num;
	}


	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() 
	{
		return "You have already registered for " + num + " courses and 2 secondary courses";
	}
}
