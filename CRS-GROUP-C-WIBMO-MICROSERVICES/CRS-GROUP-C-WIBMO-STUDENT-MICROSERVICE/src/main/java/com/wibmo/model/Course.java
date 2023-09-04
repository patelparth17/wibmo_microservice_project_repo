/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author parth.patel
 *
 */
@Entity
@Table(name="course")
public class Course implements Serializable
{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="courseID")
	private String courseId;
	
	@Column(name="courseCode")
	private String courseCode;
	
	@Column(name="courseName")
	private String courseName;
	
	@Column(name="professorID")
	private String professorID;
	
	@Column(name="seats")
	private int seats = 10;
	
	@Column(name="courseFee")
	private double fee;
	
	/**
	 * Default constructor
	 */
	public Course()
	{
		
	}
	
	/**
	 * Parametrized Constructor 
	 * @param courseCode
	 * @param courseName
	 * @param professorId
	 * @param seats
	 * @param fee
	 */
	public Course(String courseCode,String courseName,String professorId,int seats, double fee) {
		this.courseCode=courseCode;
		this.courseName=courseName;
		this.professorID=professorId;
		this.seats=seats;
		this.fee=fee;
	}
	/**
	 * Method to get Course Code
	 * @return Course Code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Method to set Course Code
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * Method to get Course Name
	 * @return Course Name
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Method to set Course Name
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Method to get available seats
	 * @return Seats available
	 */
	public int getSeats() {
		return seats;
	}
	
	/**
	 * Method to set available seats
	 * @param seats
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	/**
	 * Method to get Instructor Id of professor teaching the course
	 * @return Instructor Id
	 */
	public String getProfessorID() {
		return professorID;
	}
	
	/**
	 * Method to set Instructor Id of professor teaching the course
	 * @param professorID
	 */
	public void setProfessorID(String professorID) {
		this.professorID = professorID;
	}
	
	/**
	 * Method to get the respective course fees
	 * @return
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * Method to set the respective course fees
	 * @param fee
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}
}