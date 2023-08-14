/**
 * 
 */
package com.wibmo.business;
import java.util.List;

import com.wibmo.bean.*;
/**
 * 
 */
public interface AdminOperationInterface {
	/**
	 * 
	 * @param StudentId
	 */
	public void setReportCardStatus(String StudentId);
	
	/**
	 * 
	 * @return
	 */
	
//	public List<RegisteredCourse> generateGradeCard(String studentId);
	
	public List<Professor> displayProfessorsList();
	
	/**
	 * 
	 * @return
	 */
	public List<Student> viewAdmissionsStatus();
	
	/**
	 * 
	 * @param studentId
	 * @param studentList
	 */
	public void approveStudent(String studentId, List<Student> studentList);
	
	/**
	 * 
	 * @param professor
	 */
	public void addProfessor(Professor professor);
	
	/**
	 * 
	 * @param coursecode
	 * @param courselist
	 */
	public void removeCourse(String courseCode, List<Course> courseList);
	
	/**
	 * 
	 * @param course
	 * @param courselist
	 */
	public void addCourse(Course course, List<Course> courseList);
	
	/**
	 * 
	 * @param courseCode
	 * @param professorId
	 */
	public void assignCourse(String courseCode, String professorId);
}
