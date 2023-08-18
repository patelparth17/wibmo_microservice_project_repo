/**
 * 
 */
package com.wibmo.business;

import java.sql.SQLException;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.RegistrationDAOImpl;
import com.wibmo.dao.RegistrationDAOInterface;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.validator.StudentValidator;

/**
 * 
 */
public class RegistrationImpl implements RegistrationInterface{
	
	private static volatile RegistrationImpl instance = null;

	private RegistrationImpl() {
	}

	/**
	 * Method to make Registration Operation Singleton
	 * 
	 * @return
	 */
	public static RegistrationImpl getInstance() {
		if (instance == null) {
			synchronized (RegistrationImpl.class) {
				instance = new RegistrationImpl();
			}
		}
		return instance;
	}

	RegistrationDAOInterface registrationDAOInterface = RegistrationDAOImpl.getInstance();

	/**
	 * Method to add Course selected by student 
	 * @param courseCode
	 * @param studentName
	 * @param courseList 
	 * @return boolean indicating if the course is added successfully
	 * @throws CourseNotFoundException
	 * @throws SeatNotAvailableException 
	 * @throws CourseLimitExceedException 
	 * @throws SQLException 
	 */
	@Override
	
	public boolean addCourse(String courseCode, String studentName,List<Course> availableCourseList) throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException 
	{
		if (registrationDAOInterface.numOfRegisteredCourses(studentName) > 6)
		{	
			throw new CourseLimitExceededException(6);
		}
		else if (registrationDAOInterface.isRegistered(courseCode, studentName)) 
		{
			return false;
		} 
		else if (!registrationDAOInterface.seatAvailable(courseCode)) 
		{
			throw new SeatNotAvailableException(courseCode);
		} 
		else if(!StudentValidator.isValidCourseCode(courseCode, availableCourseList))
		{
			throw new CourseNotFoundException(courseCode);
		}
		return registrationDAOInterface.addCourse(courseCode, studentName);

	}

	/**
	 *  Method to drop Course selected by student
	 * @param courseCode
	 * @param studentName
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	@Override
	
	public boolean dropCourse(String courseCode, String studentName,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
		  if(!StudentValidator.isRegistered(courseCode, studentName, registeredCourseList))
	        	throw new CourseNotFoundException(courseCode);	
		return registrationDAOInterface.dropCourse(courseCode, studentName);
	}

	/** Method for Fee Calculation for selected courses
	 * Fee calculation for selected courses
	 * @param studentName
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	@Override
	
	public double calculateFee(String studentName) throws SQLException {
		return registrationDAOInterface.calculateFee(studentName);
	}


	/**
	 * Method to view grade card for students
	 * @param studentName
	 * @return List of Student's Grades
	 * @throws SQLException 
	 */
	@Override
	
	public List<Grade> viewGradeCard(String studentName) throws SQLException {
		return registrationDAOInterface.viewGradeCard(studentName);
	}

	/**
	 *  Method to view the list of available courses
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewCourses(String studentName) throws SQLException {
		return registrationDAOInterface.viewCourses(studentName);
	}

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentName
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException {
		return registrationDAOInterface.viewRegisteredCourses(studentName);
	}
    
	/**
	 *  Method to check student registration status
	 * @param studentName
	 * @return boolean indicating if the student's registration status
	 * @throws SQLException
	 */
	@Override

	public boolean getRegistrationStatus(String studentName) throws SQLException {
		return registrationDAOInterface.getRegistrationStatus(studentName);
	}
	
	/**
	 * Method to set student registration status
	 * @param studentName
	 * @throws SQLException
	 */
	@Override
	
	public void setRegistrationStatus(String studentName) throws SQLException {
		registrationDAOInterface.setRegistrationStatus(studentName);
	}

	@Override
	public boolean isReportGenerated(String studentName) throws SQLException {	
		return registrationDAOInterface.isReportGenerated(studentName);
	}

	@Override
	public boolean getPaymentStatus(String studentName) throws SQLException 
	{
		return registrationDAOInterface.getPaymentStatus(studentName);	
	}

	@Override
	public void setPaymentStatus(String studentName, PaymentModeConstant modeOfPayment, double amount) throws SQLException{
		registrationDAOInterface.setPaymentStatus(studentName, modeOfPayment, amount);	
	}
}