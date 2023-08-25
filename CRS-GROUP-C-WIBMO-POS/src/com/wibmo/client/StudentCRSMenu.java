package com.wibmo.client;

import java.util.*;

import org.apache.log4j.Logger;

import java.sql.*;

import com.wibmo.bean.*;
import com.wibmo.constants.*;
import com.wibmo.exception.*;
import com.wibmo.business.*;

public class StudentCRSMenu {
	
	private static Logger logger = Logger.getLogger(StudentCRSMenu.class);
	Scanner sc = new Scanner(System.in);
	RegistrationInterface registrationInterface = RegistrationImpl.getInstance();
	AdminOperationInterface adminInterface = AdminOperationImpl.getInstance();
	ProfessorInterface professorInterface = ProfessorImpl.getInstance();
	NotificationInterface notificationInterface = NotificationImpl.getInstance();
	private boolean is_registered;
	
	/**
	 * Method to create Student menu
	 * @param studentName
	 */
	public void createMenu(String studentName) {
		is_registered = getRegistrationStatus(studentName);
		while(CRSApplication.loggedin) {

			logger.info("*****************************");
			logger.info("**********Student Menu*********");
			logger.info("*****************************");
			logger.info("1. Course Registration");
			logger.info("2. Add Course");
			logger.info("3. Drop Course");
			logger.info("4. View Available Course");
			logger.info("5. View Registered Courses");
			logger.info("6. View grade card");
			logger.info("7. Make Payment");
			logger.info("8. Logout");
			logger.info("*****************************");

			int choice = sc.nextInt();
			switch (choice) {
			case 1: 
				registerCourses(studentName);
				break;

			case 2:
				addCourse(studentName);
				break;

			case 3:
				dropCourse(studentName);
				break;

			case 4:
				viewCourse(studentName);
				break;

			case 5:
				viewRegisteredCourse(studentName);
				break;

			case 6:
				viewGradeCard(studentName);
				break;

			case 7:
				make_payment(studentName);
				break;

			case 8:
				CRSApplication.loggedin = false;
				break;			

			default:
				logger.debug("Incorrect Choice!");
			}
		}
	}
	
