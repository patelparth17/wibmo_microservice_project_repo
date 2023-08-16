/**
 * 
 */
package com.wibmo.dao;

/**
 * 
 */
public interface UserDAOInterface {
	/**
	 * Method to verify credentials of Users from DataBase
	 * @param userId
	 * @param password
	 * @return Verify credentials operation status
	 */
	public boolean authenticateUser(String userId,String password);
	
	
	/**
	 * Method to update password of user in DataBase
	 * @param userID
	 * @param newPassword
	 * @return Update Password operation Status
	 */
	public boolean updatePassword(String userID,String newPassword);
}
