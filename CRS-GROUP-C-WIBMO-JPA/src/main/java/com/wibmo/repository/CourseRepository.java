/**
 * 
 */
package com.wibmo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
	@Query(value="INSERT INTO Course(courseCode, courseName, seats, professorID,courseFee) values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	void addCourse(String courseCode, String courseName, int seats, String professorId, double courseFee) ;


	@Modifying
	@Transactional
	@Query(value="DELETE FROM Course WHERE courseCode = ?1",nativeQuery=true)
	void removeCourse(String courseCode);
	

}
