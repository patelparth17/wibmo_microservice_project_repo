/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class UserDAOImpl implements UserDAOInterface {
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
	
	public boolean authenticateUser(String userId, String password, String role) throws UserNotFoundException {
		Connection connection = DBUtils.getConnection();
		try
		{
			//open db connection
			PreparedStatement stmt=connection.prepareStatement(SQLConstant.VERIFY_CREDENTIALS);
			stmt.setString(1,userId);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next())
				throw new UserNotFoundException(userId);

			else if(password.equals(resultSet.getString("password")) && role.equals(resultSet.getString("role")))
			{
				System.out.println("Connection successful");
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
	
	public boolean updatePassword(String userID, String newPassword) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLConstant.UPDATE_PASSWORD);
			stmt.setString(1, newPassword);
			stmt.setString(2, userID);
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
	public String getRole(String userId) {
		// TODO Auto-generated method stub
		Connection connection=DBUtils.getConnection();
		try {
			System.out.println("Username:");
			
			PreparedStatement statement = connection.prepareStatement(SQLConstant.GET_ROLE);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			System.out.println("Query executed.");
			
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
