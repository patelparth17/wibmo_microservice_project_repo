/**
 * 
 */
package com.wibmo.business;

import java.sql.SQLException;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public interface ProfessorInterface {
	
	public boolean addGrade(String studentId, String courseId, String grade) throws GradeNotAllotedException;
	
	public List<Course> viewCourses(String username) throws UserNotFoundException;
	
	public String getProfessorById(String username);

	/**
	 * Method to view all the enrolled students
	 * @param courseId: Course id 
	 * @return List of enrolled students
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 */
	public List<EnrolledStudent> viewEnrolledStudents(String username) throws SQLException, UserNotFoundException;
}
