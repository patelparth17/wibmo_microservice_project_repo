/**
 *  @author kuragayala.vs
 */
package com.wibmo.exception;

/**
 * Exception to check if student is not registered
 */
public class StudentNotRegisteredException extends Exception{
	 private String studentID;
	 
	 public StudentNotRegisteredException(String studentID)
	 {
		 this.studentID=studentID;
	 }
	 
	 /**
	  * getter function for studentID
	  * @return
	  */
	 public String getstudentID()
	 {
		 return studentID;
	 }
	 
	 public String getMessage() {
		 return  "Student ID: "+ studentID + " not registered!";
	 }
	 
}