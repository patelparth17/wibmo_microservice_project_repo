/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;

/**
 * 
 */
public class ProfessorImpl implements ProfessorInterface{
	
	/**
	 * @param studentId, courseId, grade
	 */
	@Override
	public boolean addGrade(String studentId, String courseId, String grade) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @param professorId
	 * @return List of Courses
	 */
	@Override
	public List<Course> viewCourses(String professorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param professorId
	 * @return professor details: String
	 */
	@Override
	public String getProfessorById(String professorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
