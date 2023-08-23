/**
 * 
 */
package com.wibmo.dao;

import org.springframework.stereotype.Repository;

import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
@Repository
public interface UserDAOInterface {
	/**
	 * Method to verify credentials of Users from DataBase
	 * @param username
	 * @param password
	 * @param role
	 * @return status
	 * @throws UserNotFoundException
	 */
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException;
	
	
	/**
	 * Method to update password of user in DataBase
	 * @param username
	 * @param newPassword
	 * @return Update Password operation Status
	 */
	public boolean updatePassword(String username,String newPassword);
	
	/**
	 * Method to get RoleConstant of User from DataBase
	 * @param username
	 * @return RoleConstant
	 */
	public String getRole(String username);
}
