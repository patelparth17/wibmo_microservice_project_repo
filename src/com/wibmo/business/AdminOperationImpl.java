/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.*;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.validator.AdminValidator;

/**
 * 
 */
public class AdminOperationImpl implements AdminOperationInterface{
	
	/**
	 * @param StudentId
	 */
	AdminDAOInterface adminDaoOperation = AdminDAOImpl.getInstance();
	private static volatile AdminOperationImpl instance = null;

	private AdminOperationImpl()
	{
		
	}
	
	/**
	 * Method to make AdminOperation Singleton
	 */
	public static AdminOperationImpl getInstance()
	{
		if(instance == null)
		{
			synchronized(AdminOperationImpl.class){
				instance = new AdminOperationImpl();
			}
		}
		return instance;
	}
	
	/**
	 * @param studentId
	 * @return report card of a student
	 */
	@Override
	public List<RegisteredCourse> generateGradeCard(String studentId){
		return adminDaoOperation.generateReportCard(studentId);
	}
	
	/**
	 * @param studentId, studentList
	 * @throws StudentNotFoundForApprovalException
	 */
	@Override
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException{
		// TODO Auto-generated method stub
		try {
			
			if(AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
				
				throw new StudentNotFoundForApprovalException(studentId);
			}
			adminDaoOperation.approveStudent(studentId);
		}
		catch(StudentNotFoundForApprovalException e) {
			
			throw e;
		}
	}
	
	/**
	 * @param courseCode, courseList
	 * @throws CourseNotFoundException
	 */
	@Override
	public void removeCourse(String courseCode, List<Course> courseList) throws CourseNotFoundException{
		// TODO Auto-generated method stub
		try {
			if(!AdminValidator.isValidDropCourse(courseCode, courseList)) {
				System.out.println("courseCode: " + courseCode + " not present in catalog!");
				throw new CourseNotFoundException(courseCode);
			}
			adminDaoOperation.removeCourse(courseCode);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}
	
	/**
	 * @param course, courseList
	 * @throws CourseAlreadyExistsException 
	 */
	@Override
	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException {
		// TODO Auto-generated method stub
		try {
			if(!AdminValidator.isValidNewCourse(course, courseList)) {
				System.out.println("courseCode: " + course.getCourseCode() + " already present in catalog!");
				throw new CourseAlreadyExistsException(course.getCourseCode());
			}
			adminDaoOperation.addCourse(course);
		}
		catch(CourseAlreadyExistsException e) {
			throw e;
		}
		
	}
	
	/**
	 * courseCode, professorId
	 */
	@Override
	public void assignCourse(String courseCode, String professorId){
		// TODO Auto-generated method stub
			adminDaoOperation.assignCourse(courseCode, professorId);
	}

	@Override
	public List<Course> viewCourses() {
		// TODO Auto-generated method stub
		return adminDaoOperation.viewCourses();
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		adminDaoOperation.addUser(user);
	}

}
