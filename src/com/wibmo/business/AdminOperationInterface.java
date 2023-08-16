/**
 * 
 */
package com.wibmo.business;
import java.util.List;

import com.wibmo.bean.*;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
/**
 * 
 */
public interface AdminOperationInterface {
	
	/**
	 * Method to list the courses in the catalog
	 * @return The list of courses in the catalog
	 */
	public List<Course> viewCourses();
	
	
	/**
	 * 
	 * @param studentId
	 * @param studentList
	 */
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException;
	
	/**
	 * 
	 * @param professor
	 * @throws UserNotAddedException 
	 * @throws UserIdAlreadyExists 
	 * @throws ProfessorNotAddedException 
	 */
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyExists;
	
	/**
	 * 
	 * Method to view the list of Professors
	 */
	public List<Professor> viewProfessors();
	
	/**
	 * 
	 * @param coursecode
	 * @param courselist
	 * @throws CourseNotDeletedException 
	 * @throws CourseNotFoundException 
	 */
	public void removeCourse(String courseCode, List<Course> courseList) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * 
	 * @param course
	 * @param courselist
	 * @throws CourseAlreadyExistsException 
	 */
	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException;
	
	/**
	 * 
	 * @param courseCode
	 * @param professorId
	 * @throws UserNotFoundException 
	 * @throws CourseNotFoundException 
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException;

	/**
	 * @param studentId
	 */
	public List<RegisteredCourse> generateGradeCard(String studentId);


	public List<Student> viewPendingAdmissions();
}
