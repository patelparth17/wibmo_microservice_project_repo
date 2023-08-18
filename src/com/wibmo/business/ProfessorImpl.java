/**
 * 
 */
package com.wibmo.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.dao.ProfessorDAOInterface;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public class ProfessorImpl implements ProfessorInterface{
	
	private static volatile ProfessorImpl instance=null;
	ProfessorDAOInterface professorDAOInterface=ProfessorDAOImpl.getInstance();
	private ProfessorImpl()
	{

	}
	
	/**
	 * Method to make ProfessorOperation Singleton
	 * @return
	 */
	public static ProfessorImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(ProfessorImpl.class){
				instance=new ProfessorImpl();
			}
		}
		return instance;
	}
	
	
	/**
	 * Method to grade a Student
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 * @return boolean indicating if grade is added or not
	 * @throws GradeNotAddedException
	 */
	@Override
	
	public boolean addGrade(String studentId,String courseCode,String grade) throws GradeNotAllotedException {
		try
		{
			professorDAOInterface.addGrade(studentId, courseCode, grade);
		}
		catch(Exception ex)
		{
			throw new GradeNotAllotedException(studentId);
		}
		return true;
	}
	
	
	/**
	 * Method to view all the enrolled students
	 * @param courseId: Course id 
	 * @return List of enrolled students
	 * @throws UserNotFoundException 
	 */
	
	@Override
	public List<EnrolledStudent> viewEnrolledStudents(String username){
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try
		{
			enrolledStudents=professorDAOInterface.getEnrolledStudents(username);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return enrolledStudents;
	}

	
	/**
	 * Method to get list of all course a professor is teaching
	 * @param username: professor id 
	 * @return List of courses the professor is teaching
	 * @throws UserNotFoundException 
	 */
	
	@Override
	public List<Course> viewCourses(String username) {
		//call the DAO class
		//get the courses for the professor
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered=professorDAOInterface.getCoursesByProfessor(username);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return coursesOffered;
	}
	
	
	@Override
	public String getProfessorById(String username)
	{
		return professorDAOInterface.getProfessorById(username);
	}
}
