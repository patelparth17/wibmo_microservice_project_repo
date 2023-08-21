/**
 * 
 */
package com.wibmo.business;

import com.wibmo.constants.GenderConstant;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * 
 */
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
	 * @return registration status: String
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
