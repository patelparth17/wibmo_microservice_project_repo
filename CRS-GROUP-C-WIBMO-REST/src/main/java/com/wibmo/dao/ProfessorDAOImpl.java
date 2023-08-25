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

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

//import org.apache.log4j.Logger;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;

/**
 * Implementation of methods which performs read and write operations of Professor in Database
 */
@Repository
public class ProfessorDAOImpl implements ProfessorDAOInterface{
	
    private static Logger logger ;
	
    Connection connection=DBUtils.getConnection();
    PreparedStatement statement = null;
    
	@Override
	public List<Course> getCoursesByProfessor(String username) {
		
		List<Course> courseList=new ArrayList<Course>();
		
		try {			
			statement = connection.prepareStatement(SQLConstant.GET_COURSES);
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
	public List<EnrolledStudent> getEnrolledStudents(String username) throws UserNotFoundException {
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try {
			statement = connection.prepareStatement(SQLConstant.GET_ENROLLED_STUDENTS);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			while(results.next())
			{ 
				enrolledStudents.add(new EnrolledStudent(results.getString("courseCode"),results.getString("courseName"),results.getString("studentId")));
			}
		}
		catch(SQLException e)
		{
			throw new  UserNotFoundException(username);
		}
		return enrolledStudents;
	}
	
	public Boolean addGrade(String studentId,String courseCode,String grade) {
		try {
			statement = connection.prepareStatement(SQLConstant.ADD_GRADE);
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