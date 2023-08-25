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
import org.springframework.stereotype.Repository;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 * Implementation of methods which performs read and write operations of Registration in Database
 */
@Repository
public class RegistrationDAOImpl implements RegistrationDAOInterface{
	
	private static Logger logger = Logger.getLogger(RegistrationDAOImpl.class);
	
	Connection conn = DBUtils.getConnection();
	private PreparedStatement stmt = null;
	
	@Override
	public boolean getRegistrationStatus(String studentName) throws SQLException
	{
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_REGISTRATION_STATUS);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);	
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 

		return status;
	}

	@Override
	public boolean addCourse(String courseCode, String studentName) throws SQLException{
		
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_USER_ID);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				stmt = conn.prepareStatement(SQLConstant.ADD_COURSE);
				stmt.setString(1, rs.getString("userID"));
				stmt.setString(2, courseCode);
				stmt.setString(3, "NOT_GRADED");
				stmt.executeUpdate();
				logger.debug("Primary course added!");
			}
			
			stmt = conn.prepareStatement(SQLConstant.DECREMENT_COURSE_SEATS);
			stmt.setString(1, courseCode);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public int numOfRegisteredCourses(String studentName) throws SQLException{
		int count = 0;
		try {
			stmt = conn.prepareStatement(SQLConstant.NUMBER_OF_REGISTERED_COURSES);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
			}
			return count;
		}
		catch (SQLException se) 
		{
			se.getMessage();
		}
		return count;
	}

	@Override
	public boolean seatAvailable(String courseCode) throws SQLException {
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_SEATS);
			stmt.setString(1, courseCode);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return (rs.getInt("seats") > 0);
			}
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	@Override
	public boolean isRegistered(String courseCode, String studentName) throws SQLException{
		try
		{
			stmt = conn.prepareStatement(SQLConstant.GET_REGISTRATION_STATUS);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				if(rs.getBoolean("isRegistered"))
					return true;
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean dropCourse(String courseCode, String studentName) throws SQLException {
		try
		{
			stmt = conn.prepareStatement(SQLConstant.DROP_COURSE_QUERY);
			stmt.setString(2, courseCode);
			stmt.setString(1, studentName);
			stmt.execute();		
			stmt = conn.prepareStatement(SQLConstant.INCREMENT_SEAT_QUERY);
			stmt.setString(1, courseCode);
			stmt.execute();	
			///
			stmt = conn.prepareStatement(SQLConstant.GET_SECONDARY_COURSES);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String secondaryCourseCode = rs.getString("courseCode");
				addCourse(secondaryCourseCode, studentName);
				stmt = conn.prepareStatement(SQLConstant.DROP_SECONDARY_COURSE);
				stmt.setString(2, secondaryCourseCode);
				stmt.setString(1, studentName);
				stmt.execute();
				
				stmt = conn.prepareStatement(SQLConstant.DECREMENT_COURSE_SEATS);
				stmt.setString(1, secondaryCourseCode);
				stmt.execute();
			}
			
			stmt.close();
			return true;
			}
			catch(SQLException e)
			{
				logger.error("Exception found" + e.getMessage());
			}
		
		return false;
	}
	
	
	@Override
	public double calculateFee(String studentId) throws SQLException
	{
		double fee = 0;
		try
		{
			stmt = conn.prepareStatement(SQLConstant.CALCULATE_FEES);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			fee = rs.getDouble(1);
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return fee;
	}


	@Override
	public List<Grade> viewGradeCard(String studentId) throws SQLException {
		List<Grade> grade_List = new ArrayList<Grade>();
		try
		{
			stmt = conn.prepareStatement(SQLConstant.VIEW_GRADE);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				String courseCode = rs.getString("courseCode");
				String courseName = rs.getString("courseName");
				String grade = rs.getString("grade");
				Grade obj = new Grade(courseCode, courseName,grade);
				grade_List.add(obj);
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return grade_List;
	}


	@Override
	public List<Course> viewCourses(String studentName) throws SQLException {
		List<Course> availableCourseList = new ArrayList<>();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.VIEW_AVAILABLE_COURSES);
			stmt.setString(1, studentName);
			stmt.setString(2, studentName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				availableCourseList.add(new Course(rs.getString("courseCode"), rs.getString("courseName"),
						rs.getString("professorId"), rs.getInt("seats"), rs.getDouble("courseFee")));
			}
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}		
		return availableCourseList;
		
	}


	@Override
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException {
		List<Course> registeredCourseList = new ArrayList<>();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.VIEW_REGISTERED_COURSES);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				registeredCourseList.add(new Course(rs.getString("courseCode"), rs.getString("courseName"),
						rs.getString("professorId"), rs.getInt("seats"), rs.getDouble("courseFee")));
			}
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
		return registeredCourseList;
	}

	@Override
	public void setRegistrationStatus(String studentName) throws SQLException
	{
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.SET_REGISTRATION_STATUS);
			stmt.setString(1, studentName);
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
	}

	@Override
	public boolean isReportGenerated(String studentName) throws SQLException
	{
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_GENERATED_REPORT_CARD_TRUE);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		return status;
	}

	@Override
	public boolean getPaymentStatus(String studentId) throws SQLException 
	{
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_PAYMENT_STATUS);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);	
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
		return status;
	}

	@Override
	public void setPaymentStatus(String studentId, PaymentModeConstant modeOfPayment, double amount) throws SQLException {
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.SET_PAYMENT_STATUS);
			stmt.setString(1, studentId);
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
	}

	@Override
	public boolean addSecondaryCourse(String courseCode, String studentName) {
		try {
			stmt = conn.prepareStatement(SQLConstant.GET_USER_ID);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				stmt = conn.prepareStatement(SQLConstant.ADD_SECONDARY_COURSE);
				stmt.setString(1, rs.getString("userID"));
				stmt.setString(2, courseCode);
				stmt.executeUpdate();
			}
		
			stmt = conn.prepareStatement(SQLConstant.DECREMENT_COURSE_SEATS);
			stmt.setString(1, courseCode);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
	
		return false;
	}

	@Override
	public int numSecondaryCourses(String studentName) {
		int count = 0;
		try {
			stmt = conn.prepareStatement(SQLConstant.NUMBER_OF_SECONDARY_COURSES);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
			}
			return count;
		}
		catch (SQLException se) 
		{
			se.getMessage();
		}
		return count;
	}
}