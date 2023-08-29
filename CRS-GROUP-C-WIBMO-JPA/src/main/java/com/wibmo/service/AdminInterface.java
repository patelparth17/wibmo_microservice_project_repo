/**
 * 
 */
package com.wibmo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.model.Course;
import com.wibmo.model.Student;

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
}
