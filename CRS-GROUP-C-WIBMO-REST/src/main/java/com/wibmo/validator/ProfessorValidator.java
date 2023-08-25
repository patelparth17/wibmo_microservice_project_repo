package com.wibmo.validator;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;


/**
 * Validates professor related operations
 */
public class ProfessorValidator {
	
	/**
	 * Method to check if Student exist in the database
	 * @param enrolledStudents
	 * @param studentId
	 * @return status
	 */
	public static boolean isValidStudent(List<EnrolledStudent> enrolledStudents,String studentId)
	{
		boolean result=false;
		//check if student exist in the students list
		for(int i=0;i<enrolledStudents.size();i++)
		{
			//role.equalsIgnoreCase("ADMIN")
			if(enrolledStudents.get(i).getStudentId()==studentId)
				result=true;
				
		}
		return result;
	}
	
	/**
	 * Method to check if course exist in the database
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