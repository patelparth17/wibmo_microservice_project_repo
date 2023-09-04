/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class NoCoursesRegisteredException extends Exception{
	private String studentId;
	 
	/**
	 * Constructor
	 * @param studentId2
	 */
	 public NoCoursesRegisteredException(String studentId2)
	 {
		 this.studentId=studentId2;
	 }
	 
	 /**
	  * Getter function for studentId
	  * @return
	  */
	 public String getStudentId()
	 {
		 return studentId;
	 }
	 
	 /**
		 * Message returned when exception is thrown
	 */
	 
	 public String getMessage() 
	 {
			return "Student with id: " + studentId + " hasn't registered for any course yet!";
	 }
}
