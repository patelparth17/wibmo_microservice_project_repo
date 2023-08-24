/**
 * 
 */
package com.wibmo.client;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.bean.*;
import com.wibmo.constants.*;
import com.wibmo.exception.*;
import com.wibmo.business.*;

public class AdminCRSMenu {
	private static Logger logger = Logger.getLogger(AdminCRSMenu.class);
	AdminOperationInterface adminOperation = AdminOperationImpl.getInstance();
	NotificationInterface notificationObject = NotificationImpl.getInstance();
	Scanner adminScanner = new Scanner(System.in);

	/**
	 * Method to Create Admin Menu
	 */
	public void createMenu() {
		while (CRSApplication.loggedin) {
			logger.info("=============================");
			logger.info("**********Admin Menu*********");
			logger.info("=============================");
			logger.info("1. View course in catalog");
			logger.info("2. Add Course to catalog");
			logger.info("3. Delete Course from catalog");
			logger.info("4. Approve Students");
			logger.info("5. Add Professor");
			logger.info("6. Assign Courses To Professor");
			logger.info("7. Generate Report Card");
			logger.info("8. View Pending Admissions");
			logger.info("9. Logout");
			logger.info("=============================");
			int adminChoice = adminScanner.nextInt();

			switch (adminChoice) {
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
				approveStudents();
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
				viewPendingAdmissions();
				break;

			case 9:
				CRSApplication.loggedin = false;
				return;

			default:
				logger.debug("***** Wrong Choice *****");
			}
		}
	}

	/**
	 * Method to generate Report Card of a Student
	 */
	private void generateReportCard() {
		logger.debug("Enter Student Id whose Report Card needs to be generated: ");
		String studentId = adminScanner.next();
		List<RegisteredCourse> reportCard = adminOperation.generateGradeCard(studentId);
		logger.debug(String.format("The Report Card details for the Student with id %s are as follows:", studentId));
		for (RegisteredCourse registeredCourse : reportCard) {
			logger.debug(String.format("%20s |%20s | %20s | %20s ", registeredCourse.getstudentId(),
					registeredCourse.getCourse().getCourseCode(), registeredCourse.getCourse().getCourseName(),
					registeredCourse.getGrade()));
		}
	}

