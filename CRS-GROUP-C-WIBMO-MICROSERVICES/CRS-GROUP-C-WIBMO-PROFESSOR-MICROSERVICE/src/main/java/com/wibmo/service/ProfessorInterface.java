/**
 * 
 */
package com.wibmo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.model.Course;
import com.wibmo.model.EnrolledStudent;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;

@Service
public interface ProfessorInterface {
	
	/**
	 * Method to add grade to the student's course
	 * @param username
	 * @param studentId
	 * @param courseId
	 * @param grade
	 * @return status
	 * @throws StudentNotRegisteredException 
	 * @throws UserNotFoundException 
	 */
	public boolean addGrade(String username,String studentId, String courseId, String grade) throws UserNotFoundException, StudentNotRegisteredException;
	
	/**
	 * Method to view courses
	 * @param username
	 * @return the list of courses 
	 * @throws UserNotFoundException
	 */
	public List<Course> viewCourses(String username) throws UserNotFoundException;

	/**
	 * Method to view all the enrolled students
	 * @param username 
	 * @return List of enrolled students
	 * @throws UserNotFoundException 
	 */
	public List<EnrolledStudent> viewEnrolledStudents(String username) throws UserNotFoundException;
}
