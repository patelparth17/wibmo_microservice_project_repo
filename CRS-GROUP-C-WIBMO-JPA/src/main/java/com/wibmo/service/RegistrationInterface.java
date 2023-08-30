/**
 * 
 */
package com.wibmo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.model.Course;
import com.wibmo.model.Grade;
import com.wibmo.exception.CourseAlreadyRegisteredException;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseLimitExceededForPrimaryException;
import com.wibmo.exception.CourseLimitExceededForSecondaryException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.CourseSizeViolation;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentAlreadyRegistered;
import com.wibmo.exception.UserNotFoundException;

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
	 * @throws CourseAlreadyRegisteredException 
	 * @throws SQLException
	 */
	public int addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, CourseAlreadyRegisteredException;

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	public List<Course> viewRegisteredCourses(String studentName);

	/**
	 * Method to view the list of available courses
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	public List<Course> viewCourses(String studentName);

	/**
	 * Method to view grade card for students
	 * @param studentName
	 * @return List of Student's Grades
	 * @throws SQLException 
	 */
	List<Grade> viewGradeCard(String studentName);

	/** 
	 * Method for Fee Calculation for selected courses
	 * @param studentName
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	public double calculateFee(String studentName);

	/**
	 * Method to drop Course selected by student
	 * @param courseCode
	 * @param studentName
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	public void dropCourse(String courseCode, String studentName, List<Course> registeredCourseList)
			throws CourseNotFoundException;


	/**
	 * Method to add secondary course
	 * @param courseCode
	 * @param studentName
	 * @return status
	 * @throws SQLException
	 * @throws SeatNotAvailableException
	 * @throws CourseNotFoundException
	 */
	public boolean addSecondaryCourse(String courseCode, String studentName) throws SeatNotAvailableException, CourseNotFoundException;
	
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
	public boolean registerCourse(String studentName, List<String> availableCourseList) throws UserNotFoundException, CourseNotFoundException, SeatNotAvailableException, SQLException,CourseSizeViolation, CourseLimitExceededForPrimaryException,CourseLimitExceededForSecondaryException, StudentAlreadyRegistered;

}
