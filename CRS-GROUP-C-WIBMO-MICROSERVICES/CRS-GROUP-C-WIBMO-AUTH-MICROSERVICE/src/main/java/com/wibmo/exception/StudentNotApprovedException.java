package com.wibmo.exception;

public class StudentNotApprovedException extends Exception{
	private String studentName;
	 
	 public StudentNotApprovedException(String studentName)
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
		return "Student :"+ studentName+" not approved by admin. Please ask the Admin to approve first!";
		 
	 }
}
