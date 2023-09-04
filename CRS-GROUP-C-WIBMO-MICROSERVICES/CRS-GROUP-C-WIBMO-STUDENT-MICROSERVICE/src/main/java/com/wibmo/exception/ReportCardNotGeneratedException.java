/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class ReportCardNotGeneratedException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studentId;
	 
	/**
	 * Constructor
	 * @param studentId2
	 */
	 public ReportCardNotGeneratedException(String studentId)
	 {
		 this.studentId=studentId;
	 }
	 
	 public String getMessage() 
	 {
			return "Report card has not generated for student : "+ studentId + " yet!";
	 }
}
