/**
 * 
 */
package com.wibmo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.SQLConstant;
import com.wibmo.model.Payment;

/**
 * 
 */

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String>{

	Payment findByStudentName(String name);

	@Modifying
	@Transactional
	@Query(value=SQLConstant.ADD_PAYMENT, nativeQuery = true)
	void addPayment(String studentName, String modeOfPayment, double amount, String referenceId, String paymentStatus);
}
