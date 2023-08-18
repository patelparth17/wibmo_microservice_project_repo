/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public interface ProfessorDAOInterface {
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param username, prof id of the professor
	 * @return get the courses offered by the professor.
	 * @throws UserNotFoundException 
	 */
	public List<Course> getCoursesByProfessor(String username);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 * @throws UserNotFoundException 
	 */
	public List<EnrolledStudent> getEnrolledStudents(String courseId);
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code for the corresponding 
	 * @return: returns the status after adding the grade
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade);


	/**
	 * Method to Get professor name by id
	 * @param profId
	 * @return Professor Id in string
	 */
	public String getProfessorById(String profId);
}
