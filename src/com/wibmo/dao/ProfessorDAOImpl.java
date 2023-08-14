/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class ProfessorDAOImpl implements ProfessorDAOInterface{
	
	private static volatile ProfessorDAOImpl instance=null;

	/**
	 * Default Constructor
	 */
	private ProfessorDAOImpl()	{}
	
	/**
	 * Method to make ProfessorDaoOperation Singleton
	 * @return
	 */
	public static ProfessorDAOImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(ProfessorDAOImpl.class){
				instance=new ProfessorDAOImpl();
			}
		}
		return instance;
	}
	
	@Override
	public List<Course> getCoursesByProfessor(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnrolledStudent> viewStudentList(String courseId) {
		Connection connection=DBUtils.getConnection();
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		String sql = SQLConstant.VIEW_STUDENTLIST_QUERY;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, courseId);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				//public EnrolledStudent(String courseCode, String courseName, int studentId) 
				enrolledStudents.add(new EnrolledStudent(rs.getString("courseCode"),rs.getString("courseName"),rs.getString("studentId")));
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return enrolledStudents;
	}

	@Override
	public Boolean recordGrade(String studentId, String courseCode, String grade) {
		Connection connection=DBUtils.getConnection();
		String sql = SQLConstant.RECORD_GRADE_QUERY;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, grade);
			stmt.setString(2, courseCode);
			stmt.setString(3, studentId);
			int row = stmt.executeUpdate();
			
			if(row==1)
				return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String getProfessorById(String profId) {
		// TODO Auto-generated method stub
		return null;
	}

}