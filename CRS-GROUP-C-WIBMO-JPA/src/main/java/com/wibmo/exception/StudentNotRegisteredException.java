/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to check if student is not registered
 */
public class StudentNotRegisteredException extends Exception{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studentName;
	 
	 public StudentNotRegisteredException(String studentName)
	 {
		 this.studentName=studentName;
	 }
	 
	 /**
	  * getter function for studentName
	  * @return
	  */
	 public String getStudentName()
	 {
		 return studentName;
	 }
	 
	 public String getMessage() {
		return "Student with username: "+ studentName+" is not registered!";
		 
	 }
	 
}