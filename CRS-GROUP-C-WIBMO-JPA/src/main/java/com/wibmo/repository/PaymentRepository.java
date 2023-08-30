/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.Payment;

/**
 * 
 */

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String>{

	Payment findByStudentName(String name);

}
