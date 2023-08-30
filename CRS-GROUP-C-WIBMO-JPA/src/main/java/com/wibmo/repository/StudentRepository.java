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

import com.wibmo.model.Student;

@Repository
public interface StudentRepository  extends CrudRepository<Student, String>{

//	List<Student> findByIsApproved(boolean flag);

	@Query(value="SELECT * FROM Student s INNER JOIN User u WHERE isApproved = 0 AND s.studentID = u.userID",nativeQuery=true)
	List<Student> viewPendingAdmissions();

	@Query(value="SELECT isApproved FROM student WHERE studentId = ?1",nativeQuery=true)
	int checkIsApproved(String studentId);

	@Modifying
	@Transactional
	@Query(value="UPDATE Student SET isApproved = 1 WHERE studentId = ?1",nativeQuery=true)
	void approveStudent(String studentId);

	@Modifying
	@Transactional
	@Query(value="UPDATE Student SET isApproved = 1",nativeQuery=true)
	void approveAllStudents();

	
	@Query(value="SELECT isRegistered FROM Student WHERE StudentID = ?1", nativeQuery = true)
	int getRegistrationStatus(String userID);

	@Query(value="SELECT isPaid FROM Student WHERE StudentID = ?1", nativeQuery = true)
	int getPaymentStatus(String userID);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Student SET isPaid = 1 WHERE studentId = ?1", nativeQuery = true)
	void setPaymentStatus(String userID);
	
}
