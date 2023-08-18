/**
 * 
 */
package com.wibmo.business;

import java.sql.SQLException;
import java.util.List;
import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

/**
 * 
 */
public interface RegistrationInterface {
	public boolean addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException;

	/**
	 * Method to set student registration status
	 * @param studentId
	 * @throws SQLException
	 */
	void setRegistrationStatus(String studentName) throws SQLException;

	/**
	 *  Method to check student registration status
	 * @param studentId
	 * @return boolean indicating if the student's registration status
	 * @throws SQLException
	 */
	boolean getRegistrationStatus(String studentName) throws SQLException;
	
	boolean getPaymentStatus(String studentName) throws SQLException;

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	List<Course> viewRegisteredCourses(String studentName) throws SQLException;

	/**
	 *  Method to view the list of available courses
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	List<Course> viewCourses(String studentName) throws SQLException;

	/**
	 * Method to view grade card for students
	 * @param studentId
	 * @return List of Student's Grades
	 * @throws SQLException 
	 */
	List<Grade> viewGradeCard(String studentName) throws SQLException;

	/** Method for Fee Calculation for selected courses
	 * Fee calculation for selected courses
	 * @param studentId
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	double calculateFee(String studentName) throws SQLException;

	/**
	 *  Method to drop Course selected by student
	 * @param courseCode
	 * @param studentId
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	boolean dropCourse(String courseCode, String studentName, List<Course> registeredCourseList)
			throws CourseNotFoundException, SQLException;

	public boolean isReportGenerated(String studentName) throws SQLException;

	public void setPaymentStatus(String studentName, PaymentModeConstant mode, double amount) throws SQLException;
}
