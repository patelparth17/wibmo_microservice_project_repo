/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public interface UserInterface {

	/**
	 * Method to find username by userID
	 * @param studentId
	 * @return username
	 */
	public String findUserName(String studentId);
}
