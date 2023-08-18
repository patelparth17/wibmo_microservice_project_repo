/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.Student;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class StudentDAOImpl implements StudentDAOInterface {
	
	private static volatile StudentDAOImpl instance=null;

	/**
	 * Default Constructor
	 */
	private StudentDAOImpl(){}
	
	/**
	 * Method to make StudentDAOImpl Singleton
	 * @return
	 */
	public static StudentDAOImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(StudentDAOImpl.class){
				instance=new StudentDAOImpl();
			}
		}
		return instance;
	}
	
	@Override
	public String registerStudent(Student student) {
		Connection connection=DBUtils.getConnection();
		
		String studentId = null,sql=null;
		sql= SQLConstant.REGISTER_USER_QUERY;
		try
		{
			//open db connection
			PreparedStatement stmt=connection.prepareStatement(sql);
			stmt.setString(1, student.getUserId());
			stmt.setString(2, student.getName());
			stmt.setString(3, student.getPassword());
			stmt.setString(4, student.getRole().toString());
			stmt.setString(5, student.getGender().toString());
			stmt.setString(6, student.getAddress());
			int rows = stmt.executeUpdate();
			if(rows==1)
			{
				sql = SQLConstant.REGISTER_STUDENT_QUERY;
				PreparedStatement stmt1 = connection.prepareStatement(sql);
				stmt1.setString(1,student.getUserId());
				stmt1.setInt(2, student.getGradYear());
				stmt1.setString(3, student.getDepartment());
				ResultSet rs =stmt1.executeQuery();
				
				if(rs.next())
					studentId=rs.getString(1);
			}
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return studentId;
	}

	@Override
	public String getStudentId(String userId) {
		// TODO Auto-generated method stub
		return null;
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
			System.out.println(e.getMessage());
		}
		
		return false;
	}

}
