/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when there is a violation in course size
 */
public class CourseSizeViolation extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String getMessage()
    {
        return "Course size should be 6 ";
    }

}