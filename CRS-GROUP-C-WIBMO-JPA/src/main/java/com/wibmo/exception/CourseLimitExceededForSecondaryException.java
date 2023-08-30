/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when secondary course limit exceeded
 */
public class CourseLimitExceededForSecondaryException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMessage()
    {
        return "Size of Secondary course should be 2";
    }
}