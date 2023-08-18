package com.wibmo.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.business.ProfessorImpl;
import com.wibmo.business.ProfessorInterface;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.validator.ProfessorValidator;

public class ProfessorCRSMenu {
	ProfessorInterface professorInterface = ProfessorImpl.getInstance();

	/**
	 * @param username
	 */
	public void createMenu(String username) {
		Scanner professorScanner = new Scanner(System.in);

		while (CRSApplication.loggedin) {
			System.out.println("--------------------------------");
			System.out.println("-------Professor Menu-----------");
			System.out.println("--------------------------------");
			System.out.println("1. view Courses");
			System.out.println("2. view Enrolled Students");
			System.out.println("3. add Grades");
			System.out.println("4. logout");
			System.out.println("--------------------------------");
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
				System.out.println("Please select appropriate option...");
			}
		}
		professorScanner.close();
	}

	public void getCourses(String username) {
		try {
			List<Course> coursesEnrolled = professorInterface.viewCourses(username);
			System.out.println(String.format("%20s %20s","COURSE CODE","COURSE NAME"));
			for(Course obj: coursesEnrolled) {
				System.out.println(String.format("%20s %20s",obj.getCourseCode(), obj.getCourseName()));
			}
		} catch(Exception ex) {
			System.out.println("Something went wrong!"+ex.getMessage());
		}
	}

	public void viewEnrolledStudents(String username) {
		System.out.println(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","StudentId" ));
		try {
			List<EnrolledStudent> enrolledStudents = professorInterface.viewEnrolledStudents(username);
			for (EnrolledStudent obj: enrolledStudents) {
				System.out.println(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}

		} catch(Exception ex) {
			System.out.println(ex.getMessage()+"Something went wrong, please try again later!");
		}
	}
	
	public void addGrade(String username) {
		Scanner in = new Scanner(System.in);

		String courseCode, grade, studentId;
		try {
			List<EnrolledStudent> enrolledStudents = new ArrayList<>();
			try {
				enrolledStudents = professorInterface.viewEnrolledStudents(username);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","Student ID" ));
			for (EnrolledStudent obj: enrolledStudents) {
				System.out.println(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}
			List<Course> coursesEnrolled = new ArrayList<>();
			try {
				coursesEnrolled	= professorInterface.viewCourses(username);
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("----------------Add Grade--------------");
			System.out.printf("Enter student id: ");
			studentId = in.nextLine();
			System.out.printf("Enter course code: ");
			courseCode = in.nextLine();
			System.out.println("Enter grade: ");
			grade = in.nextLine();
			if (!(ProfessorValidator.isValidStudent(enrolledStudents, studentId)
					&& ProfessorValidator.isValidCourse(coursesEnrolled, courseCode))) {
				professorInterface.addGrade(studentId, courseCode, grade);
				System.out.println("GradeConstant added successfully for "+studentId);
			} else {
				System.out.println("Invalid data entered, try again!");
			}
		} catch(GradeNotAllotedException ex) {
			System.out.println("GradeConstant cannot be added for"+ex.getStudentId());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
