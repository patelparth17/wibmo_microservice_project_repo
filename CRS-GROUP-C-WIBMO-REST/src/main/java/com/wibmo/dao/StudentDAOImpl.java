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
//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.bean.Student;
import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class StudentDAOImpl implements StudentDAOInterface {
	
	@Autowired
	private static Logger logger;
	
	@Override
	public void registerStudent(Student student)throws StudentNotRegisteredException {
		Connection connection=DBUtils.getConnection();
		
		String sql=null;
		sql= SQLConstant.REGISTER_USER_QUERY;
		try
		{
			PreparedStatement stmt=connection.prepareStatement(sql);
			stmt.setString(1, student.getUserId());
			stmt.setString(2, student.getName());
			stmt.setString(3, student.getPassword());
			stmt.setString(4, student.getRole().toString());
			stmt.setString(6, student.getGender().toString());
			stmt.setString(5, student.getAddress());
			int rows = stmt.executeUpdate();
			if(rows==1)
			{
				sql = SQLConstant.REGISTER_STUDENT_QUERY;
				PreparedStatement stmt1 = connection.prepareStatement(sql);
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
			throw new StudentNotRegisteredException(ex.getMessage());
		}
		
	}


	@Override
	public boolean isApproved(String studentName) {
		Connection connection=DBUtils.getConnection();
		String sql = SQLConstant.ISAPPROVED_QUERY;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
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
