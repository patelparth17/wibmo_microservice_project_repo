/**
 * 
 */
package com.wibmo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Student;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;

/**
 * 
 */

@Service
public class StudentService implements StudentInterface{

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserService userService;
	
	@Modifying
	@Transactional
	public void register(Student student) throws UserIdAlreadyExists, UserNotAddedException {
		if(userRepo.findById(student.getuserID()).isPresent()) {
			throw new UserIdAlreadyExists(student.getuserID());
		}
		
		try {
			userService.addUser(student);
		} catch (UserNotAddedException e) {
			throw e;
		}
	}

	public int getRegistrationStatus(String studentName) throws UserNotFoundException {
		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		
		return studentRepo.getRegistrationStatus(userID);
	}

	public int getPaymentStatus(String studentName) throws UserNotFoundException  {
		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		return studentRepo.getPaymentStatus(userID);
	}

	public void setPaymentStatus(String studentName) {
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		studentRepo.setPaymentStatus(userID);		
	}

	public int getApprovalStatus(String studentName) throws StudentNotApprovedException, UserNotFoundException {

		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();

		int approvalStatus = studentRepo.getApprovalStatus(userID);
		if(approvalStatus == 0) {
			throw new StudentNotApprovedException(studentName);
		}
		return approvalStatus;
	}

}
