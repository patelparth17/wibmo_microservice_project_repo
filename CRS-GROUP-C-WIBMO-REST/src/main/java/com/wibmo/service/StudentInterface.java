/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.constants.GenderConstant;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * Interface of methods which calls Student DAO methods 
 */
@Service
public interface StudentInterface {
	/**
	 * Method to register by student
	 * @param name
	 * @param userId
	 * @param password
	 * @param gender
	 * @param gradYear
	 * @param dept
	 * @param address
	 * @throws StudentNotRegisteredException 
	 */
	public void register(String name,String userId,String password,GenderConstant gender,int gradYear,String dept,String address) throws StudentNotRegisteredException;
	
	
	/**
	 * Method to get the approval status of student
	 * @param studentName
	 * @return student approval status: boolean
	 */
	public boolean isApproved(String studentName);
}
