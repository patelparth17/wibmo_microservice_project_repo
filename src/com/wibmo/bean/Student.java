/**
 * 
 */
package com.wibmo.bean;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

/**
 * @author parth.patel
 *
 */
public class Student extends User 
{
	private String department;
	private String studentId;
	private int gradYear;
	boolean isApproved;
	
	/**
	 * Constructing User of a Student type.
	 * @param userId
	 * @param name
	 * @param role
	 * @param password
	 * @param gender
	 * @param address
	 * @param dept
	 * @param studentId
	 * @param gradYear
	 * @param isApproved
	 */
	public Student(String userId, String name, RoleConstant role, String password, GenderConstant gender, String address,String dept,String studentId,int gradYear,boolean isApproved) {
		super(userId, name, role, password,gender,address);
		this.department = dept;
		this.studentId = studentId;
		this.gradYear = gradYear;
		this.isApproved = isApproved;
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
}
