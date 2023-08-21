/**
 * @author parth.patel
 */
package com.wibmo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class SemRegisteration {
	
	int studentID;
	int sem;
	List<Course> courseList = new ArrayList<Course>();

	/**
	 * Parametrized Constructor
	 * @param studentID
	 * @param sem
	 */
	public SemRegisteration(int studentID, int sem) {
		this.studentID = studentID;
		this.sem = sem;
	}
	
	/**
	 * Method to get the student ID
	 * @return studentID
	 */
	public int getstudentID() {
		return studentID;
	}
	
	/**
	 * Method to set the student ID
	 * @param studentID
	 */
	public void setstudentID(int studentID) {
		this.studentID = studentID;
	}
	
	/**
	 * Method to get the semester 
	 * @return sem
	 */
	public int getSem() {
		return sem;
	}
	
	/**
	 * Method to set the semester 
	 * @param sem
	 */
	public void setSem(int sem) {
		this.sem = sem;
	}
	
	/**
	 * Method to get the courselist
	 * @return courseList
	 */
	public List<Course> getCourseList() {
		return courseList;
	}
	
	/**
	 * Method to set the course list
	 * @param courseList
	 */
	public void setCourseList(List<Course> courseList) {
		this.courseList = new ArrayList<Course>(courseList);
	}
}