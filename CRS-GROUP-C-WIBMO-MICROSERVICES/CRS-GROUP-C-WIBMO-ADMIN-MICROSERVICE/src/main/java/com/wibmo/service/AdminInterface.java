/**
 * 
 */
package com.wibmo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.NoCoursesRegisteredException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Grade;
import com.wibmo.model.Professor;
import com.wibmo.model.Student;
import com.wibmo.model.User;

/**
 * 
 */
@Service
public interface AdminInterface {
	/**
	 * Method to list the courses in the catalog
	 * @return The list of courses in the catalog
	 */
	public List<Course> viewCourses();
	
	/**
	 * Method to add course to the catalog
	 * @param course
	 * @param courseList
	 * @throws CourseAlreadyExistsException
	 */
	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException;
	
	/**
	 * Method to drop course from the catalog
	 * @param courseCode
	 * @param courseList
	 * @throws CourseNotFoundException
	 * @throws CourseNotDeletedException
	 */
	public void removeCourse(String courseCode, List<Course> courseList) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to view pending admissions
	 * @return The list of students
	 */
	public List<Student> viewPendingAdmissions();
	
	/**
	 * Method to approve the student by admin
	 * @param studentId
	 * @param studentList
	 * @throws StudentNotFoundForApprovalException
	 * @throws StudentAlreadyApprovedException 
	 */
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException, StudentAlreadyApprovedException;

	/**
	 * Method to approve all students
	 * @param studentList
	 */
	public void approveAllStudents();
	
	/**
	 * Method to add professor
	 * @param professor
	 * @throws UserNotAddedException 
	 */
	public void addProfessor(Professor professor) throws UserNotAddedException, ProfessorNotAddedException ;
	
	/**
	 * Method to assign course to Professor
	 * @param courseCode
	 * @param professorId
	 * @throws UserNotFoundException
	 * @throws CourseNotFoundException
	 */
	public void assignCourse(String courseCode, String professorId) throws UserNotFoundException, CourseNotFoundException;
	
	/**
	 * Method to generate Report Card of Student
	 * @param studentId
	 * @return List of grades in various courses taken by the student
	 * @throws UserNotFoundException
	 * @throws NoCoursesRegisteredException
	 */
	public List<Grade> generateGradeCard(String studentId) throws UserNotFoundException, NoCoursesRegisteredException;
	
	/**
	 * Method to save notification details to the notification table and send the appropriate notification to Admin
	 * @param type
	 * @param name
	 */
	public void sendNotification(NotificationTypeConstant type, String name);
	
	/**
	 * Method to approve the Student Registration of given student
	 * @param studentId
	 * @throws StudentAlreadyRegisteredException
	 * @throws UserNotFoundException
	 */
	public void approveStudentRegisteration(String studentId) throws StudentAlreadyRegisteredException, UserNotFoundException;
	
	/**
	 * Method to add a New User 
	 * @param user
	 * @throws UserNotAddedException
	 */
	public void addUser(User user) throws UserNotAddedException;
	
	/**
	 * Method to find the username from given studentId of a student
	 * @param studentId
	 * @return username
	 */
	public String findUserName(String studentId);
	
	/**
	 * Method to check whether Professor exists or not
	 * @param professorId
	 * @return status
	 */
	public boolean isProfessorExists(String professorId);
}
