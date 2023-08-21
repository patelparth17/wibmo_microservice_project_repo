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

import org.apache.log4j.Logger;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

public class ProfessorDAOImpl implements ProfessorDAOInterface{
	private static Logger logger = Logger.getLogger(ProfessorDAOImpl.class);
	private static volatile ProfessorDAOImpl instance=null;

	/**
	 * Default Constructor
	 */
	private ProfessorDAOImpl()	{}
	
	/**
	 * Method to make ProfessorDAOImpl Singleton
	 * @return instance
	 */
	public static ProfessorDAOImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(ProfessorDAOImpl.class){
				instance=new ProfessorDAOImpl();
			}
		}
		return instance;
	}
	
	@Override
	public List<Course> getCoursesByProfessor(String username) {
		Connection connection=DBUtils.getConnection();
		List<Course> courseList=new ArrayList<Course>();
		
		try {			
			PreparedStatement statement = connection.prepareStatement(SQLConstant.GET_COURSES);
			statement.setString(1, username);
			ResultSet rs=statement.executeQuery();
			while(rs.next())
			{
				courseList.add(new Course(rs.getString("courseCode"),rs.getString("courseName"),rs.getString("professorId"),rs.getInt("seats"), rs.getDouble("courseFee")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return courseList;
		
	}

	@Override
	public List<EnrolledStudent> getEnrolledStudents(String username) {
		Connection connection=DBUtils.getConnection();
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLConstant.GET_ENROLLED_STUDENTS);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			while(results.next())
			{ 
				enrolledStudents.add(new EnrolledStudent(results.getString("courseCode"),results.getString("courseName"),results.getString("studentId")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return enrolledStudents;
	}
	
	public Boolean addGrade(String studentId,String courseCode,String grade) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLConstant.ADD_GRADE);
			statement.setString(1, grade);
			statement.setString(2, courseCode);
			statement.setString(3, studentId);
			int row = statement.executeUpdate();
			return (row==1);
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}

}