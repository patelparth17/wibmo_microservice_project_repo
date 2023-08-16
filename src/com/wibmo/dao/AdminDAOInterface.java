/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.exception.*;

/**
 * 
 */
public interface AdminDAOInterface {
	/**
	 * Method to view courses
	 * @return
	 */
	public List<Course> viewCourses();
	
	/**
	 * Method to generate grade card of a Student 
	 * studentid 
	 * @return  
	 */
	public List<RegisteredCourse> generateReportCard(String Studentid);
	
	
	/**
	 * Method to approve a Student 
	 * studentid
	 * studentlist
	 * @throws StudentNotFoundForApprovalException 
	 */
	public void approveStudent(String studentid) throws StudentNotFoundForApprovalException;
	
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 * @throws CourseNotDeletedException 
	 * @throws CourseNotFoundException 
	 */

	public void removeCourse(String coursecode) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in the catalog
	 * @throws CourseExistsAlreadyException
	 */
	
	public void addCourse(Course course)throws CourseAlreadyExistsException;
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId
	 * @throws UserNotFoundException 
	 * @throws CourseNotFoundException 
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException;
	
	/**
	 * Method to add User
	 * @param user
	 * @throws UserNotAddedException, UserIdAlreadyInUseException 
	 */
	public void addUser(User user)throws UserNotAddedException, UserIdAlreadyExists ;
	
	/**
	 * Method to add Professor to DB
	 * professor : Professor Object storing details of a professor 
	 * @throws UserNotAddedException 
	 * @throws UserIdAlreadyExists 
	 */
	
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyExists;
	
	
	/**
	 * Method to view professors
	 * @return
	 */
	public List<Professor> viewProfessors();

	public List<Student> viewPendingAdmissions();
}
