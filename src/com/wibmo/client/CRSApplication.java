/**
 * 
 */
package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.wibmo.constants.*;
import com.wibmo.exception.*;

import com.wibmo.business.*;


public class CRSApplication {
	static boolean loggedin = false;
<<<<<<< Updated upstream
//	StudentInterface studentInterface=StudentOperation.getInstance();
=======
	StudentInterface studentInterface=StudentImpl.getInstance();
>>>>>>> Stashed changes
	UserInterface userInterface =UserOperation.getInstance();
	NotificationInterface notificationInterface = NotificationImpl.getInstance();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CRSApplication crsApplication=new CRSApplication();
		createMainMenu();
		int userInput;
		userInput=sc.nextInt();
		try
		{
<<<<<<< Updated upstream
			
		//until user do not exit the application
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
//					crsApplication.registerStudent();
					break;	
				case 3:
					crsApplication.updatePassword();
					break;
				default:
					System.out.println("Invalid Input");
=======
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
//						crsApplication.registerStudent();
						break;	
					case 3:
						crsApplication.updatePassword();
						break;
					default:
						System.out.println("Invalid Input");
				}
				createMainMenu();
				userInput=sc.nextInt();
>>>>>>> Stashed changes
			}
		}catch(Exception ex)
		{
			System.out.println("Error occured "+ex);
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
        System.out.println("---------------------------------------------------------------");
		System.out.println("               Welcome to Course Management System");
		System.out.printf("%30s %5s %n",localDate,localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		System.out.println("---------------------------------------------------------------");
		System.out.println("1. Login");
		System.out.println("2. Student Registration");
		System.out.println("3. Update password");
		System.out.println("4. Exit");
		System.out.println("Enter user input");
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
			System.out.println("-----------------Login------------------");
			System.out.println("Username:");
			username = in.next();
			System.out.println("Password:");
			password = in.next();
			System.out.println("Role:");
			role = in.next();
<<<<<<< Updated upstream
			loggedin = userInterface.authenticateUser(userId, password, role);
			
			//2 cases1
			
			
			//true->role->student->approved
			if(loggedin)
			{
				 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
				 
				 LocalDateTime myDateObj = LocalDateTime.now();
				   
				 String formattedDate = myDateObj.format(myFormatObj);  
				
				 role = userInterface.getRole(userId);
			
				switch(role) {
				case "ADMIN":
					System.out.println(formattedDate + " Login Successful");
					AdminCRSMenu adminMenu = new AdminCRSMenu();
					adminMenu.createMenu();
					break;
				case "PROFESSOR":
//					System.out.println(formattedDate + " Login Successful");
//					ProfessorCRSMenu professorMenu=new ProfessorCRSMenu();
//					professorMenu.createMenu(userId);
					
					break;
				case "STUDENT":
//					
//					String studentId = userId;
//					boolean isApproved=studentInterface.isApproved(studentId);
//					if(isApproved) {
//						System.out.println(formattedDate + " Login Successful");
//						StudentCRSMenu studentMenu=new StudentCRSMenu();
//						studentMenu.create_menu(studentId);
//						
//					} else {
//						System.out.println("Failed to login, you have not been approved by the administration!");
//						loggedin=false;
//					}
					break;
=======
			loggedin = userInterface.authenticateUser(username, password, role);

			//true->role->student->approved
			if(loggedin)
			{
				 role = userInterface.getRole(username);
				 switch(role) {
				 	case "ADMIN":
				 		System.out.println("Login Successful as an Admin");
				 		AdminCRSMenu adminMenu = new AdminCRSMenu();
				 		adminMenu.createMenu();
				 		break;
				 	case "PROFESSOR":
				 		System.out.println("Login Successful as a Professor");
				 		ProfessorCRSMenu professorMenu=new ProfessorCRSMenu();
				 		professorMenu.createMenu(username);
				 		break;
				 	case "STUDENT":
						boolean isApproved=studentInterface.isApproved(username);
						if(isApproved) {
							System.out.println("Login Successful as a Student");
							StudentCRSMenu studentMenu=new StudentCRSMenu();
							studentMenu.createMenu(username);	
						} else {
							System.out.println("Failed to login, you have not been approved by the administration!");
							loggedin=false;
						}
				 		break;
				 	}
>>>>>>> Stashed changes
				}
				else
					System.out.println("Invalid Credentials!");			
		}catch(UserNotFoundException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Method to help Student register themselves, pending admin approval
	 */
<<<<<<< Updated upstream
//	public void registerStudent()
//	{
//		Scanner sc=new Scanner(System.in);
//
//		String userId,name,password,address,branchName;
//		GenderConstant gender;
//		int genderV, batch;
//		try
//		{
//			//input all the student details
//			System.out.println("---------------Student Registration-------------");
//			System.out.println("Name:");
//			name=sc.nextLine();
//			System.out.println("Email:");
//			userId=sc.next();
//			System.out.println("Password:");
//			password=sc.next();
//			System.out.println("Enter Gender Choice: \t 1: Male \t 2.Female\t 3.Other");
//			genderV=sc.nextInt();
//			sc.nextLine();
//			
//			switch(genderV)
//			{
//			case 1:
//				gender=GenderConstant.MALE;
//				break;
//			case 2:
//				gender=GenderConstant.FEMALE;
//				break;
//				
//			case 3:
//				gender=GenderConstant.OTHER;
//				break;
//			default: 
//				gender=GenderConstant.OTHER;
//			}
//			
//			System.out.println("Branch:");
//			branchName=sc.nextLine();
//			System.out.println("Batch:");
//			batch=sc.nextInt();
//			sc.nextLine();
//			System.out.println("Address:");
//			address=sc.nextLine();
//			
//			
//			String newStudentId = studentInterface.register(name, userId, password, gender, batch, branchName, address);
//			
//			//notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, newStudentId, null,0);
//			
//		}
=======

	public void registerStudent()
	{
		Scanner sc=new Scanner(System.in);

		String userId,name,password,address,dept;
		GenderConstant gender;
		int genderChoice, gradYear;
		try
		{
			//input all the student details
			System.out.println("---------------Student Registration-------------");
			System.out.println("Name:");
			name=sc.nextLine();
			System.out.println("Email:");
			userId=sc.next();
			System.out.println("Password:");
			password=sc.next();
			System.out.println("Enter Gender Choice: \t 1: Male \t 2.Female\t 3.Other");
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
			
			System.out.println("Department:");
			dept=sc.nextLine();
			System.out.println("Graduation Year:");
			gradYear=sc.nextInt();
			sc.nextLine();
			System.out.println("Address:");
			address=sc.nextLine();
			
			
			String newStudentId = studentInterface.register(name, userId, password, gender, gradYear, dept, address);
			
//			notificationInterface.sendNotification(NotificationTypeConstant.REGISTERATION, newStudentId, null,0);
			
		}
>>>>>>> Stashed changes
//		catch(StudentNotRegisteredException ex)
//		{
//			System.out.println("Something went wrong! "+ex.getStudentName() +" not registered. Please try again");
//		}
<<<<<<< Updated upstream
//		//sc.close();
//	}
=======
		finally {
		sc.close();
		}
	}

>>>>>>> Stashed changes
	
	/**
	 * Method to update password of User
	 */
	public void updatePassword() {
		Scanner in = new Scanner(System.in);
		String username,newPassword;
		try {
			System.out.println("------------------Update Password--------------------");
			System.out.println("Username:");
			username=in.next();
			System.out.println("New Password:");
			newPassword=in.next();
			boolean isUpdated=userInterface.updatePassword(username, newPassword);
			if(isUpdated)
				System.out.println("Password updated successfully!");

			else
				System.out.println("Something went wrong, please try again!");
		} catch(Exception ex) {
			System.out.println("Error Occured "+ex.getMessage());
		}
		
	}
		
}