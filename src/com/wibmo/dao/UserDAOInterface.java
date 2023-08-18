/**
 * 
 */
package com.wibmo.dao;

import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public interface UserDAOInterface {
	/**
	 * Method to verify credentials of Users from DataBase
	 * @param userId
	 * @param password
	 * @return Verify credentials operation status
	 * @throws UserNotFoundException 
	 */
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException;
	
	
	/**
	 * Method to update password of user in DataBase
	 * @param userID
	 * @param newPassword
	 * @return Update Password operation Status
	 */
	public boolean updatePassword(String username,String newPassword);
	
	/**
	 * Method to get RoleConstant of User from DataBase
	 * @param userId
	 * @return RoleConstant
	 */
	public String getRole(String username);
}
