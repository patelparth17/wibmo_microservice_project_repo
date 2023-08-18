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
	 * Method to make UserDaoOperation Singleton
	 * @return
	 */
	public static UserDAOImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(UserDAOImpl.class){
				instance=new UserDAOImpl();
			}
		}
		return instance;
	}
	
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		Connection connection = DBUtils.getConnection();
		try
		{
			PreparedStatement stmt=connection.prepareStatement(SQLConstant.VERIFY_CREDENTIALS);
			stmt.setString(1,username);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next())
				throw new UserNotFoundException(username);

			else if(password.equals(resultSet.getString("password")) && role.equals(resultSet.getString("role")))
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
			System.out.println("Error :"+ ex.getMessage());
		}
		
		return false;
	}
	
	public boolean updatePassword(String username, String newPassword) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLConstant.UPDATE_PASSWORD);
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
			System.out.println(e.getMessage());
		}
		
		return false;
	}



	@Override
	public String getRole(String username) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLConstant.GET_ROLE);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				return rs.getString("role");
			}		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
}