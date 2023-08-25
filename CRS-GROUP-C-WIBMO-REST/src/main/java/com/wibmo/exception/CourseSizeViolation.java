/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when there is a violation in course size
 */
public class CourseSizeViolation extends Exception{

    @Override
    public String getMessage()
    {
        return "Course size should be 6 ";
    }

}