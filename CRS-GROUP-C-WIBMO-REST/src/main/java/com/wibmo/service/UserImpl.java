package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.UserDAOInterface;
import com.wibmo.exception.UserNotFoundException;

@Service
public class UserImpl implements UserInterface {
	
	@Autowired
	private UserDAOInterface userDaoInterface;
//	UserDAOInterface userDaoInterface= UserDAOImpl.getInstance();
//	private UserImpl()
//	{
//		
//	}
//	
//	/**
//	 * Method to make UserImpl Singleton
//	 * @return instance
//	 */
//	public static UserImpl getInstance()
//	{
//		if(instance==null)
//		{
//			synchronized(UserImpl.class){
//				instance=new UserImpl();
//			}
//		}
//		return instance;
//	}
	
	@Override
	public boolean updatePassword(String username,String newPassword) throws UserNotFoundException {
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