/**
 * 
 */
package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.constants.*;
import com.wibmo.exception.*;

import com.wibmo.business.*;


public class CRSApplication {
	
	 private static Logger logger = Logger.getLogger(CRSApplication.class);
	static boolean loggedin = false;
	StudentInterface studentInterface=StudentImpl.getInstance();
	UserInterface userInterface =UserImpl.getInstance();
	NotificationInterface notificationInterface = NotificationImpl.getInstance();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CRSApplication crsApplication=new CRSApplication();
		createMainMenu();
		int userInput;
		userInput=sc.nextInt();
		try
		{
			while(userInput!=4)
			{
				switch(userInput)
				{	
					case 1:
						//login
						crsApplication.loginUser();
						break;
					case 2:
						//student registration
						crsApplication.registerStudent();
						break;	
					case 3:
						crsApplication.updatePassword();
						break;
					default:
						logger.debug("Invalid Input");
				}
				createMainMenu();
				userInput=sc.nextInt();
			}
		}catch(Exception ex)
		{
			logger.debug("Error occured "+ex);
		}
		finally
		{
			sc.close();
		}
	}
	
	/**
	 * Method to Create Main Menu
	 */
	public static void createMainMenu()
	{
		LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        logger.info("---------------------------------------------------------------");
		logger.info("               Welcome to Course Management System");
		logger.info(String.format("%35s %5s %n",localDate.format(DateTimeFormatter.ofPattern("dd-LLLL-yyyy")),localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		logger.info("---------------------------------------------------------------");
		logger.info("1. Login");
		logger.info("2. Student Registration");
		logger.info("3. Update password");
		logger.info("4. Exit");
		logger.info("Enter user input");
	}
	
	/**
	 * Method for Login functionality
	 */
	public void loginUser()
	{
		Scanner in = new Scanner(System.in);
		String username, password, role;
		try
		{
			logger.info("-----------------Login------------------");
			logger.info("Username:");
			username = in.next();
			logger.info("Password:");
			password = in.next();
			logger.info("Role:");
			role = in.next();
			loggedin = userInterface.authenticateUser(username, password, role);

			//true->role->student->approved
			if(loggedin)
			{
				 role = userInterface.getRole(username);
				 switch(role) {
				 	case "ADMIN":
				 		logger.debug("Login Successful as an Admin");
				 		AdminCRSMenu adminMenu = new AdminCRSMenu();
				 		adminMenu.createMenu();
				 		break;
				 	case "PROFESSOR":
				 		logger.debug("Login Successful as a Professor");
				 		ProfessorCRSMenu professorMenu=new ProfessorCRSMenu();
				 		professorMenu.createMenu(username);
				 		break;
				 	case "STUDENT":
						boolean isApproved=studentInterface.isApproved(username);
						if(isApproved) {
							logger.debug("Login Successful as a Student");
							StudentCRSMenu studentMenu=new StudentCRSMenu();
							studentMenu.createMenu(username);	
						} else {
							logger.debug("Failed to login, you have not been approved by the administration!");
							loggedin=false;
						}
				 		break;
				 	}
				}
				else
					logger.debug("Invalid Credentials!");			
		}catch(UserNotFoundException ex)
		{
			logger.error(ex.getMessage());
		}
	}
	
	/**
	 * Method to help Student register themselves, pending admin approval
	 */
	public void registerStudent()
	{
		Scanner sc=new Scanner(System.in);

		String userId,name,password,address,dept;
		GenderConstant gender;
		int genderChoice, gradYear;
		try
		{
			//input all the student details
			logger.info("---------------Student Registration-------------");
			logger.info("Name:");
			name=sc.nextLine();
			logger.info("UserID:");
			userId=sc.next();
			logger.info("Password:");
			password=sc.next();
			logger.info("Enter Gender Choice: \t 1: Male \t 2.Female\t 3.Other");
			genderChoice=sc.nextInt();
			sc.nextLine();
			
			switch(genderChoice)
			{
			case 1:
				gender=GenderConstant.MALE;
				break;
			case 2:
				gender=GenderConstant.FEMALE;
				break;
				
			case 3:
				gender=GenderConstant.OTHER;
				break;
			default: 
				gender=GenderConstant.OTHER;
			}
			
			logger.info("Department:");
			dept=sc.nextLine();
			logger.info("Graduation Year:");
			gradYear=sc.nextInt();
			sc.nextLine();
			logger.info("Address:");
			address=sc.nextLine();
			
			studentInterface.register(name, userId, password, gender, gradYear, dept, address);
			notificationInterface.sendNotification(NotificationTypeConstant.REGISTERATION, name, null,0);
			
		}
		catch(StudentNotRegisteredException ex)
		{
			logger.error("Something went wrong! "+ex.getStudentName() +" not registered. Please try again");
		}
	}
	
	/**
	 * Method to update password of User
	 */
	public void updatePassword() {
		Scanner in = new Scanner(System.in);
		String username,newPassword;
		try {
			logger.info("------------------Update Password--------------------");
			logger.info("Username:");
			username=in.next();
			logger.info("New Password:");
			newPassword=in.next();
			boolean isUpdated=userInterface.updatePassword(username, newPassword);
			if(isUpdated)
				logger.debug("Password updated successfully!");

			else
				logger.debug("Something went wrong, please try again!");
		} catch(Exception ex) {
			logger.error("Error Occured "+ex.getMessage());
		}
		
	}
		
}