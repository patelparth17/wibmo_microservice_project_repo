/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.SQLConstant;
import com.wibmo.model.Student;

@Repository
public interface StudentRepository  extends CrudRepository<Student, String>{
	
	@Query(value= SQLConstant.GET_APPROVAL_STATUS, nativeQuery = true)
	int getApprovalStatus(String userID);
	
}
