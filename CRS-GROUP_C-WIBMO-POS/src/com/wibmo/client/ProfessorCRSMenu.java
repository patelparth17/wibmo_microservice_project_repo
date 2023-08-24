package com.wibmo.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.business.ProfessorImpl;
import com.wibmo.business.ProfessorInterface;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.validator.ProfessorValidator;

public class ProfessorCRSMenu {
	private static Logger logger = Logger.getLogger(ProfessorCRSMenu.class);
	ProfessorInterface professorInterface = ProfessorImpl.getInstance();

	/**
	 * Method to create Professor menu
	 * @param username
	 */
	public void createMenu(String username) {
		Scanner professorScanner = new Scanner(System.in);

		while (CRSApplication.loggedin) {
			logger.info("--------------------------------");
			logger.info("-------Professor Menu-----------");
			logger.info("--------------------------------");
			logger.info("1. view Courses");
			logger.info("2. view Enrolled Students");
			logger.info("3. add Grades");
			logger.info("4. logout");
			logger.info("--------------------------------");
			System.out.printf("Choose From Menu: ");

			int professorChoice = professorScanner.nextInt();
			switch (professorChoice) {
			case 1:
				getCourses(username);
				break;
			case 2:
				viewEnrolledStudents(username);
				break;
			case 3:
				addGrade(username);
				break;
			case 4:
				CRSApplication.loggedin = false;
				return;
			default:
				logger.debug("Please select appropriate option...");
			}
		}
		professorScanner.close();
	}

	/**
	 * Method to get the signed-up courses 
	 * @param username
	 */
	public void getCourses(String username) {
		try {
			List<Course> coursesEnrolled = professorInterface.viewCourses(username);
			logger.debug("List of courses:");
			logger.debug(String.format("%20s %20s","COURSE CODE","COURSE NAME"));
			for(Course obj: coursesEnrolled) {
				logger.debug(String.format("%20s %20s",obj.getCourseCode(), obj.getCourseName()));
			}
		} catch(Exception ex) {
			logger.error("Something went wrong!"+ex.getMessage());
		}
	}

	/**
	 * Method to view the list of enrolled students
	 * @param username
	 */
	public void viewEnrolledStudents(String username) {
		logger.debug("The list of enrolled students:");
		logger.debug(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","StudentId" ));
		try {
			List<EnrolledStudent> enrolledStudents = professorInterface.viewEnrolledStudents(username);
			for (EnrolledStudent obj: enrolledStudents) {
				logger.debug(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}

		} catch(Exception ex) {
			logger.error(ex.getMessage()+"Something went wrong, please try again later!");
		}
	}
	
	/**
	 * Method to add grade to the student's course
	 * @param username
	 */
	public void addGrade(String username) {
		Scanner in = new Scanner(System.in);

		String courseCode, grade, studentId;
		try {
			List<EnrolledStudent> enrolledStudents = new ArrayList<>();
			try {
				enrolledStudents = professorInterface.viewEnrolledStudents(username);
			} catch (UserNotFoundException | SQLException e) {
				logger.error(e.getMessage());
			}
			logger.debug("The list of enrolled students:");
			logger.debug(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","Student ID" ));
			for (EnrolledStudent obj: enrolledStudents) {
				logger.debug(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}
			List<Course> coursesEnrolled = new ArrayList<>();
			try {
				coursesEnrolled	= professorInterface.viewCourses(username);
			} catch (UserNotFoundException e) {
				logger.error(e.getMessage());
			}
			logger.debug("----------------Add Grade--------------");
			logger.info("Enter student id: ");
			studentId = in.nextLine();
			logger.info("Enter course code: ");
			courseCode = in.nextLine();
			logger.info("Enter grade: ");
			grade = in.nextLine();
			if (!(ProfessorValidator.isValidStudent(enrolledStudents, studentId)
					&& ProfessorValidator.isValidCourse(coursesEnrolled, courseCode))) {
				professorInterface.addGrade(studentId, courseCode, grade);
				logger.debug("GradeConstant added successfully for student ID : "+studentId);
			} else {
				logger.debug("Invalid data entered, try again!");
			}
		} catch(GradeNotAllotedException ex) {
			logger.error("GradeConstant cannot be added for student ID : "+ex.getStudentId());
		}
	}
}