	/**
	 * Method to assign Course to a Professor
	 */
	private void assignCourseToProfessor() {
		List<Professor> professorList = adminOperation.viewProfessors();
		logger.debug("*************************** Professor *************************** ");
		logger.debug(String.format("%20s | %20s | %20s ", "ProfessorId", "Name", "Designation"));
		for (Professor professor : professorList) {
			logger.info(String.format("%20s | %20s | %20s ", professor.getUserId(), professor.getName(),
					professor.getDesignation()));
		}

		logger.debug("\n\n");
		List<Course> courseList = adminOperation.viewCourses();
		logger.debug("**************** Course ****************");
		logger.debug(String.format("%20s | %20s", "CourseCode", "CourseName"));
		for (Course course : courseList) {
			logger.debug(String.format("%20s | %20s ", course.getCourseCode(), course.getCourseName()));
		}

		logger.debug("Enter Course Code:");
		String courseCode = adminScanner.next();

		logger.debug("Enter Professor's User Id:");
		String userId = adminScanner.next();

		try {
			adminOperation.assignCourse(courseCode, userId);
			logger.debug("Professor with id " + userId + " assigned to Course Code " + courseCode + " successfully!");
		} catch (CourseNotFoundException | UserNotFoundException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Method to add Professor to DB
	 */
	private void addProfessor() {

		Professor professor = new Professor();

		logger.debug("Enter Professor Name:");
		String professorName = adminScanner.next();
		professor.setName(professorName);

		logger.debug("Enter Department:");
		String department = adminScanner.next();
		professor.setDepartment(department);

		logger.debug("Enter Designation:");
		String designation = adminScanner.next();
		professor.setDesignation(designation);

		logger.debug("Enter User Id:");
		String userId = adminScanner.next();
		professor.setUserId(userId);

		logger.debug("Enter Password:");
		String password = adminScanner.next();
		professor.setPassword(password);

		logger.debug("Enter Gender: \t 1: Male \t 2.Female \t 3.Other ");
		int gender = adminScanner.nextInt();
		professor.setGender(GenderConstant.getName(gender));

		logger.debug("Enter Address:");
		String address = adminScanner.next();
		professor.setAddress(address);

		professor.setRole(RoleConstant.stringToName("Professor"));

		try {
			adminOperation.addProfessor(professor);
			logger.debug("Professor is successfully addaed");
		} catch (ProfessorNotAddedException | UserIdAlreadyExists e) {
			logger.error(e.getMessage());
		}

	}
	
	/**
	 * Menu for approve students
	 */
	private void approveStudents() {
		logger.info("1. Approve students by ID");
		logger.info("2. Approve all students");
		logger.info("Enter your choice:");
		int choice = adminScanner.nextInt();
		switch(choice) {
			case 1:
				approveStudentByID();
				break;
			case 2:
				approveAllStudents();
				break;
			default:
				logger.debug("Enter valid choice");
		}
				
	}

	/**
	 * Method to approve a Student
	 */
	private void approveStudentByID() {

		List<Student> studentList = viewPendingAdmissions();
		if(studentList==null)
			return;
		logger.debug("Enter Student's ID:");
		String studentUserIdApproval = adminScanner.next();

		try {
			String name = adminOperation.approveStudent(studentUserIdApproval, studentList);
			logger.debug("Student with studentId " + studentUserIdApproval + " approved!");
			// send notification from system
			notificationObject.sendNotification(NotificationTypeConstant.APPROVED, name, null, 0);

		} catch (StudentNotFoundForApprovalException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Method to approve all students
	 */
	private void approveAllStudents() {
		List<Student> studentList = viewPendingAdmissions();
		if(studentList == null)
			return;
		adminOperation.approveAllStudents(studentList);
		logger.debug("Successfully approved all the pending approvals!");
		for(Student studentObj:studentList) {
			String name = studentObj.getName();	
			notificationObject.sendNotification(NotificationTypeConstant.APPROVED, name, null, 0);
		}
	}

	/**
	 * Method to view all pending admissions
	 * 
	 * @return list of students having pending admissions
	 */
	private List<Student> viewPendingAdmissions() {
		List<Student> pendingStudentsList = adminOperation.viewPendingAdmissions();
		if (pendingStudentsList.size() == 0) {
			logger.debug("No students pending approvals");
			return null;
		}
		logger.debug("List of the pending approvals:\n");
		logger.debug(String.format("%20s | %20s | %20s", "StudentId", "Name", "Gender"));
		for (Student student : pendingStudentsList) {
			logger.debug(String.format("%20s | %20s | %20s", student.getStudentId(), student.getName(),
					student.getGender().toString()));
		}
		return pendingStudentsList;
	}

	/**
	 * Method to delete Course from catalogue
	 * 
	 * @throws CourseNotFoundException
	 */
	private void deleteCourse() {
		List<Course> courseList = viewCoursesInCatalogue();
		logger.debug("Enter number of courses you want to delete:");
		int numberOfCourses = adminScanner.nextInt();
		while (numberOfCourses > 0) {
			logger.debug("Enter Course Code:");
			String courseCode = adminScanner.next();
			try {
				adminOperation.removeCourse(courseCode, courseList);
				logger.debug("Course with course code "+courseCode+" is successfully deleted.");
			} catch (CourseNotFoundException | CourseNotDeletedException e) {
				logger.error(e.getMessage());
			}
			numberOfCourses--;
		}
	}
	
	

	/**
	 * Method to add Course to catalogue
	 */
	private void addCourseToCatalogue() {
		List<Course> courseList = viewCoursesInCatalogue();
		logger.debug("Enter number of courses you want to add:");
		int numberOfCourses = adminScanner.nextInt();

		while (numberOfCourses > 0) {
			logger.debug("Enter Course Code:");
			String courseCode = adminScanner.next();

			logger.debug("Enter Course Name:");
			String courseName = adminScanner.next();

			logger.debug("Enter Course Fee:");
			double courseFee = adminScanner.nextDouble();

			Course course = new Course(courseCode, courseName, null, 10, courseFee);

			try {
				adminOperation.addCourse(course, courseList);
				logger.debug("Course with course code "+courseCode+" is added successfully.");
			} catch (CourseAlreadyExistsException e) {
				logger.error(e.getMessage());
			}
			numberOfCourses--;
		}

	}

	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
		List<Course> courseList = adminOperation.viewCourses();
		if (courseList.size() == 0) {
			logger.debug("No course in the catalog!");
			return courseList;
		}
		logger.debug("The current courses in the course Catalog are as follows:");
		logger.debug(String.format("%20s | %20s | %20s | %20s", "COURSE CODE", "COURSE NAME", "INSTRUCTOR", "SEATS"));
		for (Course course : courseList) {
			logger.debug(String.format("%20s | %20s | %20s", course.getCourseCode(), course.getCourseName(),
					course.getInstructorId(),course.getSeats()));
		}
		return courseList;
	}

}
