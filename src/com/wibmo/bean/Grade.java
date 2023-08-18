/**
 * 
 */
package com.wibmo.bean;

/**
 * 
 */
public class Grade {
	
	private String courseCode;
	private String courseName;
	private String grade;
	
	public Grade(String courseCode, String courseName, String grade) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.grade = grade;
	}


	/**
	 * @return the courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	
	/**
	 * @param crsCode the courseCode to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	
	/**
	 * @return the courseName
	 */
	public String getcourseName() {
		return courseName;
	}
	
	
	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	
	
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
