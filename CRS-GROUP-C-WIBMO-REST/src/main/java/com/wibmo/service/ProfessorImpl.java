/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;

/**
 * Implementation of methods which calls Professor DAO methods 
 */
@Service
public class ProfessorImpl implements ProfessorInterface{
	
	@Autowired
	private ProfessorDAOImpl professorDAOInterface;


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
	public List<EnrolledStudent> viewEnrolledStudents(String username) throws UserNotFoundException{
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try
		{
			enrolledStudents=professorDAOInterface.getEnrolledStudents(username);
		}
		catch(UserNotFoundException ex)
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
