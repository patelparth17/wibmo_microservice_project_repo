/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.model.Course;
import com.wibmo.model.Student;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.validator.AdminValidator;

/**
 * 
 */

@Service
public class AdminService {

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	StudentRepository studentRepo;

	public List<Course> viewCourses() {
		List<Course> courses = new ArrayList<Course>();
		courseRepo.findAll().forEach(c -> courses.add(c));
		return courses;
	}

	public void addCourse(Course course, List<Course> courseList) throws CourseAlreadyExistsException {
		if (!AdminValidator.isValidNewCourse(course, courseList)) {
			throw new CourseAlreadyExistsException(course.getCourseCode());
		}
		courseRepo.addCourse(course.getCourseCode(), course.getCourseName(), 10, null, course.getFee());
	}

	public void removeCourse(String courseCode, List<Course> courseList)
			throws CourseNotFoundException, CourseNotDeletedException {
		if (!AdminValidator.isValidDropCourse(courseCode, courseList)) {
			throw new CourseNotFoundException(courseCode);
		}
		courseRepo.removeCourse(courseCode);
	}

	public List<Student> viewPendingAdmissions() {
		return studentRepo.viewPendingAdmissions();
//		return studentRepo.findByIsApproved(false);
	}

	public void approveStudent(String studentId, List<Student> studentList)
			throws StudentNotFoundForApprovalException, StudentAlreadyApprovedException {
		if (AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
			throw new StudentNotFoundForApprovalException(studentId);
		}
		else if(studentRepo.checkIsApproved(studentId)==1) {
			throw new StudentAlreadyApprovedException(studentId);
		}
		studentRepo.approveStudent(studentId);
	}
	
	public void approveAllStudents() {
		studentRepo.approveAllStudents();
	}
}
