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

import com.wibmo.constants.SQLConstant;
import com.wibmo.model.Student;

@Repository
public interface StudentRepository  extends CrudRepository<Student, String>{

//	List<Student> findByIsApproved(boolean flag);

	@Query(value= SQLConstant.VIEW_PENDING_APPROVALS,nativeQuery=true)
	List<Student> viewPendingAdmissions();

	@Modifying
	@Transactional
	@Query(value= SQLConstant.APPROVE_STUDENT_QUERY,nativeQuery=true)
	void approveStudent(String studentId);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.APPROVE_ALL_STUDENTS,nativeQuery=true)
	void approveAllStudents();

	
	@Query(value= SQLConstant.GET_REGISTRATION_STATUS, nativeQuery = true)
	int getRegistrationStatus(String userID);

	@Query(value= SQLConstant.GET_PAYMENT_STATUS, nativeQuery = true)
	int getPaymentStatus(String userID);
	
	@Modifying
	@Transactional
	@Query(value= SQLConstant.SET_PAYMENT_STATUS, nativeQuery = true)
	void setPaymentStatus(String userID);

	
	@Query(value= SQLConstant.GET_APPROVAL_STATUS, nativeQuery = true)
	int getApprovalStatus(String userID);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.SET_REGISTRATION_STATUS, nativeQuery = true)
	void setRegisterationStatus(String studentId);

	@Modifying
	@Transactional
	@Query(value= SQLConstant.SET_REPORT_CARD_STATUS, nativeQuery = true)
	void setGeneratedReportCardStatus(String studentId);

	@Query(value= SQLConstant.GET_REPORT_CARD_STATUS, nativeQuery = true)
	int getGeneratedReportCardStatus(String userId);
	
}
