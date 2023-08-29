/**
 * 
 */
package com.wibmo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.model.Course;
import com.wibmo.model.EnrolledStudent;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;

@Service
public interface ProfessorInterface {
	
	/**
	 * Method to add grade to the student's course
	 * @param studentName
	 * @param courseId
	 * @param grade
	 * @return the status
	 * @throws GradeNotAllotedException
	 */
	public boolean addGrade(String username,String studentName, String courseId, String grade) throws GradeNotAllotedException;
	
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
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 */
	public List<EnrolledStudent> viewEnrolledStudents(String username) throws SQLException, UserNotFoundException;
}
