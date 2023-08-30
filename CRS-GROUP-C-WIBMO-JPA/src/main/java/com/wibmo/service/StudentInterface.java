/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.model.Student;

/**
 * 
 */

@Service
public interface StudentInterface {

	/**
	 * Method to add student
	 * @param student
	 * @throws UserIdAlreadyExists
	 * @throws UserNotAddedException
	 */
	public void register(Student student) throws UserIdAlreadyExists, UserNotAddedException;
}
