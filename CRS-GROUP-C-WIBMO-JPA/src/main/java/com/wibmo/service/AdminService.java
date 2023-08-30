/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
//import com.wibmo.constants.NotificationTypeConstant;
//import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentAlreadyRegistered;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Professor;
import com.wibmo.model.RegisteredCourse;
import com.wibmo.model.Student;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.validator.AdminValidator;

/**
 * 
 */

@Service
public class AdminService implements AdminInterface {

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	ProfessorService professorService;
	
	@Autowired
	RegisteredCourseRepository registeredCourseRepo;
	

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

	public void sendNotification(NotificationTypeConstant type, String name) {
		
		notificationService.sendNotification(type, name);
	}

	public void addProfessor(Professor professor) throws UserNotAddedException, ProfessorNotAddedException {
		try {
			userService.addUser(professor);
		} catch (UserNotAddedException e) {
			throw e;
		}
		
		try {
			professorService.addProfessor(professor);
		} catch (ProfessorNotAddedException e) {
			throw e;
		}
		
	}

	public void assignCourse(String courseCode, String professorId) throws UserNotFoundException, CourseNotFoundException {
		if(!professorService.isProfessorExists(professorId)) {
			throw new UserNotFoundException(professorId);
		}
		if(courseRepo.findByCourseCode(courseCode).isEmpty()) {
			throw new CourseNotFoundException(courseCode);
		}
		courseRepo.assignCourse(courseCode,professorId);
		
	}

	
	public List<RegisteredCourse> generateGradeCard(String studentId) {
		
		List<RegisteredCourse> registeredCourses = registeredCourseRepo.findByStudentId(studentId);
		for(RegisteredCourse registeredCourse : registeredCourses) {
			System.out.println(registeredCourse.getCourseCode());
			if(courseRepo.findByCourseCode(registeredCourse.getCourseCode()).isPresent()) {
				Course course = courseRepo.findByCourseCode(registeredCourse.getCourseCode()).get();
				registeredCourse.setCourse(course);
			}
		}
		
		return registeredCourses;
	}
	
	public void approveStudentRegisteration(String studentId) throws StudentAlreadyRegistered, UserNotFoundException {
		studentRepo.setRegisterationStatus(studentId);
		String userId = userRepo.findByUserID(studentId).getuserID();
		int registerationStatus = studentRepo.getRegistrationStatus(userId);
		if(userId==null) {
			throw new UserNotFoundException(userId);
		}
		if(registerationStatus==1) {
			throw new StudentAlreadyRegistered(userId);
		}
	}
}
