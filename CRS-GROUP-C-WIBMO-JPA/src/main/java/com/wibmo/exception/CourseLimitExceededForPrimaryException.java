package com.wibmo.exception;

/**
 * Exception to throw when primary courses limit exceeded
 */
public class CourseLimitExceededForPrimaryException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMessage()
    {
        return "Size of Primary course should be 4";
    }
}