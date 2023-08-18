
package com.wibmo.dao;

import java.sql.SQLException;
import java.util.List;

import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.bean.*;

/**
 * 
 */
public interface RegistrationDAOInterface {
	/**
	 * Method to add course in database
	 * @param courseCode
	 * @param studentName
	 * @return boolean indicating if the course is added successfully
	 */
	public boolean addCourse(String courseCode, String studentName) throws SQLException;

	
	/**
	 * Drop Course selected by student
	 * @param courseCode
	 * @param studentName
	 * @return boolean indicating if the course is dropped successfully
	 */
	
	public boolean dropCourse(String courseCode, String studentName) throws SQLException;

	/**
	 * Method to get the list of courses available from course catalog 
	 * @param studentName
	 * @return list of Courses
	 */
	public List<Course> viewCourses(String studentName) throws SQLException;

	
	/**
	 * Method to View list of Registered Courses
	 * @param studentName
	 * @return list of Registered Courses
	 */
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException;

	
	/**
	 * Method to view grade card of the student
	 * @param studentName
	 * @return GradeConstant Card
	 */
	
	public double calculateFee(String studentName) throws SQLException;

	/**
	 * Check if seat is available for that particular course
	 * @param courseCode
	 * @return seat availability status
	 */
	public boolean seatAvailable(String courseCode) throws SQLException;

	/**
	 * Method to get the list of courses registered by the student
	 * Number of registered courses for a student
	 * @param studentName
	 * @return Number of registered Courses
	 */
	public int numOfRegisteredCourses(String studentName) throws SQLException;

	/**
	 * Method checks if the student is registered for that course
	 * @param courseCode
	 * @param studentName
	 * @return Students registration status
	 */
	public boolean isRegistered(String courseCode, String studentName) throws SQLException;

	
	/**
	 *  Method to get student registration status
	 * @param studentName
	 * @return Student's registration status
	 */
	public boolean getRegistrationStatus(String studentName) throws SQLException;
	
	public boolean getPaymentStatus(String studentName) throws SQLException;

	/**
	 *  Method to set student registration status
	 * @param studentName
	 */
	public void setRegistrationStatus(String studentName) throws SQLException;


	/**
	 * Method to view grade card of the student
	 * @param studentName
	 * @throws SQLException 
	 * @return Studen's grade card
	 */
	public List<Grade> viewGradeCard(String studentName) throws SQLException;

	public boolean isReportGenerated(String studentName) throws SQLException;

	public void setPaymentStatus(String studentId, PaymentModeConstant modeOfPayment, double amount) throws SQLException;
}
