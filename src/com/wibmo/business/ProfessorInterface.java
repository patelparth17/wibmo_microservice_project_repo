/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;

/**
 * 
 */
public interface ProfessorInterface {
	
	public boolean addGrade(String studentId, String courseId, String grade);
	
	public List<Course> viewCourses(String professorId);
	
	public String getProfessorById(String professorId);
	
//	public List<EnrolledStudent> viewEnrolledStudents(String professorId);
}
