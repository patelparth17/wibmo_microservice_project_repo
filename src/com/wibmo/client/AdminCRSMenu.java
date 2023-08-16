/**
 * 
 */
package com.wibmo.client;

import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.*;
import com.wibmo.constants.*;
import com.wibmo.exception.*;
import com.wibmo.business.*;

public class AdminCRSMenu {
	
	 AdminOperationInterface adminOperation = AdminOperationImpl.getInstance();
	 NotificationInterface notificationObject = NotificationImpl.getInstance();
	 Scanner adminScanner = new Scanner(System.in);
	
	/**
	 * Method to Create Admin Menu
	 */
	 public void createMenu(){
		
		 while(CRSApplication.loggedin) {
			 System.out.println("=============================");
			 System.out.println("**********Admin Menu*********");
			 System.out.println("=============================");
			 System.out.println("1. View course in catalog");
			 System.out.println("2. Add Course to catalog");
			 System.out.println("3. Delete Course from catalog");
			 System.out.println("4. Approve Students");
			 System.out.println("5. Add Professor");
			 System.out.println("6. Assign Courses To Professor");
			 System.out.println("7. Generate Report Card");
			 System.out.println("8. View Pending Admissions");
			 System.out.println("9. Logout");
			 System.out.println("*****************************");
			
			int adminChoice = adminScanner.nextInt();
			
			switch(adminChoice) {
			case 1:
				viewCoursesInCatalogue();
				break;
				
			case 2:
				addCourseToCatalogue();
				break;
				
			case 3:
				deleteCourse();
				break;
				
			case 4:
				approveStudent();
				break;
			
			case 5:
				addProfessor();
				break;
			
			case 6:
				assignCourseToProfessor();
				break;
			
			case 7:
				generateReportCard();
				break;
				
			case 8:
				List<Student> pendingStudentsList =viewPendingAdmissions();
				if(pendingStudentsList.size() == 0) {
					System.out.println("No students pending approvals");
				}
				System.out.println(String.format("%20s | %20s | %20s", "StudentId", "Name", "GenderConstant"));
				for(Student student : pendingStudentsList) {
					System.out.println(String.format("%20s | %20s | %20s", student.getStudentId(), student.getName(), student.getGender().toString()));
				}
				break;
				
			case 9:
				CRSApplication.loggedin = false;
				return;

			default:
				System.out.println("***** Wrong Choice *****");
			}
		}
	}
	 
	 /**
	  * Method to generate Report Card of a Student
	  */
	 private void generateReportCard() {
		 System.out.println("Enter Student Id whose Report Card needs to be generated: ");
		 String studentId = adminScanner.next();
		 List<RegisteredCourse> reportCard = adminOperation.generateGradeCard(studentId);
		 System.out.println(String.format("The Report Card for the Student with id %s is %s", studentId, reportCard.toString()));
	 }
	
	/**
	 * Method to assign Course to a Professor
	 */
	private void assignCourseToProfessor() {
		List<Professor> professorList= adminOperation.viewProfessors();
		System.out.println("*************************** Professor *************************** ");
		System.out.println(String.format("%20s | %20s | %20s ", "ProfessorId", "Name", "Designation"));
		for(Professor professor : professorList) {
			System.out.println(String.format("%20s | %20s | %20s ", professor.getUserId(), professor.getName(), professor.getDesignation()));
		}
		
		
		System.out.println("\n\n");
		List<Course> courseList= adminOperation.viewCourses();
		System.out.println("**************** Course ****************");
		System.out.println(String.format("%20s | %20s", "CourseCode", "CourseName"));
		for(Course course : courseList) {
			System.out.println(String.format("%20s | %20s ", course.getCourseCode(), course.getCourseName()));
		}
		
		System.out.println("Enter Course Code:");
		String courseCode = adminScanner.next();
		
		System.out.println("Enter Professor's User Id:");
		String userId = adminScanner.next();
		
		try {
			
			adminOperation.assignCourse(courseCode, userId);
		
		}
		catch(CourseNotFoundException | UserNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Method to add Professor to DB
	 */
	private void addProfessor() {
		
		Professor professor = new Professor();
		
		System.out.println("Enter Professor Name:");
		String professorName = adminScanner.next();
		professor.setName(professorName);
		
		System.out.println("Enter Department:");
		String department = adminScanner.next();
		professor.setDepartment(department);
		
		System.out.println("Enter Designation:");
		String designation = adminScanner.next();
		professor.setDesignation(designation);
		
		System.out.println("Enter User Id:");
		String userId = adminScanner.next();
		professor.setUserId(userId);
		
		System.out.println("Enter Password:");
		String password = adminScanner.next();
		professor.setPassword(password);
		
		System.out.println("Enter Gender: \t 1: Male \t 2.Female \t 3.Other ");
		int gender = adminScanner.nextInt();
		professor.setGender(GenderConstant.getName(gender));
		
		System.out.println("Enter Address:");
		String address = adminScanner.next();
		professor.setAddress(address);
		
		professor.setRole(RoleConstant.stringToName("Professor"));
		
		try {
			adminOperation.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyExists e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Method to approve a Student using Student's ID
	 */
	private void approveStudent() {
		
		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0) {
			return;
		}
		
		System.out.println("Enter Student's ID:");
		String studentUserIdApproval = adminScanner.next();
		
		try {
			adminOperation.approveStudent(studentUserIdApproval, studentList);
			//send notification from system
			notificationObject.sendNotification(NotificationTypeConstant.REGISTERATION, studentUserIdApproval, null,0);
	
		} catch (StudentNotFoundForApprovalException e) {
			System.out.println(e.getMessage());
		}
	}

	private List<Student> viewPendingAdmissions() {
		List<Student> pendingStudentsList= adminOperation.viewPendingAdmissions();
		return pendingStudentsList;
	}

	/**
	 * Method to delete Course from catalogue
	 * @throws CourseNotFoundException 
	 */
	private void deleteCourse() {
		List<Course> courseList = viewCoursesInCatalogue();
		System.out.println("Enter Course Code:");
		String courseCode = adminScanner.next();
		
		try {
			adminOperation.removeCourse(courseCode, courseList);
		} catch (CourseNotFoundException | CourseNotDeletedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method to add Course to catalogue
	 */
	private void addCourseToCatalogue() {
		List<Course> courseList = viewCoursesInCatalogue();

		adminScanner.nextLine();
		System.out.println("Enter Course Code:");
		String courseCode = adminScanner.nextLine();
		
		System.out.println("Enter Course Name:");
		String courseName = adminScanner.next();
		
		Course course = new Course(courseCode, courseName, null, 10);
		
		try {
			adminOperation.addCourse(course, courseList);
		} catch (CourseAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}						
	}
	
	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
		List<Course> courseList = adminOperation.viewCourses();
		if(courseList.size() == 0) {
			System.out.println("No course in the catalogue!");
			return courseList;
		}
		System.out.println(String.format("%20s | %20s | %20s","COURSE CODE", "COURSE NAME", "INSTRUCTOR"));
		for(Course course : courseList) {
			System.out.println(String.format("%20s | %20s | %20s", course.getCourseCode(), course.getCourseName(), course.getInstructorId()));
		}
		return courseList;
	}

}
