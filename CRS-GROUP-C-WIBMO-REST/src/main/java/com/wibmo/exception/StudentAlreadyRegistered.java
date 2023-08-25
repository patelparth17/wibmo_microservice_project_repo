/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception for student already registered
 */
public class StudentAlreadyRegistered extends Exception{

    private String studentName;

    public StudentAlreadyRegistered(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Getter function for professorId
     * @return studentName
     */
    public String studentName() {
        return this.studentName;
    }

    /**
     * Message returned when exception is thrown
     */

    @Override
    public String getMessage() {
        return "Student " + studentName + " already Registered";
    }
}