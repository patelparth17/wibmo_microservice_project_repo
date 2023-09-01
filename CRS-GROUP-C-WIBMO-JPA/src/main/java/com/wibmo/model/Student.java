/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student extends User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="dept")
	private String department;
	
	@Column(name="studentID")
	private String studentId;
	
	@Column(name="gradYear")
	private int gradYear;
	
	@Column(name="isApproved")
	boolean isApproved;
	
	@Column(name="isReportGenerated")
	boolean isReportGenerated;

	@Column(name="isRegistered")
	boolean isRegistered;
	
	@Column(name="isPaid")
	boolean isPaid;
	
	/**
	 * Constructing User of a Student type.
	 * @param userId
	 * @param name
	 * @param role
	 * @param password
	 * @param gender
	 * @param address
	 * @param dept
	 * @param gradYear
	 * @param isApproved
	 */
	public Student(String userId, String name, String role, String password, String gender, String address,String dept,int gradYear,boolean isApproved
			,boolean isReportGenerated,boolean isRegistered, boolean isPaid) {
		super(userId, name, role, password,gender,address);
		this.department = dept;
		this.studentId = userId;
		this.gradYear = gradYear;
		this.isApproved = isApproved;
		this.isRegistered = isRegistered;
		this.isReportGenerated = isReportGenerated;
		this.isPaid = isPaid;
	}

	public Student() {
		
	}
	
	/**
	 * Method to get the department of student
	 * @return department
	 */
	public String getDepartment() 
	{
		return department;
	}
	
	/**
	 * Method to set the department of student
	 * @param department
	 */
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	
	/**
	 * Method to get the studentID
	 * @return studentID
	 */
	public String getStudentId() 
	{
		return studentId;
	}
	
	/**
	 * Method to set the studentID
	 * @param studentID
	 */
	public void setStudentId(String studentId) 
	{
		this.studentId = studentId;
	}
	
	/**
	 * Method to get the graduation year of student
	 * @return gradYear
	 */
	public int getGradYear() 
	{
		return gradYear;
	}
	
	/**
	 * Method to set the graduation year of student
	 * @param gradYear
	 */
	public void setGradYear(int gradYear) 
	{
		this.gradYear = gradYear;
	}
	
	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isReportGenerated() {
		return isReportGenerated;
	}

	public void setReportGenerated(boolean isReportGenerated) {
		this.isReportGenerated = isReportGenerated;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
}
