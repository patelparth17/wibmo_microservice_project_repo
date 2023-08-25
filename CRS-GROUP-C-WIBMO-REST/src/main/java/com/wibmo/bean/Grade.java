/**
 * 
 */
package com.wibmo.bean;

import java.io.Serializable;

/**
 * Bean class for Grade
 */
public class Grade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseCode;
	private String courseName;
	private String grade;
	
	/**
	 * Parametrized Constructor
	 * @param courseCode
	 * @param courseName
	 * @param grade
	 */
	public Grade(String courseCode, String courseName, String grade) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.grade = grade;
	}


	/**
	 * Method to get the course code
	 * @return courseCode
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
	 * @return Course Name
	 */
	public String getcourseName() {
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
	 * Method to get the grade of the respective course
	 * @return grade
	 */
	public String getGrade() {
		return grade;
	}
	
	
	/**
	 * Method to set the grade of the respective course
	 * @param grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
}