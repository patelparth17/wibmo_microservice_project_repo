/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.dao.ProfessorDAOInterface;
import com.wibmo.exception.GradeNotAllotedException;

public class ProfessorImpl implements ProfessorInterface{
	private static volatile ProfessorImpl instance=null;
	ProfessorDAOInterface professorDAOInterface=ProfessorDAOImpl.getInstance();
	private ProfessorImpl()
	{

	}
	
	/**
	 * Method to make ProfessorImpl Singleton
	 * @return instance
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
	
	@Override
	public List<Course> viewCourses(String username) {
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
}
