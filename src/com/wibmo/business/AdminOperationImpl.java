/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;

/**
 * 
 */
public class AdminOperationImpl implements AdminOperationInterface{
	
	/**
	 * @param StudentId
	 */
	@Override
	public void setReportCardStatus(String StudentId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return List of Professor
	 */
	@Override
	public List<Professor> displayProfessorsList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param studentId
	 */
//	@Override
//	public List<RegisteredCourse> generateGradeCard(String studentId){
	
//	}
	
	/**
	 * @return List of Student
	 */
	@Override
	public List<Student> viewAdmissionsStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param studentId, studentList
	 */
	@Override
	public void approveStudent(String studentId, List<Student> studentList) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param professor
	 */
	@Override
	public void addProfessor(Professor professor) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param courseCode, courseList
	 */
	@Override
	public void removeCourse(String courseCode, List<Course> courseList) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param course, courseList
	 */
	@Override
	public void addCourse(Course course, List<Course> courseList) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * courseCode, professorId
	 */
	@Override
	public void assignCourse(String courseCode, String professorId) {
		// TODO Auto-generated method stub
		
	}

}
