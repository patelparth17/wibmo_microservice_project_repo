/**
 * @author parth.patel
 */
package com.wibmo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class SemRegisteration {
	
	int studenID;
	int sem;
	Date date;
	List<Course> courseList = new ArrayList<Course>();

	public SemRegisteration(int studentID, int sem, Date date) {
		this.studenID = studentID;
		this.sem = sem;
		this.date = date;
	}
	public SemRegisteration() {
		// TODO Auto-generated constructor stub
	}
	public int getStudenID() {
		return studenID;
	}
	public void setStudenID(int studenID) {
		this.studenID = studenID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSem() {
		return sem;
	}
	
	/**
	 * 
	 * @param sem
	 */
	public void setSem(int sem) {
		this.sem = sem;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Course> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<Course> courseList) {
		this.courseList = new ArrayList<Course>(courseList);
	}

}