/**
 * 
 */
package com.wibmo.business;

import com.wibmo.constants.GenderConstant;

/**
 * 
 */
public interface StudentInterface {
	/**
	 * 
	 * @param name
	 * @param userId
	 * @param password
	 * @param gender
	 * @param batch
	 * @param branch
	 * @param address
	 * @return registration status: String
	 */
	public String register(String name,String userId,String password,GenderConstant gender,int batch,String branch,String address);
	
	/**
	 * 
	 * @param userId
	 * @return studentId: String
	 */
	public String getStudentId(String userId);
	
	/**
	 * 
	 * @param studentId
	 * @return student approval status: boolean
	 */
	public boolean isApproved(String studentId);
}
