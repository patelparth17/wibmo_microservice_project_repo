/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

/**
 * 
 */

public class EnrolledStudent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String courseCode;
	String courseName;
	String studentId;
	
	/**
	 * Parametrized constructor
	 * @param courseCode
	 * @param courseName
	 * @param studentId
	 */
	public EnrolledStudent(String courseCode,String courseName,String studentId){
		this.courseCode=courseCode;
		this.courseName=courseName;
		this.studentId=studentId;
	}
	
	public EnrolledStudent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method to get the course code
	 * @return Course code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Method to set the course code
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * Method to get the course name
	 * @return Course name
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Method to set the course name
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Method to get the studentID
	 * @return Student ID
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * Method to set the studentID
	 * @param studentId
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}	
}