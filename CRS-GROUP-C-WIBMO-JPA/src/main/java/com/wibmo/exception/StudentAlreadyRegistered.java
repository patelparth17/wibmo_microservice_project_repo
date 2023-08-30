/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception for student already registered
 */
public class StudentAlreadyRegistered extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studentId;

    public StudentAlreadyRegistered(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Getter function for professorId
     * @return studentName
     */
    public String studentId() {
        return this.studentId;
    }

    /**
     * Message returned when exception is thrown
     */

    @Override
    public String getMessage() {
        return "Student " + studentId + " already Registered";
    }
}