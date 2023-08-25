/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when primary courses limit exceeded
 */
public class CourseLimitExceededForPrimaryException extends Exception{
	public String getMessage()
    {
        return "Size of Primary course should be 4";
    }
}