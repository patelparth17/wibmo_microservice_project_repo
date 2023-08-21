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


public class RegistrationImpl implements RegistrationInterface{
	private static volatile RegistrationImpl instance = null;
	private RegistrationImpl() {}

	/**
	 * Method to make RegistrationImpl Singleton
	 * @return instance
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

	@Override
	public boolean dropCourse(String courseCode, String studentName,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
		  if(!StudentValidator.isRegistered(courseCode, studentName, registeredCourseList))
	        	throw new CourseNotFoundException(courseCode);	
		return registrationDAOInterface.dropCourse(courseCode, studentName);
	}

	@Override
	public double calculateFee(String studentName) throws SQLException {
		return registrationDAOInterface.calculateFee(studentName);
	}

	@Override
	public List<Grade> viewGradeCard(String studentName) throws SQLException {
		return registrationDAOInterface.viewGradeCard(studentName);
	}

	@Override
	public List<Course> viewCourses(String studentName) throws SQLException {
		return registrationDAOInterface.viewCourses(studentName);
	}

	@Override
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException {
		return registrationDAOInterface.viewRegisteredCourses(studentName);
	}
    
	@Override
	public boolean getRegistrationStatus(String studentName) throws SQLException {
		return registrationDAOInterface.getRegistrationStatus(studentName);
	}
	
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