/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.NoCoursesRegisteredException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Grade;
import com.wibmo.model.Professor;
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
		courseRepo.addCourse(course.getCourseCode(), course.getCourseName(), course.getSeats(), course.getProfessorID(), course.getFee());
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
	}

	public void approveStudent(String studentId, List<Student> studentList)
			throws StudentNotFoundForApprovalException, StudentAlreadyApprovedException {
		if (AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
			throw new StudentNotFoundForApprovalException(studentId);
		}
		else if(studentRepo.getApprovalStatus(studentId)==1) {
			throw new StudentAlreadyApprovedException(studentId);
		}
		studentRepo.approveStudent(studentId);
	}
	
	public void approveAllStudents() {
		studentRepo.approveAllStudents();
	}

	public void addProfessor(Professor professor) throws UserNotAddedException, ProfessorNotAddedException {
		try {
			userService.addUser(professor);
		} catch (UserNotAddedException e) {
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

	
	public List<Grade> generateGradeCard(String studentId) throws UserNotFoundException, NoCoursesRegisteredException {
		Optional<Student> student = studentRepo.findByStudentId(studentId);
		if(student.isEmpty()) {
			throw new UserNotFoundException(studentId);
		}
		studentRepo.setGeneratedReportCardStatus(studentId);
		
		List<Map<String, String>> registeredCourses = registeredCourseRepo.findByStudentId(studentId);
		if(registeredCourses.size()==0) {
			throw new NoCoursesRegisteredException(studentId);
		}
		
		List<Grade> grades = new ArrayList<Grade>();
		for(Map<String, String> map : registeredCourses) {
			for(Map.Entry<String,String> entry : map.entrySet()) {
				if(courseRepo.findByCourseCode(entry.getValue()).isPresent()) {
					Course course = courseRepo.findByCourseCode(entry.getValue()).get();
					Grade grade = new Grade();
					grade.setCourseCode(entry.getValue());
					grade.setCourseName(course.getCourseName());
					grade.setGrade(map.get("grade"));
					grades.add(grade);
				}
			}
		}
		
		return grades;
	}
	
	public void approveStudentRegisteration(String studentId) throws StudentAlreadyRegisteredException, UserNotFoundException {
		Optional<Student> student = studentRepo.findByStudentId(studentId);
		if(student.isEmpty()) {
			throw new UserNotFoundException(studentId);
		}
		int registerationStatus = studentRepo.getRegistrationStatus(studentId);
		if(registerationStatus==1) {
			throw new StudentAlreadyRegisteredException(studentId);
		}
		studentRepo.setRegisterationStatus(studentId);
		
	}
}
