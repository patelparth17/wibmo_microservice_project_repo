/**
 * 
 */
package com.wibmo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.NoCoursesRegisteredException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Professor;
import com.wibmo.model.Student;
import com.wibmo.service.AdminService;
import com.wibmo.service.NotificationService;
import com.wibmo.service.UserService;

/**
 * 
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotificationService notificationService;
	
	/**
	 * Method to view courses in catalog
	 * @return List of courses in catalog
	 */
	@RequestMapping(value = "/viewCourseList", method = RequestMethod.GET)
	public List<Course> viewCoursesInCatalogue(){
		return adminService.viewCourses();
	}
	
	/**
	 * Method to add course to catalog
	 * @param courseCode
	 * @param courseName
	 * @param courseFee
	 * @return status message
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addCourseToCatalogue", method = RequestMethod.POST)
	public ResponseEntity addCourseToCatalogue(
			@RequestParam String courseCode,
			@RequestParam String courseName, 
			@RequestParam double courseFee) {
		
		List<Course> courseList = adminService.viewCourses();
		Course course = new Course(courseCode,courseName,"0",10,courseFee);
		try {
			adminService.addCourse(course, courseList);
		} catch (CourseAlreadyExistsException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course Added to Catalog Successfully!", HttpStatus.CREATED);
	}
	
	/**
	 * Method to delete course from catalog
	 * @param courseCode
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteCourse", method = RequestMethod.DELETE)
	public ResponseEntity deleteCourse(@RequestParam String courseCode) {
		List<Course> courseList = adminService.viewCourses();
		try {
			adminService.removeCourse(courseCode, courseList);
		} catch (CourseNotFoundException | CourseNotDeletedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course with "+ courseCode +" is deleted.", HttpStatus.OK);
	}
	
	/**
	 * Method to view pending approvals 
	 * @return List of students having pending approvals
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewPendingAdmissions", method = RequestMethod.GET)
	public ResponseEntity viewPendingAdmissions(){
		List<Student> pendingStudentsList = adminService.viewPendingAdmissions();
		if(pendingStudentsList != null) {
			List<String> pendingStudentNames = new ArrayList<String>();
			for(Student students: pendingStudentsList) {
				pendingStudentNames.add(students.getusername());
			}
			return new ResponseEntity("The pending students for approval are as follows : " + pendingStudentNames, HttpStatus.OK);
		}
		else {
			return new ResponseEntity("All students are approved and no student pending for approval!", HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Method to approve student by ID
	 * @param studentId
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/approveStudent", method = RequestMethod.PUT)
	public ResponseEntity approveStudent(@RequestParam String studentId) {
		List<Student> studentList = adminService.viewPendingAdmissions();
		try {
			adminService.approveStudent(studentId, studentList);
			String name = userService.findUserName(studentId);
			notificationService.sendNotification(NotificationTypeConstant.APPROVED, name);
		} catch (StudentNotFoundForApprovalException | StudentAlreadyApprovedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Student with "+ studentId + " is approved for Registeration!", HttpStatus.OK);
	}
	
	
	/**
	 * Method to approve all students
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/approveAllStudents", method = RequestMethod.PUT)
	public ResponseEntity approveAllStudents() {
		List<Student> studentList = adminService.viewPendingAdmissions();
		if(studentList.size() == 0)
			return new ResponseEntity("No student pending for approval!", HttpStatus.NOT_IMPLEMENTED);
		
		adminService.approveAllStudents();
		for(Student studentObj:studentList) {
			String name = studentObj.getusername();	
			notificationService.sendNotification(NotificationTypeConstant.APPROVED, name);
		}
		return new ResponseEntity("Successfully approved all the approvals for Registeration!", HttpStatus.OK);
	}
	
	/**
	 * Method to add professors
	 * @param professor
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addProfessor", method = RequestMethod.POST)
	public ResponseEntity addProfessor(@RequestBody Professor professor) throws UserIdAlreadyExists{
		try {
			adminService.addProfessor(professor);
		} catch (UserNotAddedException | ProfessorNotAddedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity("Professor added Successfully!", HttpStatus.CREATED);
	}
	
	/**
	 * Method to assign course to professor
	 * @param professorId
	 * @param courseCode
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/assignCourseToProfessor", method = RequestMethod.PUT)
	public ResponseEntity assignCourseToProfessor(
			@RequestParam String professorId, 
			@RequestParam String courseCode)  {
		try {
			
			adminService.assignCourse(courseCode, professorId);
			
		} catch (CourseNotFoundException | UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Professor with ID: "+ professorId +" assigned to course code: " + courseCode +"!", HttpStatus.OK);
	}
	
	/**
	 * Method to generate report card of student
	 * @param studentId
	 * @return report card
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/generateReportCard", method = RequestMethod.GET)
	public ResponseEntity generateReportCard(@RequestParam String studentId){
		try {
			return new ResponseEntity(adminService.generateGradeCard(studentId),HttpStatus.OK);
		} catch (UserNotFoundException | NoCoursesRegisteredException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Method to approve student by ID
	 * @param studentId
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/approveStudentRegisteration", method = RequestMethod.PUT)
	public ResponseEntity approveStudentRegisteration(@RequestParam String studentId) {
		try {
			adminService.approveStudentRegisteration(studentId);
			String name = userService.findUserName(studentId);
			notificationService.sendNotification(NotificationTypeConstant.REGISTERATION, name);
		} catch (StudentAlreadyRegisteredException | UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Student with "+ studentId + " is approved for course registration!", HttpStatus.OK);
	}
}
