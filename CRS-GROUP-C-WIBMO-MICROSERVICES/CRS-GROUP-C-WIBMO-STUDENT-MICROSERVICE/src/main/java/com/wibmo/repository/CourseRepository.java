/**
 * 
 */
package com.wibmo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.SQLConstant;
import com.wibmo.model.Course;

/**
 * 
 */

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

	List<Course> findByProfessorID(String professorID);


	List<Course> findAllByProfessorID(String userID);
	
	@Modifying
	@Transactional
	@Query(value=SQLConstant.ADD_COURSE_QUERY, nativeQuery = true)
	void addCourse(String courseCode, String courseName, int seats, String professorId, double courseFee) ;


	@Modifying
	@Transactional
	@Query(value= SQLConstant.DELETE_COURSE_QUERY,nativeQuery=true)
	void removeCourse(String courseCode);


	Optional<Course> findByCourseCode(String courseCode);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.ASSIGN_COURSE_QUERY ,nativeQuery = true)
	void assignCourse(String courseCode, String professorId);
	
	@Query(value= SQLConstant.VIEW_AVAILABLE_COURSE_QUERY, nativeQuery = true)
	List<Course> viewCourses(String userId);

	@Query(value= SQLConstant.CALCULATE_FEE_QUERY, nativeQuery = true)
	double calculateFee(String userID);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.INCREMENT_SEATS_QUERY, nativeQuery = true)
	void incrementSeats(String courseCode);
	
	@Modifying
	@Transactional
	@Query(value= SQLConstant.DECREMENT_SEATS_QUERY, nativeQuery = true)
	void decrementSeats(String courseCode);

}
