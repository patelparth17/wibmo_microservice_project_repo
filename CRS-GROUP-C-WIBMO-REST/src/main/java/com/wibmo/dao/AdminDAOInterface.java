/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
@Repository
public interface AdminDAOInterface {
	
	/**
	 * Method to view courses
	 * @return list of courses
	 */
	public List<Course> viewCourses();
	
	/**
	 * Method to generate grade card of a Student 
	 * studentName
	 * @return list of registered courses with grades
	 */
	public List<RegisteredCourse> generateReportCard(String StudentName);
	
	
	/**
	 * Method to approve a Student
	 * @param studentName
	 * @throws StudentNotFoundForApprovalException
	 * @throws StudentAlreadyApprovedException 
	 */
	public void approveStudent(String studentName) throws StudentNotFoundForApprovalException, StudentAlreadyApprovedException;
	
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @throws CourseNotDeletedException 
	 * @throws CourseNotFoundException 
	 */

	public void removeCourse(String coursecode) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course
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
	 * @param professor
	 * @throws UserNotAddedException 
	 * @throws UserIdAlreadyExists 
	 */
	
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyExists;
	
	
	/**
	 * Method to view professors
	 * @return list of professors
	 */
	public List<Professor> viewProfessors();

	/**
	 * Method to view the pending approvals
	 * @return the list of students having pending approvals
	 */
	public List<Student> viewPendingAdmissions();
	
	public void approveAllStudents(List<Student> studentList);
}
