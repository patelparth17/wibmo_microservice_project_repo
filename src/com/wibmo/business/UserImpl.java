package com.wibmo.business;

import com.wibmo.dao.UserDAOInterface;
import com.wibmo.dao.UserDAOImpl;
import com.wibmo.exception.UserNotFoundException;

public class UserImpl implements UserInterface {
	
	private static volatile UserImpl instance=null;
	UserDAOInterface userDaoInterface= UserDAOImpl.getInstance();
	private UserImpl()
	{
		
	}
	
	/**
	 * Method to make UserImpl Singleton
	 * @return instance
	 */
	public static UserImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(UserImpl.class){
				instance=new UserImpl();
			}
		}
		return instance;
	}
	
	@Override
	public boolean updatePassword(String username,String newPassword) {
		return userDaoInterface.updatePassword(username, newPassword);
	}

	@Override
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		try
		{
			return userDaoInterface.authenticateUser(username, password, role);		
		}
		catch(UserNotFoundException e) {
			throw e;
		}
	}
	
	@Override
	public String getRole(String username) {
		return userDaoInterface.getRole(username);
	}
}