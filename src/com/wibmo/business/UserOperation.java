package com.wibmo.business;

import com.wibmo.dao.UserDAOInterface;
import com.wibmo.dao.UserDAOImpl;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 * @author Goenka
 * Implementations of User Operations
 *
 */
public class UserOperation implements UserInterface {
	
	private static volatile UserOperation instance=null;
	UserDAOInterface userDaoInterface= UserDAOImpl.getInstance();
	private UserOperation()
	{
		
	}
	
	/**
	 * Method to make UserOperation Singleton
	 * @return
	 */
	public static UserOperation getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(UserOperation.class){
				instance=new UserOperation();
			}
		}
		return instance;
	}

	/**
	 * Method to update password of a user
	 * @param userID
	 * @param newPassword
	 * @return boolean indicating if the password is updated successfully
	 */
	
	@Override
	public boolean updatePassword(String username,String newPassword) {
		return userDaoInterface.updatePassword(username, newPassword);
	}

	
	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 * @return boolean indicating if user exists in the database
	 */

	@Override
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		//DAO class
		try
		{
			return userDaoInterface.authenticateUser(username, password, role);		
		}
		finally
		{
			
		}
	}
	
	/**
	 * Method to get role of a specific User
	 * @param userId
	 * @return RoleConstant of the User
	 */
	@Override
	public String getRole(String username) {
		return userDaoInterface.getRole(username);
	}

	


	

}