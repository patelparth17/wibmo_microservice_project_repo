package com.wibmo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wibmo.model.RegisteredCourse;

@Repository
public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse, String>{

	List<RegisteredCourse> findAllByCourseCode(String courseCode);

	List<RegisteredCourse> findByStudentId(String studentId);
	
	@Query(value="SELECT count(studentId) FROM registeredcourse WHERE studentId = ?1", nativeQuery = true)
	int numOfRegisteredCourses(String userId);

	@Query(value="SELECT count(studentId) FROM secondarycourse WHERE studentId = ?1", nativeQuery = true)
	int numSecondaryCourses(String userId);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO secondarycourse (studentId, courseCode) VALUES (?1 , ?2)", nativeQuery = true)
	void addSecondaryCourse(String studentId, String courseCode);

	

	@Modifying
	@Transactional
	@Query(value="INSERT INTO registeredcourse (studentId,courseCode,grade) VALUES ( ?1 , ?2, ?3)", nativeQuery = true)
	int addCourse(String studentId ,String courseCode, String grade);

	List<RegisteredCourse> findAllByStudentId(String userId);
	
	@Query(value="SELECT DISTINCT courseCode FROM registeredcourse rc WHERE rc.studentID = :studentId", nativeQuery = true)
	List<String> getCourseCodes(@Param("studentId") String studentId);
	
	@Modifying
	@Transactional
	@Query(value="DELETE registeredcourse FROM registeredcourse WHERE studentID = ?1 AND courseCode = ?2", nativeQuery = true)
	void dropCourse(String userId, String courseCode);
	
	@Query(value="SELECT courseCode FROM registeredcourse WHERE studentID = ?1 AND courseCode = ?2 ", nativeQuery = true)
	String getRegisteredCourses(String userId, String courseCode);
	
	@Query(value="SELECT courseCode FROM secondarycourse WHERE studentID = ?1 ", nativeQuery = true)
	Optional<List<String>> getSecondaryCourses(String userId);

	@Modifying
	@Transactional
	@Query(value="DELETE secondarycourse FROM secondarycourse WHERE studentID = ?1 AND courseCode = ?2", nativeQuery = true)
	void dropSecondaryCourse(String userId, String courseCode);
}
