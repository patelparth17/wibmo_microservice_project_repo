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


	Optional<Course> findByCourseCode(String courseCode);

	@Modifying
	@Transactional
	@Query(value="UPDATE Course SET professorID = ?2 WHERE courseCode = ?1",nativeQuery = true)
	void assignCourse(String courseCode, String professorId);
	
	@Query(value="SELECT * FROM course WHERE courseCode NOT IN  (SELECT courseCode  FROM registeredcourse WHERE studentId = ?1 UNION SELECT courseCode  FROM secondarycourse WHERE studentId = ?1) AND seats > 0", nativeQuery = true)
	List<Course> viewCourses(String userId);

	@Query(value="SELECT sum(courseFee) FROM course WHERE courseCode IN (SELECT courseCode FROM registeredcourse WHERE studentId = ?1)", nativeQuery = true)
	double calculateFee(String userID);

	@Modifying
	@Transactional
	@Query(value="UPDATE course SET seats = seats+1 WHERE courseCode=?1", nativeQuery = true)
	void incrementSeats(String courseCode);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE course SET seats = seats-1 WHERE courseCode = ?1", nativeQuery = true)
	void decrementSeats(String courseCode);

}
