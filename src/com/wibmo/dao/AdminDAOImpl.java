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
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.User;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class AdminDAOImpl implements AdminDAOInterface{
	
	private static volatile AdminDAOImpl instance = null;
	
	/**
	 * Default Constructor
	 */
	private AdminDAOImpl(){}
	
	/**
	 * Method to make AdminDAOImpl Singleton
	 * @return
	 */
	public static AdminDAOImpl getInstance()
	{
		if(instance == null)
		{
			synchronized(AdminDAOImpl.class){
				instance = new AdminDAOImpl();
			}
		}
		return instance;
	}
	
	@Override
	public List<Course> viewCourses() {
		List<Course> courseList = new ArrayList<>();
		String sql = SQLConstant.VIEW_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course course = new Course();
				course.setCourseCode(rs.getString(1));
				course.setCourseName(rs.getString(2));
				course.setInstructorId(rs.getString(3));
				courseList.add(course);	
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return courseList;
	}


	@Override
	public void approveStudent(String studentID) {
		String sql = SQLConstant.APPROVE_STUDENT_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,studentID);
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("Student with ID "+studentID+" approved.");
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());			
		}
	}


	@Override
	public void removeCourse(String courseCode) {
		String sql = SQLConstant.REMOVE_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,courseCode);
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("Course with course code "+courseCode+" removed.");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());		
			}
	}

	@Override
	public void addCourse(Course course) {
		String sql = SQLConstant.ADD_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, course.getCourseCode());
			stmt.setString(2, course.getCourseName());
			stmt.setInt(3, 10);
			stmt.setString(4, "NOT_GRADED");
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("Course with name "+course.getCourseName()+" added.");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());		
			}
	}
	

	@Override
	public void assignCourse(String courseCode, String professorId) {
		String sql = SQLConstant.ASSIGN_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, professorId);
			stmt.setString(2, courseCode);
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("Course with code "+courseCode+" assigned to Profesor with ID "+professorId);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());		
			}
	}

	@Override
	public void addUser(User user) {
		String sql = SQLConstant.ADD_USER_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getRole().toString());
			stmt.setString(5, user.getGender().toString());
			stmt.setString(6, user.getAddress());
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("User with name "+user.getName()+" is added. ");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());		
			}
	}

	@Override
	public List<RegisteredCourse> generateReportCard(String Studentid) {
		List<RegisteredCourse> Courses = new ArrayList<RegisteredCourse>();
		String sql = SQLConstant.GENERATE_REPORTCARD_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, Studentid);
			ResultSet rs = stmt.executeQuery();
					
			while(rs.next()) {
				Course course = new Course();
				RegisteredCourse temp = new RegisteredCourse() ;
				course.setCourseCode(rs.getString(1));
				course.setCourseName(rs.getString(2));
				course.setInstructorId(rs.getString(3));
				course.setSeats(rs.getInt(4));
				temp.setCourse(course);
				temp.setstudentId(Studentid);
				temp.setGrade(rs.getString(8));
				Courses.add(temp);
			}
					
			String sql1 = "update student set isReportGenerated = 1 where studentId = ?";
			stmt = connection.prepareStatement(sql1);
			stmt.setString(1, Studentid);
			int row = stmt.executeUpdate();
			if(row==1) {
				System.out.println("Report card generated.");
			}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		
		return Courses;	
	}

	
}