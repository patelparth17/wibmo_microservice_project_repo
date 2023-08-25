package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.UserDAOInterface;
import com.wibmo.exception.UserNotFoundException;

/**
 * Implementation of methods which calls User DAO methods 
 */
@Service
public class UserImpl implements UserInterface {
	
	@Autowired
	private UserDAOInterface userDaoInterface;
	
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