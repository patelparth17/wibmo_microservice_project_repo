package com.wibmo.validator;

import java.util.List;

import com.wibmo.model.Course;
import com.wibmo.model.EnrolledStudent;


/**
 * 
 * @author Parth
 * Class for Professor Validator
 * 
 */
public class ProfessorValidator {
	
	/**
	 * Method to check if Student exist in the database
	 * @param students: list of students in the database
	 * @param studentId: current student
	 * @return true, if student is valid. else, false.
	 */
	public static boolean isValidStudent(List<EnrolledStudent> enrolledStudents,String studentId)
	{
		boolean result=false;
		//check if student exist in the students list
		for(int i=0;i<enrolledStudents.size();i++)
		{
			//role.equalsIgnoreCase("ADMIN")
			if(enrolledStudents.get(i).getStudentId().equals(studentId))
				result=true;
		}
		return result;
	}

	/**
	 * Method to check if professor teaches the course
	 * @param assignedCourses
	 * @param courseCode
	 * @return status
	 */
	public static boolean isValidCourse(List<Course> assignedCourses,String courseCode)
	{
		//check if course is valid
		boolean result=false;
		for(int i=0;i<assignedCourses.size();i++)
		{
			if(assignedCourses.get(i).getCourseCode().equalsIgnoreCase(courseCode))
				result= true;
		}
		return result;
	}
}