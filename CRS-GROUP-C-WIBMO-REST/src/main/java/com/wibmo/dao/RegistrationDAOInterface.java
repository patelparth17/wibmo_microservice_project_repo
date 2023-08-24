
package com.wibmo.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;

@Repository
public interface RegistrationDAOInterface {
	
	/**
	 * Method to add course in database
	 * @param courseCode
	 * @param studentName
	 * @return boolean indicating if the course is added successfully
	 * @throws SQLException
	 */
	public boolean addCourse(String courseCode, String studentName) throws SQLException;

	
	/**
	 * Method to drop course selected by student
	 * @param courseCode
	 * @param studentName
	 * @return boolean indicating if the course is dropped successfully
	 * @throws SQLException
	 */
	
	public boolean dropCourse(String courseCode, String studentName) throws SQLException;

	/**
	 * Method to get the list of courses available from course catalog 
	 * @param studentName
	 * @return list of Courses
	 * @throws SQLException
	 */
	public List<Course> viewCourses(String studentName) throws SQLException;

	
	/**
	 * Method to View list of Registered Courses
	 * @param studentName
	 * @return list of Registered Courses
	 * @throws SQLException
	 */
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException;

	
	/**
	 * Method to calculate fee
	 * @param studentName
	 * @return fee
	 * @throws SQLException
	 */
	
	public double calculateFee(String studentName) throws SQLException;

	/**
	 * Check if seat is available for that particular course
	 * @param courseCode
	 * @return seat availability status
	 * @throws SQLException
	 */
	public boolean seatAvailable(String courseCode) throws SQLException;

	/**
	 * Method to get the number of courses registered by the student
	 * Number of registered courses for a student
	 * @param studentName
	 * @return Number of registered Courses
	 * @throws SQLException
	 */
	public int numOfRegisteredCourses(String studentName) throws SQLException;

	/**
	 * Method checks if the student is registered for that course
	 * @param courseCode
	 * @param studentName
	 * @return Students registration status
	 * @throws SQLException
	 */
	public boolean isRegistered(String courseCode, String studentName) throws SQLException;

	
	/**
	 *  Method to get student registration status
	 * @param studentName
	 * @return Student's registration status
	 * @throws SQLException
	 */
	public boolean getRegistrationStatus(String studentName) throws SQLException;
	
	/**
	 * Method to get the payment status of student
	 * @param studentName
	 * @return status
	 * @throws SQLException
	 */
	public boolean getPaymentStatus(String studentName) throws SQLException;

	/**
	 * Method to set student registration status
	 * @param studentName
	 * @throws SQLException
	 */
	public void setRegistrationStatus(String studentName) throws SQLException;


	/**
	 * Method to view grade card of the student
	 * @param studentName
	 * @throws SQLException 
	 * @return Student's grade card
	 */
	public List<Grade> viewGradeCard(String studentName) throws SQLException;

	/**
	 * Method to check student report generated status
	 * @param studentName
	 * @return status
	 * @throws SQLException
	 */
	public boolean isReportGenerated(String studentName) throws SQLException;

	/**
	 * Method to set the payment status of student
	 * @param studentId
	 * @param modeOfPayment
	 * @param amount
	 * @throws SQLException
	 */
	public void setPaymentStatus(String studentId, PaymentModeConstant modeOfPayment, double amount) throws SQLException;


	public int numSecondaryCourses(String studentName);


	public boolean addSecondaryCourse(String courseCode, String studentName);
}
