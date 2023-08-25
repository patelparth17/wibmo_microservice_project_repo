/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.bean.Student;
import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.utils.DBUtils;

/**
 * Implementation of methods which performs read and write operations of Student in Database
 */
@Repository
public class StudentDAOImpl implements StudentDAOInterface {
	
	@Autowired
	private static Logger logger = Logger.getLogger(StudentDAOImpl.class);
	
	Connection connection=DBUtils.getConnection();
	
	@Override
	public void registerStudent(Student student)throws StudentNotRegisteredException {
		try
		{
			PreparedStatement stmt=connection.prepareStatement(SQLConstant.REGISTER_USER_QUERY);
			stmt.setString(1, student.getUserId());
			stmt.setString(2, student.getName());
			stmt.setString(3, student.getPassword());
			stmt.setString(4, student.getRole().toString());
			stmt.setString(6, student.getGender().toString());
			stmt.setString(5, student.getAddress());
			int rows = stmt.executeUpdate();
			if(rows==1)
			{
				PreparedStatement stmt1 = connection.prepareStatement(SQLConstant.REGISTER_STUDENT_QUERY);
				stmt1.setString(1,student.getUserId());
				stmt1.setInt(2, student.getGradYear());
				stmt1.setString(3, student.getDepartment());
				stmt1.setBoolean(4, false);
				stmt1.setBoolean(5, false);
				stmt1.setBoolean(6, false);
				stmt1.setBoolean(7, false);
				int row =stmt1.executeUpdate();
				
				if(row==1) 
					logger.debug("Row Inserted");
			}
		}
		catch(SQLException ex)
		{
			throw new StudentNotRegisteredException(student.getUserId());
		}
		
	}


	@Override
	public boolean isApproved(String studentName) {
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLConstant.ISAPPROVED_QUERY);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				return rs.getBoolean("isApproved");
			}
				
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}
}
