/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class ReportCardNotGeneratedException extends Exception{
	private String studentId;
	 
	/**
	 * Constructor
	 * @param studentId2
	 */
	 public ReportCardNotGeneratedException(String studentId)
	 {
		 this.studentId=studentId;
	 }
	 
	 /**
	  * Getter function for studentId
	  * @return studentId
	  */
	 public String getStudentId()
	 {
		 return studentId;
	 }
	 
	 public String getMessage() 
	 {
			return "Report card has not generated for student : "+ studentId + " yet!";
	 }
}
