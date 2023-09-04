/**
 * 
 */
package com.wibmo.validator;

import java.util.List;

import com.wibmo.model.Course;
import com.wibmo.model.Student;

/**
 * 
 */
public class AdminValidator {
	
	/**
	 * Method to validate if dropCourse is already present in catalog
	 * @param newCourse
	 * @param courseList
	 * @return true if dropCourse is already present in catalog
	 */
	public static boolean isValidDropCourse(String courseCode, List<Course> courseList) {
		for(Course course: courseList) {
			if(courseCode.equalsIgnoreCase(course.getCourseCode())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to validate if newCourse is not already present in catalog
	 * @param newCourse
	 * @param courseList
	 * @return true if newCourse is not already present in catalog
	 */
	public static boolean isValidNewCourse(Course newCourse, List<Course> courseList) {
		for(Course course: courseList) {
			if(newCourse.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method to validate if studentId is still unapproved
	 * @param studentId
	 * @param studentList
	 * @return if studentId is still unapproved
	 */
	public static boolean isValidUnapprovedStudent(String studentId, List<Student> studentList) {
		if(studentList != null)
		{
			for(Student student: studentList) {
				if(studentId == student.getStudentId()) {
					return true;
				}
			}
		}
		return false;
	}
}
