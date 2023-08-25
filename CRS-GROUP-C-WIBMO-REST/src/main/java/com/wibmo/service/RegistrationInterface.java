/**
 * 
 */
package com.wibmo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseLimitExceededForPrimaryException;
import com.wibmo.exception.CourseLimitExceededForSecondaryException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.CourseSizeViolation;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentAlreadyRegistered;

/**
 * Interface of methods which calls Registration DAO methods 
 */
@Service
public interface RegistrationInterface {
	
	/**
	 * Method to add available course
	 * @param courseCode
	 * @param studentName
	 * @param availableCourseList
	 * @return status
	 * @throws CourseNotFoundException
	 * @throws CourseLimitExceededException
	 * @throws SeatNotAvailableException
	 * @throws SQLException
	 */
	public boolean addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException;

	/**
	 * Method to set student registration status
	 * @param studentName
	 * @throws SQLException
	 */
	public void setRegistrationStatus(String studentName) throws SQLException;

	/**
	 * Method to check student registration status
	 * @param studentName
	 * @return boolean indicating the student's registration status
	 * @throws SQLException
	 */
	public boolean getRegistrationStatus(String studentName) throws SQLException;
	
	/**
	 * Method to check student payment status
	 * @param studentName
	 * @return status
	 * @throws SQLException
	 */
	public boolean getPaymentStatus(String studentName) throws SQLException;

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException;

	/**
	 * Method to view the list of available courses
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	public List<Course> viewCourses(String studentName) throws SQLException;

	/**
	 * Method to view grade card for students
	 * @param studentName
	 * @return List of Student's Grades
	 * @throws SQLException 
	 */
	List<Grade> viewGradeCard(String studentName) throws SQLException;

	/** 
	 * Method for Fee Calculation for selected courses
	 * @param studentName
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	public double calculateFee(String studentName) throws SQLException;

	/**
	 * Method to drop Course selected by student
	 * @param courseCode
	 * @param studentName
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	public boolean dropCourse(String courseCode, String studentName, List<Course> registeredCourseList)
			throws CourseNotFoundException, SQLException;

	/**
	 * Method to check student report generated status
	 * @param studentName
	 * @return status
	 * @throws SQLException
	 */
	public boolean isReportGenerated(String studentName) throws SQLException;

	/**
	 * Method to set the student payment status
	 * @param studentName
	 * @param mode
	 * @param amount
	 * @throws SQLException
	 */
	public void setPaymentStatus(String studentName, PaymentModeConstant mode, double amount) throws SQLException;
	
	/**
	 * Method to add secondary course
	 * @param courseCode
	 * @param studentName
	 * @param secondarycourseList
	 * @return status
	 * @throws SQLException
	 * @throws SeatNotAvailableException
	 * @throws CourseNotFoundException
	 */
	public boolean addSecondaryCourse(String courseCode, String studentName, List<Course> secondarycourseList) throws SQLException, SeatNotAvailableException, CourseNotFoundException;
	
	/**
	 * Method for course registration
	 * @param studentName
	 * @param availableCourseList
	 * @return status
	 * @throws CourseNotFoundException
	 * @throws SeatNotAvailableException
	 * @throws SQLException
	 * @throws CourseSizeViolation
	 * @throws CourseLimitExceededForPrimaryException
	 * @throws CourseLimitExceededForSecondaryException
	 * @throws StudentAlreadyRegistered
	 */
	public boolean registerCourse(String studentName, List<String> availableCourseList) throws CourseNotFoundException, SeatNotAvailableException, SQLException,CourseSizeViolation, CourseLimitExceededForPrimaryException,CourseLimitExceededForSecondaryException, StudentAlreadyRegistered;
}
