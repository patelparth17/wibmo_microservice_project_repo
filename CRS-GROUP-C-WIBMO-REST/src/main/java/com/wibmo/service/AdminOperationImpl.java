/**
 * 
 */
package com.wibmo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.dao.AdminDAOImpl;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.validator.AdminValidator;

@Service
public class AdminOperationImpl implements AdminOperationInterface{
	
	@Autowired
	private AdminDAOImpl adminDaoOperation;
	
	@Override
	public List<RegisteredCourse> generateGradeCard(String studentId){
		return adminDaoOperation.generateReportCard(studentId);
	}
	
	@Override
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException, StudentAlreadyApprovedException{
		try {
			
			if(AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
				
				throw new StudentNotFoundForApprovalException(studentId);
			}
			try {
				adminDaoOperation.approveStudent(studentId);
			} catch (StudentAlreadyApprovedException e) {
				throw e;
			}
		}
		catch(StudentNotFoundForApprovalException e) {
			
			throw e;
		}
	}
	
	@Override
	public void removeCourse(String courseCode, List<Course> courseList) throws CourseNotFoundException,CourseNotDeletedException{
		try {
			if(!AdminValidator.isValidDropCourse(courseCode, courseList)) {
				throw new CourseNotFoundException(courseCode);
			}
			adminDaoOperation.removeCourse(courseCode);
		}
		catch(CourseNotFoundException | CourseNotDeletedException e) {
			throw e;
		}
	}
	
	@Override
	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException {
		try {
			if(!AdminValidator.isValidNewCourse(course, courseList)) {
				throw new CourseAlreadyExistsException(course.getCourseCode());
			}
			adminDaoOperation.addCourse(course);
		}
		catch(CourseAlreadyExistsException e) {
			throw e;
		}
	}
	
	@Override
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException{
			try {
				adminDaoOperation.assignCourse(courseCode, professorId);
			} catch (CourseNotFoundException | UserNotFoundException e) {
				throw e;
			}
	}

	@Override
	public List<Course> viewCourses() {
		return adminDaoOperation.viewCourses();
	}

	@Override
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyExists {
		try {
			adminDaoOperation.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyExists e) {
			throw e;
		}
	}

	@Override
	public List<Professor> viewProfessors() {
		return adminDaoOperation.viewProfessors();
	}

	@Override
	public List<Student> viewPendingAdmissions() {
		return adminDaoOperation.viewPendingAdmissions();
	}
	
	@Override
	public void approveAllStudents(List<Student> studentList) {
		adminDaoOperation.approveAllStudents(studentList);
	}
}
