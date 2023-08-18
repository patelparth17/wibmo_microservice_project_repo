/**
 * 
 */
package com.wibmo.dao;

import com.wibmo.bean.Student;

/**
 * 
 */
public interface StudentDAOInterface {
	/**
	 * Method to add student to database
	 * @param student: student object containing all the fields
	 * @return true if student is added, else false
	 */
	public String registerStudent(Student student);
	
	
	/**
	 * Method to retrieve Student Id from User Id
	 * @param userId
	 * @return Student Id
	 */
	public String getStudentId(String userId);
	
	/**
	 * Method to check if Student is approved
	 * @param studentId
	 * @return boolean indicating if student is approved
	 */
	public boolean isApproved(String studentName);
}
