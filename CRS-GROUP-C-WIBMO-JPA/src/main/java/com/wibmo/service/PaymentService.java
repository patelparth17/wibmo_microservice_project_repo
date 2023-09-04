/**
 * 
 */
package com.wibmo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.repository.PaymentRepository;

/**
 * 
 */

@Service
public class PaymentService implements PaymentInterface {

	@Autowired
	PaymentRepository paymentRepo;
	
	@Modifying
	public String addPayment(String name,PaymentModeConstant mode, String type, double fee) {
		String referenceId=UUID.randomUUID().toString();
		paymentRepo.addPayment(name, mode.toString(), fee, referenceId, type);
		return referenceId;
	}

	public String findByStudentName(String name) {
		return paymentRepo.findByStudentName(name).getTranscationId();
	}

}
