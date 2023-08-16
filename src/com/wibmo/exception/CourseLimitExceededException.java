/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseLimitExceededException {
	
	private int num;
	
	public CourseLimitExceededException(int num) {
		this.num = num;
	}
	
	public String getMessage() 
	{
		return "You have already registered for " + num + " courses";
	}
}
