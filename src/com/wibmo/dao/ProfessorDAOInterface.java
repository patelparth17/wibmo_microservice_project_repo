/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;

/**
 * 
 */
public interface ProfessorDAOInterface {
	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param username
	 * @return get the courses offered by the professor.
	 */
	public List<Course> getCoursesByProfessor(String username);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: courseCode
	 * @return: return the enrolled students for the corresponding professor and course code. 
	 */
	public List<EnrolledStudent> getEnrolledStudents(String courseId);
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 * @return status
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade);
}
