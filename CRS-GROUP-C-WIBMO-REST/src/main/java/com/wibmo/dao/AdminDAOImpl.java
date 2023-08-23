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

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.constants.SQLConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;


/**
 * 
 */
@Repository
public class AdminDAOImpl implements AdminDAOInterface{
	
	
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


	public void approveStudent(String studentID)throws StudentNotFoundForApprovalException {
		String sql = SQLConstant.APPROVE_STUDENT_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,studentID);
			int row = stmt.executeUpdate();
			if(row == 0) {
				throw new StudentNotFoundForApprovalException(studentID);
			}
			
			
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			
		}
	}


	@Override
	public void removeCourse(String courseCode)  throws CourseNotFoundException, CourseNotDeletedException{
		String sql = SQLConstant.REMOVE_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,courseCode);
			int row = stmt.executeUpdate();
			if(row == 0) {
				throw new CourseNotFoundException(courseCode);
			}
			
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			throw new CourseNotDeletedException(courseCode);
		}
	}

	@Override
	public void addCourse(Course course) throws CourseAlreadyExistsException {
		String sql = SQLConstant.ADD_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, course.getCourseCode());
			stmt.setString(2, course.getCourseName());
			stmt.setInt(3, 10);
			stmt.setInt(4, 0);
			stmt.setDouble(5, course.getFee());
			int row = stmt.executeUpdate();
			if(row == 0) {
				throw new CourseAlreadyExistsException(course.getCourseCode());
			}
			
		}catch(SQLException se) {
			throw new CourseAlreadyExistsException(course.getCourseCode());
			
		}
	}
	

	@Override
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException {
		String sql1 = SQLConstant.CHECK_PROFESSOR_QUERY;
		String sql2 = SQLConstant.ASSIGN_COURSE_QUERY;
		try {
			Connection connection = DBUtils.getConnection();
			PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, professorId);
			ResultSet rs = stmt1.executeQuery();
			if(!rs.next()) {
				throw new UserNotFoundException(professorId);
			}
			PreparedStatement stmt2 = connection.prepareStatement(sql2);
			stmt2.setString(1, professorId);
			stmt2.setString(2, courseCode);
			int row2 = stmt2.executeUpdate();
			if(row2 == 0) {
				throw new CourseNotFoundException(courseCode);
			}
			
		}catch(SQLException se) {
			throw new UserNotFoundException(professorId);	
		}
		
	}

	@Override
	public void addUser(User user)throws UserNotAddedException, UserIdAlreadyExists {
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
			if(row == 0) {
				throw new UserNotAddedException(user.getUserId()); 
			}
		}catch(SQLException se) {
			throw new UserIdAlreadyExists(user.getUserId());
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
				course.setCourseCode(rs.getString("courseCode"));
				course.setCourseName(rs.getString("courseName"));
				course.setInstructorId(rs.getString("professorID"));
				course.setSeats(rs.getInt("seats"));
				temp.setCourse(course);
				temp.setstudentId(Studentid);
				temp.setGrade(rs.getString("grade"));
				Courses.add(temp);
			}
					
			String sql1 = SQLConstant.SET_STUDENT_REPORT_GENERATION;
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

	@Override
	public void addProfessor(Professor professor) throws UserIdAlreadyExists, ProfessorNotAddedException {
		try {
			this.addUser(professor);
		}catch (UserNotAddedException e) {
			throw new ProfessorNotAddedException(professor.getUserId());		
		}catch (UserIdAlreadyExists e) {
			throw e;
		}
		
		
		PreparedStatement statement = null;
		try {
			Connection connection = DBUtils.getConnection();
			String sql = SQLConstant.ADD_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, professor.getUserId());
			statement.setString(2, professor.getDepartment());
			statement.setString(3, professor.getDesignation());
			int row = statement.executeUpdate();
			if(row == 0) {
				System.out.println("Professor with professorId: " + professor.getUserId() + " not added.");
				throw new ProfessorNotAddedException(professor.getUserId());
			}
			System.out.println("Professor with professorId: " + professor.getUserId() + " added."); 
			
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			throw new UserIdAlreadyExists(professor.getUserId());
		} 
		
	}

	@Override
	public List<Professor> viewProfessors() {
		PreparedStatement statement = null;
		List<Professor> professorList = new ArrayList<Professor>();
		try {
			Connection connection = DBUtils.getConnection();
			String sql = SQLConstant.VIEW_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
		
			while(resultSet.next()) {
				Professor professor = new Professor();
				professor.setUserId(resultSet.getString(1));
				professor.setName(resultSet.getString(2));
				professor.setGender(GenderConstant.stringToGender(resultSet.getString(3)));
				professor.setDepartment(resultSet.getString(4));
				professor.setDesignation(resultSet.getString(5));
				professor.setAddress(resultSet.getString(6));
				professor.setRole(RoleConstant.PROFESSOR);
				professor.setPassword("*********");
				professorList.add(professor);
			}
			System.out.println(professorList.size() + " professors in the institute.");
		
		}catch(SQLException se) {
			System.out.println(se.getMessage());
		
	}
	return professorList;
	}

	@Override
	public List<Student> viewPendingAdmissions() {
		List<Student> studentList = new ArrayList<Student>();
		try {
			Connection connection = DBUtils.getConnection();
			String sql = SQLConstant.VIEW_PENDING_ADMISSIONS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				
				Student student = new Student();
				student.setUserId(resultSet.getString(1));
				student.setName(resultSet.getString(2));
				student.setPassword(resultSet.getString(3));
				student.setRole(RoleConstant.stringToName(resultSet.getString(4)));
				student.setGender(GenderConstant.stringToGender( resultSet.getString(5)));
				student.setAddress(resultSet.getString(6));
				student.setStudentId(resultSet.getString(7));
				studentList.add(student);
				
			}	
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return studentList;
	}
}