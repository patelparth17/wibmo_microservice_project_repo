/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.exception.UserNotFoundException;

/**
 * Interface of methods which performs read and write operations of Professor in Database
 */
@Repository
public interface ProfessorDAOInterface {
	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param username
	 * @return get the courses offered by the professor.
	 */
	public List<Course> getCoursesByProfessor(String username);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param courseId : courseCode
	 * @return List : return the enrolled students for the corresponding professor and course code. 
	 * @throws UserNotFoundException 
	 */
	public List<EnrolledStudent> getEnrolledStudents(String courseId) throws UserNotFoundException;
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 * @return status
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade);
}
