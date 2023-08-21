/**
 * 
 */
package com.wibmo.business;

import com.wibmo.exception.UserNotFoundException;

public interface UserInterface {
	
	/**
	 * Method to get role of a specific User
	 * @param username
	 * @return RoleConstant of the User
	 */
	String getRole(String username);

	/**
	 * Method to verify User credentials
	 * @param username
	 * @param password
	 * @param role
	 * @return boolean indicating if user exists in the database
	 */
	boolean authenticateUser(String username, String password, String role) throws UserNotFoundException;

	/**
	 * Method to update password of a user
	 * @param username
	 * @param newPassword
	 * @return boolean indicating if the password is updated successfully
	 */
	boolean updatePassword(String username, String newPassword);
	
}