	/**
	 * Method to check if student is already registered
	 * @param studentName
	 * @return Registration Status
	 */
	private boolean getRegistrationStatus(String studentName)
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
	 * Method to register for courses
	 * @param studentName
	 */
	private void registerCourses(String studentName)
	{
		if(is_registered)
		{
			logger.debug(" Registration is already completed");
			return;
		}
		int indexOfUnregisteredCourse;
		if(viewRegisteredCourse(studentName)==null) {
			indexOfUnregisteredCourse=1;
		}
		else
			indexOfUnregisteredCourse = viewRegisteredCourse(studentName).size()+1 ;
		while(indexOfUnregisteredCourse <= 4)
		{
			try
			{
				List<Course> courseList=viewCourse(studentName);
				if(courseList==null)
					return;
				logger.debug(String.format("Enter Course Code for Course %d: ", indexOfUnregisteredCourse));
				String courseCode = sc.next();
				if(registrationInterface.addCourse(courseCode,studentName,courseList))
				{
					logger.debug("Course " + courseCode + " registered sucessfully.");
					indexOfUnregisteredCourse++;
				}
				else
				{
					logger.debug(" You have already registered for Course : " + courseCode);
				}
			}	
			catch(CourseNotFoundException | CourseLimitExceededException | SQLException | SeatNotAvailableException e)
			{
				logger.error(e.getMessage());
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
					return;
				logger.debug(String.format("Enter Course Code for Secondary Course %d: ", indexOfSecondaryCourse));
				String courseCode = sc.next();
				if(registrationInterface.addSecondaryCourse(courseCode,studentName,secondarycourseList))
				{
					logger.debug("Course " + courseCode + " registered sucessfully.");
					indexOfSecondaryCourse++;
				}
				else
				{
					logger.debug(" You have already registered for Course : " + courseCode);
				}
			}	
			catch(CourseNotFoundException | SQLException | SeatNotAvailableException e)
			{
				logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
		is_registered = true;
	}
	
	/**
	 * Method to add course 
	 * @param studentName
	 */
	private void addCourse(String studentName) {
		if(is_registered)
		{
			List<Course> availableCourseList=viewCourse(studentName);
			if(availableCourseList==null)
				return;

			try
			{
				logger.info("Enter Course Code : " );
				String courseCode = sc.next();
				if(registrationInterface.addCourse(courseCode, studentName,availableCourseList))
				{
					logger.debug(" You have successfully registered for Course : " + courseCode);
				}
				else
				{
					logger.debug(" You have already registered for Course : " + courseCode);
				}
			}
			catch(SQLException e){
				logger.error(e.getMessage());
			}catch(CourseNotFoundException e) {
				logger.error(e.getMessage());
			}catch(CourseLimitExceededException e) {
				logger.error(e.getMessage());
			}catch(SeatNotAvailableException e) {
				logger.error(e.getMessage());
			}
		}
		else 
		{
			logger.debug("Please complete registration");
		}
	}

	/**
	 * Method to drop Course
	 * @param studentName
	 */
	private void dropCourse(String studentName) {
		if(is_registered)
		{
			List<Course> registeredCourseList=viewRegisteredCourse(studentName);
			if(registeredCourseList==null)
				return;
			logger.debug("Enter the Course Code to drop: ");
			String courseCode = sc.next();
			try
			{
				registrationInterface.dropCourse(courseCode, studentName,registeredCourseList);
				logger.debug("You have successfully dropped Course : " + courseCode);

			}
			catch(CourseNotFoundException e)
			{
				logger.error("You have not registered for course : " + e.getCourseCode());
			} 
			catch (SQLException e) 
			{
				logger.error(e.getMessage());
			}
		}
		else
		{
			logger.debug("Please complete registration");
		}
	}

	/**
	 * Method to pay fee
	 * @param studentName
	 */
	private void make_payment(String studentName)
	{
		double fee = 1000.0;
		boolean isreg = false;
		boolean ispaid = false;
		try
		{
			isreg = registrationInterface.getRegistrationStatus(studentName);
			ispaid = registrationInterface.getPaymentStatus(studentName);
			fee=registrationInterface.calculateFee(studentName);
		}catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}


		if(!isreg)
		{
			logger.debug("You have not registered yet");
		}
		else if(isreg && !ispaid)
		{
			logger.debug("Your total fee  = " + fee);
			logger.debug("Want to continue Fee Payment(y/n)");
			String ch = sc.next();
			if(ch.equals("y"))
			{
				logger.debug("Select Mode of Payment:");

				int index = 1;
				for(PaymentModeConstant mode : PaymentModeConstant.values())
				{
					logger.debug(index + " " + mode);
					index = index + 1;
				}

				PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(sc.nextInt());

				if(mode == null)
					logger.debug("Invalid Input");
				else
				{
					try 
					{
						registrationInterface.setPaymentStatus(studentName, mode, fee);
						logger.debug("Payment Successful by studentName :" + studentName);
						notificationInterface.sendNotification(NotificationTypeConstant.PAID, studentName, mode, fee);		
					}
					catch (Exception e) 
					{
						logger.error(e.getMessage());
					}
				}
			}
		}
		else
		{
			logger.debug("You have already paid the fees");
		}
	}

	/**
	 * Method to view all available Courses 
	 * @param studentName
	 * @return List of available Courses 
	 */
	private List<Course> viewCourse(String studentName){
		List<Course> course_available=null;
		try 
		{
			course_available = registrationInterface.viewCourses(studentName);
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		if(course_available.isEmpty())
		{
			logger.debug("NO COURSE AVAILABLE");
			return null;
		}
		logger.debug("List of Available Courses:");
		logger.debug(String.format("%-20s %-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "INSTRUCTOR", "SEATS"));
		for(Course obj : course_available)
		{
			logger.debug(String.format("%-20s %-20s %-20s %-20s",obj.getCourseCode(), obj.getCourseName(),obj.getInstructorId(), obj.getSeats()));
		}

		return course_available;
	}

	/**
	 * Method to view grade card for particular student  
	 * @param studentName
	 */
	private void viewGradeCard(String studentName) {
		List<Grade> grade_card=null;
		boolean isReportGenerated = false;

		try 
		{
			isReportGenerated = registrationInterface.isReportGenerated(studentName);
			if(isReportGenerated) {
				grade_card = registrationInterface.viewGradeCard(studentName);
				logger.debug(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "GRADE"));

				if(grade_card.isEmpty())
				{
					logger.debug("You haven't registered for any course");
					return;
				}

				for(Grade obj : grade_card)
				{
					logger.debug(String.format("%-20s %-20s %-20s",obj.getCourseCode(), obj.getcourseName(),obj.getGrade()));
				}
			}
			else
				logger.debug("Report card not yet generated");
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
	}

	/**
	 * Method to view Registered Courses
	 * @param studentName
	 * @return List of Registered Courses
	 */
	private List<Course> viewRegisteredCourse(String studentName){
		List<Course> course_registered=null;
		try 
		{
			course_registered = registrationInterface.viewRegisteredCourses(studentName);
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}

		if(course_registered.isEmpty())
		{
			logger.debug("You haven't registered for any course");
			return null;
		}

		logger.debug(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "INSTRUCTOR"));
		for(Course obj : course_registered)
		{
			logger.debug(String.format("%-20s %-20s %-20s ",obj.getCourseCode(), obj.getCourseName(),obj.getInstructorId()));
		}
		return course_registered;
	}
}
