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
import com.wibmo.bean.Grade;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class RegistrationDAOImpl implements RegistrationDAOInterface{

	private static volatile RegistrationDAOImpl instance=null;
	private PreparedStatement stmt = null;
	
	/**
	 * Default Constructor
	 */
	private RegistrationDAOImpl() {}
	
	/**
	 * Method to make RegistrationDaoOperation Singleton
	 * @return
	 */
	public static RegistrationDAOImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(RegistrationDAOImpl.class){
				instance=new RegistrationDAOImpl();
			}
		}
		return instance;
	}

	/**
	 * Method to retrieve Student's registration status
	 * @param studentId
	 * @return Student's registration status
	 * @throws SQLException
	 */
	@Override
	public boolean getRegistrationStatus(String studentName) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_REGISTRATION_STATUS);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);
			//System.out.println(status);	
		} 
		catch (SQLException e) 
		{
			e.getMessage();

		} 

		return status;
	}

	@Override
	public boolean addCourse(String courseCode, String studentName) throws SQLException{
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_USER_ID);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				stmt = conn.prepareStatement(SQLConstant.ADD_COURSE);
				stmt.setString(1, rs.getString("userID"));
				stmt.setString(2, courseCode);
				stmt.setString(3, "-");
				stmt.executeUpdate();
			}
			
			stmt = conn.prepareStatement(SQLConstant.DECREMENT_COURSE_SEATS);
			stmt.setString(1, courseCode);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.getMessage();
		}
		
		return false;
		
	}
	
	
	/**
	 * Number of registered courses for a student
	 * @param studentId
	 * @return Number of registered courses for a student
	 * @throws SQLException 
	 */
	@Override
	public int numOfRegisteredCourses(String studentName) throws SQLException{
		Connection conn = DBUtils.getConnection();
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
		catch (Exception e)
		{
			e.getMessage();
		}
		return count;
	}


	/**
	 * Check if seat is available for that particular course
	 * @param courseCode
	 * @return status of seat availablity
	 * @throws SQLException 
	 */
	@Override
	public boolean seatAvailable(String courseCode) throws SQLException {
		Connection conn = DBUtils.getConnection();
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
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method checks if the student is registered for that course
	 * @param courseCode
	 * @param studentId
	 * @return Students registration status
	 * @throws SQLException 
	 */
	@Override
	public boolean isRegistered(String courseCode, String studentName) throws SQLException{
		Connection conn = DBUtils.getConnection();

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
		catch(Exception e)
		{
			e.getClass();
			e.getMessage();
		}
		return false;
	}


	/**
	 * Drop Course selected by student
	 * @param courseCode : code for selected course
	 * @param studentId
	 * @return status of drop course operation
	 * @throws CourseNotFoundException 
	 */
	@Override
	public boolean dropCourse(String courseCode, String studentName) throws SQLException {
		Connection conn = DBUtils.getConnection();
		try
		{
			stmt = conn.prepareStatement(SQLConstant.DROP_COURSE_QUERY);
			stmt.setString(2, courseCode);
			stmt.setString(1, studentName);
			stmt.execute();		
			stmt = conn.prepareStatement(SQLConstant.INCREMENT_SEAT_QUERY);
			stmt.setString(1, courseCode);
			stmt.execute();			
			stmt.close();
			return true;
			}
			catch(Exception e)
			{
				System.out.println("Exception found" + e.getMessage());
			}
		
		return false;
	}
	
	/**
	 * Method to retrieve fee for the selected courses from the database and calculate total fee
	 * @param studentId
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	
	@Override
	public double calculateFee(String studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
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
			e.getErrorCode();
			e.getMessage();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return fee;
	}

	/**
	 * Method to view grade card of the student
	 * @param studentId
	 * @throws SQLException 
	 * @return Studen's grade card
	 */
	@Override
	public List<Grade> viewGradeCard(String studentId) throws SQLException {
		
		Connection conn = DBUtils.getConnection();
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
			e.getMessage();
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return grade_List;
	}

	/**
	 * Method to get the list of courses available from course catalog 
	 * @param studentId
	 * @return list of courses
	 * @throws SQLException
	 */
	@Override
	public List<Course> viewCourses(String studentName) throws SQLException {
		List<Course> availableCourseList = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.VIEW_AVAILABLE_COURSES);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				availableCourseList.add(new Course(rs.getString("courseCode"), rs.getString("courseName"),
						rs.getString("professorId"), rs.getInt("seats"), rs.getDouble("courseFee")));
			}
		} 
		catch (SQLException e) 
		{
			e.getMessage();
		} 
		catch (Exception e)
		{
			e.getMessage();
		}		
		return availableCourseList;
		
	}

	/**
	 * Method to get the list of courses registered by the student
	 * @param studentId
	 * @return list of courses registered by student
	 * @throws SQLException 
	 */
	@Override
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException {

		Connection conn = DBUtils.getConnection();
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
			e.getMessage();
		} 
		return registeredCourseList;
	}

	
	/**
	 * Method to set Student's registration status
	 * @param studentId
	 * @throws SQLException
	 */
	@Override
	public void setRegistrationStatus(String studentName) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.SET_REGISTRATION_STATUS);
			stmt.setString(1, studentName);
			stmt.executeUpdate();

		} 
		catch (SQLException e) 
		{
			e.getMessage();

		} 
	
	}

	@Override
	public boolean isReportGenerated(String studentName) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.GET_GENERATED_REPORT_CARD_TRUE);
			stmt.setString(1, studentName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);
			//System.out.println(status);	
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
	
		} 

		return status;
	}

	@Override
	public boolean getPaymentStatus(String studentId) throws SQLException 
	{
		{
			Connection conn = DBUtils.getConnection();
			boolean status = false;
			try 
			{
				stmt = conn.prepareStatement(SQLConstant.GET_PAYMENT_STATUS);
				stmt.setString(1, studentId);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				status = rs.getBoolean(1);
				//System.out.println(status);	
			} 
			catch (SQLException e) 
			{
				e.getMessage();

			} 
			return status;
	}


	}

	@Override
	public void setPaymentStatus(String studentId, PaymentModeConstant modeOfPayment, double amount) throws SQLException {
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLConstant.SET_PAYMENT_STATUS);
			stmt.setString(1, studentId);
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());

		} 
	}

}
