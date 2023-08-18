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
import com.wibmo.exception.UserNotFoundException;
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
	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param userId, prof id of the professor
	 * @return get the courses offered by the professor.
	 * @throws UserNotFoundException 
	 */
	@Override
	public List<Course> getCoursesByProfessor(String username) {
		Connection connection=DBUtils.getConnection();
		List<Course> courseList=new ArrayList<Course>();
		
		try {
//			PreparedStatement stmt = connection.prepareStatement(SQLConstant.GET_PROF_ID);
//			stmt.setString(1, username);
//			ResultSet result = stmt.executeQuery();
			
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
			e.getMessage();
		}
		return courseList;
		
	}

	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: username: professor id 
	 * @param: courseCode: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 * @throws UserNotFoundException 
	 */
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
			e.getMessage();
		}
		return enrolledStudents;
	}
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param: username: professor id 
	 * @param: courseCode: course code for the corresponding 
	 * @return: returns the status after adding the grade
	 */
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
			e.getMessage();
		}
		return false;
	}
	

	/**
	 * Method to Get professor name by id
	 * @param username
	 * @return Professor Id in string
	 */
	@Override
	public String getProfessorById(String username)
	{
		String prof_Name = null;
		Connection connection=DBUtils.getConnection();
		try 
		{
			PreparedStatement statement = connection.prepareStatement(SQLConstant.GET_PROF_NAME);
			
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			rs.next();
			
			prof_Name = rs.getString(1);
			
		}
		catch(SQLException e)
		{
			e.getMessage();
		}
		
		return prof_Name;
	}

}