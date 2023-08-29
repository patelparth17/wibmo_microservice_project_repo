/**
 * 
 */
package com.wibmo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.Professor;


@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {


	@Query(value="SELECT userID FROM user WHERE username = ?1", nativeQuery = true)
	public String getUserID(String username);

	@Modifying
	@Transactional
	@Query(value="UPDATE registeredcourse SET Grade=? WHERE courseCode=? AND studentId=?",nativeQuery = true)
	public void addGrade(String grade, String courseCode, String studentID);
	
	
}
