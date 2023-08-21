/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class UserDAOImpl implements UserDAOInterface {
	//plug logger in UserDAOImpl
	 private static Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	 //1. INFO -- logger.info (menu)
	 //2. DEBUG -- logger.debug (sysout)
	 //3. ERROR -- logger.error( in exceptions in catch block)
	 
	private static volatile UserDAOImpl instance=null;

	/**
	 * Default Constructor
	 */
	private UserDAOImpl() {	}
	
	/**
	 * Method to make UserDAOImpl Singleton
	 * @return instance
	 */
	public static UserDAOImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(UserDAOImpl.class){
				instance=new UserDAOImpl();
			}
		}
		return instance;
	}
	
	Connection connection = DBUtils.getConnection();
	PreparedStatement stmt = null;
	
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		
		try
		{
			stmt=connection.prepareStatement(SQLConstant.VERIFY_CREDENTIALS);
			stmt.setString(1,username);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next())
				throw new UserNotFoundException(username);

			else if(password.equals(resultSet.getString("password")) && role.equalsIgnoreCase(resultSet.getString("role")))
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			logger.error("Error :"+ ex.getMessage());
		}
		
		return false;
	}
	
	public boolean updatePassword(String username, String newPassword) {
		try {
			stmt = connection.prepareStatement(SQLConstant.UPDATE_PASSWORD);
			stmt.setString(1, newPassword);
			stmt.setString(2, username);
			int row = stmt.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return false;
	}



	@Override
	public String getRole(String username) {
		try {
			stmt = connection.prepareStatement(SQLConstant.GET_ROLE);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return rs.getString("role");
			}		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return null;
	}
}