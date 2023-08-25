/**
 * 
 */
package com.wibmo.dao;

import org.springframework.stereotype.Repository;

import com.wibmo.bean.Student;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * Interface of methods which performs read and write operations of Student in Database
 */
@Repository
public interface StudentDAOInterface {
	
	/**
	 * Method to add student to database
	 * @param student: student object containing all the fields
	 * @throws StudentNotRegisteredException 
	 */
	public void registerStudent(Student student) throws StudentNotRegisteredException;
	
	
	/**
	 * Method to check if Student is approved
	 * @param studentName
	 * @return boolean indicating if student is approved
	 */
	public boolean isApproved(String studentName);
}
