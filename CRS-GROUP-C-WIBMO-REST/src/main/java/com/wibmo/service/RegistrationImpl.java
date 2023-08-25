/**
 * 
 */
package com.wibmo.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.RegistrationDAOInterface;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseLimitExceededForPrimaryException;
import com.wibmo.exception.CourseLimitExceededForSecondaryException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.CourseSizeViolation;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentAlreadyRegistered;
import com.wibmo.validator.StudentValidator;

/**
 * Implementation of methods which calls Registration DAO methods 
 */
@Service
public class RegistrationImpl implements RegistrationInterface{
	
	@Autowired
	private RegistrationDAOInterface registrationDAOInterface;
	
	private static Logger logger = Logger.getLogger(RegistrationImpl.class);

	@Override
	public boolean addCourse(String courseCode, String studentName,List<Course> availableCourseList) throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException 
	{
		if (registrationDAOInterface.numOfRegisteredCourses(studentName) >= 4 && registrationDAOInterface.numSecondaryCourses(studentName)>=2)
		{	
			throw new CourseLimitExceededException(4);
		} 
		else if(registrationDAOInterface.numSecondaryCourses(studentName)<2) {
			return registrationDAOInterface.addSecondaryCourse(courseCode, studentName);
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
	
	@Override
	public boolean addSecondaryCourse(String courseCode, String studentName, List<Course> secondarycourseList) throws SQLException, SeatNotAvailableException, CourseNotFoundException {
		if (!registrationDAOInterface.seatAvailable(courseCode)) 
		{
			throw new SeatNotAvailableException(courseCode);
		} 
		else if(!StudentValidator.isValidCourseCode(courseCode, secondarycourseList))
		{
			throw new CourseNotFoundException(courseCode);
		}
		return registrationDAOInterface.addSecondaryCourse(courseCode, studentName);
	}
	
	@Override

    public boolean registerCourse(String studentName, List<String> courseList) throws SQLException, CourseSizeViolation, CourseLimitExceededForPrimaryException, CourseLimitExceededForSecondaryException, StudentAlreadyRegistered {
        if(courseList.size() != 6) {
            throw new CourseSizeViolation();
        }else if (registrationDAOInterface.numOfRegisteredCourses(studentName) > 4 && registrationDAOInterface.numSecondaryCourses(studentName) <= 2){
            throw new CourseLimitExceededForPrimaryException();
        }else if(registrationDAOInterface.numOfRegisteredCourses(studentName) <= 4 && registrationDAOInterface.numSecondaryCourses(studentName) > 2) {
            throw new CourseLimitExceededForSecondaryException();
        }
        else if(getRegistrationStatus(studentName))
        {
            throw new StudentAlreadyRegistered(studentName);
        }
        int indexOfUnregisteredCourse = (viewRegisteredCourses(studentName)==null)? 1:viewRegisteredCourses(studentName).size()+1;
        int index=0;
        while(indexOfUnregisteredCourse <= 4)
        {
                if(registrationDAOInterface.addCourse(courseList.get(index),studentName))
                {
                    logger.info("You have successfully enrolled for "+ courseList.get(index));
                    indexOfUnregisteredCourse++;
                }
                else
                {
                    logger.info(" You have already registered for Course : " + courseList.get(index));
                }
                index++;
        }
        logger.debug("\n*******************************************************");
        logger.debug("        Primary Course Registration Successful");
        logger.debug("*******************************************************\n");
        

        int indexOfSecondaryCourse=1;
        while(indexOfSecondaryCourse <=2) {
                if(registrationDAOInterface.addSecondaryCourse(courseList.get(index),studentName))
                {
                    logger.info("You have successfully enrolled for "+ courseList.get(index));
                    indexOfSecondaryCourse++;
                }
                else
                {
                    logger.info(" You have already registered for Course : " + courseList.get(index));
                }
                index++;
        }
        logger.debug("\n*******************************************************");
        logger.debug("        Secondary Course Registration Successful");
        logger.debug("*******************************************************\n");
        registrationDAOInterface.setRegistrationStatus(studentName);
        return false;

    }
}