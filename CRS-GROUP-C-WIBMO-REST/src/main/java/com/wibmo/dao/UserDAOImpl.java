/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import org.apache.log4j.Logger;

import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class UserDAOImpl implements UserDAOInterface {
		
	@Autowired
	private static Logger logger;

	
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		Connection connection = DBUtils.getConnection();
		try
		{
			PreparedStatement stmt=connection.prepareStatement(SQLConstant.VERIFY_CREDENTIALS);
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
				throw new UserNotFoundException(username);
			}
			
		}
		catch(SQLException ex)
		{
			logger.error("Error :"+ ex.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
		return null;
	}
}