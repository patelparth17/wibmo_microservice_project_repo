/**
 * 
 */
package com.wibmo.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.service.AdminOperationInterface;
import com.wibmo.service.NotificationInterface;
import com.wibmo.service.ProfessorInterface;
import com.wibmo.service.RegistrationInterface;


/**
 * 
 */
@RestController
@RequestMapping("/student")
public class StudentCRSMenuController {
	
	private static Logger logger = Logger.getLogger(StudentCRSMenuController.class);
	
	@Autowired
	RegistrationInterface registrationInterface;
	
	@Autowired
	AdminOperationInterface adminInterface;
	
	@Autowired
	ProfessorInterface professorInterface;
	
	@Autowired
	NotificationInterface notificationInterface;
	
	/**
	 * Method to view registered courses of student
	 * @param studentName
	 * @return List of registered courses
	 */
	@RequestMapping(value = "/viewRegisteredCourse/{studentName}", method = RequestMethod.GET)
	private List<Course> viewRegisteredCourse(@PathVariable("studentName") String studentName) {
		try {
			return registrationInterface.viewRegisteredCourses(studentName);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Method to view available courses
	 * @param studentName
	 * @return List of available courses
	 */
	@RequestMapping(value = "/viewCourse/{studentName}", method = RequestMethod.GET)
	private List<Course> viewCourse(@PathVariable("studentName") String studentName) {
		try {
			return registrationInterface.viewCourses(studentName);

		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Method to view gradecard of the student
	 * @param studentName
	 * @return List of registered courses with grades
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewGradeCard/{studentName}", method = RequestMethod.GET)
	private ResponseEntity viewGradeCard(@PathVariable("studentName") String studentName){
		try {
			return new ResponseEntity(registrationInterface.viewGradeCard(studentName), HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Method to check if student is already registered
	 * @param studentName
	 * @return Registration Status
	 */
	@RequestMapping(value = "/getRegistrationStatus/{studentName}")
	private boolean getRegistrationStatus(@PathVariable("studentName") String studentName)
	{
		try 
		{
			return registrationInterface.getRegistrationStatus(studentName);
		} 
		catch (SQLException e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Method to register courses
	 * @param studentName
	 * @param courseCode
	 * @return status message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/registerCourses", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT} )
	private ResponseEntity registerCourses( 
			@RequestParam String studentName, 
			@RequestBody List<String> courseCode) 
	{
		if(getRegistrationStatus(studentName))
		{
			return new ResponseEntity(" Registration is already completed", HttpStatus.NOT_IMPLEMENTED);
		}
		int indexOfUnregisteredCourse = (viewRegisteredCourse(studentName)==null)? 1:viewRegisteredCourse(studentName).size()+1;
		int index=0;
		List<String> messages = new ArrayList<>();
		while(indexOfUnregisteredCourse <= 4)
		{
			try
			{
				List<Course> courseList=viewCourse(studentName);
				if(courseList==null)
					return new ResponseEntity("Course list is empty!",HttpStatus.NOT_IMPLEMENTED);
				if(registrationInterface.addCourse(courseCode.get(index++),studentName,courseList))
				{
					messages.add("Course " + courseCode + " registered sucessfully.");
					indexOfUnregisteredCourse++;
				}
				else
				{
					messages.add(" You have already registered for Course : " + courseCode);
				}
			}	
			catch(CourseNotFoundException | CourseLimitExceededException | SQLException | SeatNotAvailableException e)
			{
				messages.add(e.getMessage());
			}
		}
		logger.debug("\n*******************************************************");
		logger.debug("        Primary Course Registration Successful");
		logger.debug("*******************************************************\n");
		
		
		int indexOfSecondaryCourse=1;
		while(indexOfSecondaryCourse <=2) {
			try {
				List<Course> secondarycourseList=viewCourse(studentName);
				if(secondarycourseList==null)
					return new ResponseEntity("Course list is empty!",HttpStatus.NOT_IMPLEMENTED);
				if(registrationInterface.addSecondaryCourse(courseCode.get(index++),studentName,secondarycourseList))
				{
					messages.add("Course " + courseCode + " registered sucessfully.");
					indexOfSecondaryCourse++;
				}
				else
				{
					messages.add(" You have already registered for Course : " + courseCode);
				}
			}	
			catch(CourseNotFoundException | SQLException | SeatNotAvailableException e)
			{
				messages.add(e.getMessage());
			}
		}

		logger.debug("\n*******************************************************");
		logger.debug("        Secondary Course Registration Successful");
		logger.debug("*******************************************************\n");

		try {
			registrationInterface.setRegistrationStatus(studentName);
		}
		catch(SQLException e)
		{
			messages.add(e.getMessage());
		}
		return new ResponseEntity("Course is registered for "+ studentName + "\n" + messages, HttpStatus.CREATED);
	}
	
	/**
	 * Method to add courses
	 * @param studentName
	 * @param courseCode
	 * @return status message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addCourse")
	private ResponseEntity addCourse(
			@RequestParam String studentName,
			@RequestParam String courseCode
			) {
		List<Course> courseList;
		try {
			courseList = registrationInterface.viewCourses(studentName);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		try {
			registrationInterface.addCourse(courseCode,studentName,courseList);
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException | SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course is registered for "+ studentName, HttpStatus.CREATED);
	}
	
	/**
	 * Method to drop course
	 * @param studentName
	 * @param courseCode
	 * @return status message
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dropCourse")
	private ResponseEntity dropCourse(
			@RequestParam String studentName,
			@RequestParam String courseCode
			) {
		List<Course> registeredCourseList;
		try {
			registeredCourseList = registrationInterface.viewRegisteredCourses(studentName);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		try {
			registrationInterface.dropCourse(courseCode, studentName, registeredCourseList);
		} catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course deleted for "+ studentName, HttpStatus.OK);
	}
	
	/**
	 * Method to make payment
	 * @param studentName
	 * @param mode
	 * @return status message
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/makePayment")
	private ResponseEntity makePayment(
			@RequestParam String studentName,
			@RequestParam String mode
			) {
		boolean isreg,ispaid = false;
		double fee = 0.0;
		try
		{
			isreg = registrationInterface.getRegistrationStatus(studentName);
			ispaid = registrationInterface.getPaymentStatus(studentName);
			fee=registrationInterface.calculateFee(studentName);
		}catch (SQLException e) 
		{
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}


		if(!isreg)
		{
			return new ResponseEntity("You have not registered yet",HttpStatus.NOT_IMPLEMENTED);
		}
		else if(isreg && !ispaid)
		{
			try 
			{
				PaymentModeConstant paymentMode = PaymentModeConstant.stringToPaymentMode(mode);
				registrationInterface.setPaymentStatus(studentName, paymentMode, fee);
				notificationInterface.sendNotification(NotificationTypeConstant.PAID, studentName, paymentMode, fee);	
				return new ResponseEntity("Payment Successful by studentName :" + studentName,HttpStatus.OK);	
			}catch (Exception e) 
			{
				return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity("You have already paid the fees",HttpStatus.NOT_IMPLEMENTED);
	}
}
