package com.wibmo.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.SQLConstant;
import com.wibmo.model.RegisteredCourse;

@Repository
public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse, String>{

	List<RegisteredCourse> findAllByCourseCode(String courseCode);

	@Query(value= SQLConstant.GET_GRADES_QUERY, nativeQuery = true)
	List<Map<String, String>> findByStudentId(String studentId);
	
	@Query(value= SQLConstant.NUM_OF_REGISTERED_COURSES, nativeQuery = true)
	int numOfRegisteredCourses(String userId);

	@Query(value= SQLConstant.NUM_OF_SECONDARY_COURSES, nativeQuery = true)
	int numSecondaryCourses(String userId);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.ADD_SECONDARY_COURSE_QUERY, nativeQuery = true)
	void addSecondaryCourse(String studentId, String courseCode);

	

	@Modifying
	@Transactional
	@Query(value= SQLConstant.ADD_PRIMARY_COURSE_QUERY, nativeQuery = true)
	int addCourse(String studentId ,String courseCode, String grade);

	List<RegisteredCourse> findAllByStudentId(String userId);
	
	@Query(value= SQLConstant.GET_COURSES_QUERY, nativeQuery = true)
	List<String> getCourseCodes(@Param("studentId") String studentId);
	
	@Modifying
	@Transactional
	@Query(value= SQLConstant.DROP_COURSE_QUERY, nativeQuery = true)
	void dropCourse(String userId, String courseCode);
	
	@Query(value= SQLConstant.GET_REGISTERED_COURSES, nativeQuery = true)
	String getRegisteredCourses(String userId, String courseCode);
	
	@Query(value= SQLConstant.GET_SECONDARY_COURSES, nativeQuery = true)
	Optional<List<String>> getSecondaryCourses(String userId);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.DROP_SECONDARY_COURSE, nativeQuery = true)
	void dropSecondaryCourse(String userId, String courseCode);
	
	@Modifying
	@Transactional
	@Query(value= SQLConstant.ADD_GRADE_QUERY,nativeQuery = true)
	public void addGrade(String grade, String courseCode, String studentID);
}
