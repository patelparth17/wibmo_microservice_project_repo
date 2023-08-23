/**
 * 
 */
package com.wibmo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotFoundException;
/**
 * 
 */
@Service
public interface AdminOperationInterface {
	
	/**
	 * Method to list the courses in the catalog
	 * @return The list of courses in the catalog
	 */
	public List<Course> viewCourses();
	
	
	/**
	 * Method to approve the student by admin
	 * @param studentId
	 * @param studentList
	 * @throws StudentNotFoundForApprovalException
	 */
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException;
	
	/**
	 * Method to add Professor
	 * @param professor
	 * @throws ProfessorNotAddedException
	 * @throws UserIdAlreadyExists
	 */
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyExists;
	
	/**
	 * Method to view the list of Professors
	 */
	public List<Professor> viewProfessors();
	
	/**
	 * Method to drop course from the catalog
	 * @param courseCode
	 * @param courseList
	 * @throws CourseNotFoundException
	 * @throws CourseNotDeletedException
	 */
	public void removeCourse(String courseCode, List<Course> courseList) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to add course to the catalog
	 * @param course
	 * @param courseList
	 * @throws CourseAlreadyExistsException
	 */
	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException;
	
	/**
	 * Method to assign course to the professor
	 * @param courseCode
	 * @param professorId
	 * @throws CourseNotFoundException
	 * @throws UserNotFoundException
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException;

	/**
	 * Method to generate grade card of the student
	 * @param studentId
	 */
	public List<RegisteredCourse> generateGradeCard(String studentId);

	/**
	 * Method to view pending admissions
	 * @return The list of students
	 */
	public List<Student> viewPendingAdmissions();
}
