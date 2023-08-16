/**
 * 
 */
package com.wibmo.business;
import java.util.List;

import com.wibmo.bean.*;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserNotFoundException;
/**
 * 
 */
public interface AdminOperationInterface {
	/**
	 * 
	 * @param StudentId
	 */
//	public void setReportCardStatus(String StudentId);
	
	/**
	 * 
	 * @return
	 */
	
//	public List<RegisteredCourse> generateGradeCard(String studentId);
	
//	public List<Professor> displayProfessorsList();
	public void addUser(User user);
	
	public List<Course> viewCourses();
	/**
	 * 
	 * @return
	 */
//	public List<Student> viewAdmissionsStatus();
	
	/**
	 * 
	 * @param studentId
	 * @param studentList
	 */
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException;
	
	/**
	 * 
	 * @param professor
	 */
//	public void addProfessor(Professor professor);
	
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
	public void assignCourse(String courseCode, String professorId);

	/**
	 * @param studentId
	 */
	public List<RegisteredCourse> generateGradeCard(String studentId);
}
