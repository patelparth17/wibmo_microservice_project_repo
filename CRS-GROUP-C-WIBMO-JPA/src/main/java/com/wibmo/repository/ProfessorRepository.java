/**
 * 
 */
package com.wibmo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wibmo.model.Professor;
import com.wibmo.model.Course;
import com.wibmo.model.EnrolledStudent;
import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.UserNotFoundException;


@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {

	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param username
	 * @return get the courses offered by the professor.
	 */
	@Query(SQLConstant.GET_COURSES)
	public List<Course> getCoursesByProfessor(String username);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: courseCode
	 * @return: return the enrolled students for the corresponding professor and course code. 
	 * @throws UserNotFoundException 
	 */
//	public List<EnrolledStudent> getEnrolledStudents(String courseId) throws UserNotFoundException;
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 * @return status
	 */
//	public Boolean addGrade(String studentId,String courseCode,String grade);
	
}
