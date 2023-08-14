/**
 * 
 */
package com.wibmo.business;

import com.wibmo.constants.GenderConstant;

/**
 * 
 */
public class StudentImpl implements StudentInterface{
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
	@Override
	public String register(String name, String userId, String password, GenderConstant gender, int batch, String branch,
			String address) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param userId
	 * @return studentId: String
	 */
	@Override
	public String getStudentId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param studentId
	 * @return student approval status: boolean
	 */
	@Override
	public boolean isApproved(String studentId) {
		// TODO Auto-generated method stub
		return false;
	}

}
