/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.User;

/**
 * 
 */
public interface AdminDAOInterface {
	/**
	 * Method to view courses
	 * @return
	 */
	public List<Course> viewCourses();
	
	/**
	 * Method to generate grade card of a Student 
	 * studentid 
	 * @return  
	 */
	public List<RegisteredCourse> generateReportCard(String Studentid);
	
	
	/**
	 * Method to approve a Student 
	 * studentid
	 * studentlist
	 */
	public void approveStudent(String studentid);
	
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 */

	public void removeCourse(String coursecode);
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in the catalog
	 */
	
	public void addCourse(Course course);
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId
	 */
	public void assignCourse(String courseCode, String professorId);
	
	/**
	 * Method to add User
	 * @param user
	 */
	public void addUser(User user);
}
