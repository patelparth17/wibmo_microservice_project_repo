/**
 * 
 */
package com.wibmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.model.Course;
import com.wibmo.model.Student;
import com.wibmo.service.AdminService;

/**
 * 
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	/**
	 * Method to view courses in catalog
	 * @return List of courses in catalg
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
	@RequestMapping(value = "/deleteCourse/{courseCode}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCourse(@PathVariable String courseCode) {
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
	@RequestMapping(value = "/viewPendingAdmissions", method = RequestMethod.GET)
	public List<Student> viewPendingAdmissions(){
		List<Student> pendingStudentsList= adminService.viewPendingAdmissions();
		return pendingStudentsList;
	}
	
	/**
	 * Method to approve student by ID
	 * @param studentId
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/approveStudent/{studentId}", method = RequestMethod.PUT)
	public ResponseEntity approveStudent(@PathVariable String studentId) {
		List<Student> studentList = adminService.viewPendingAdmissions();
		System.out.println(studentId);
		try {
			adminService.approveStudent(studentId, studentList);
		} catch (StudentNotFoundForApprovalException | StudentAlreadyApprovedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Student with "+ studentId + " is approved!", HttpStatus.OK);
	}
	
	
	/**
	 * Method to approve all students
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/approveAllStudents", method = RequestMethod.PUT)
	public ResponseEntity approveAllStudents() {
		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0)
			return new ResponseEntity("No student pending for approval!", HttpStatus.NOT_IMPLEMENTED);
		
		adminService.approveAllStudents();
//		for(Student studentObj:studentList) {
//			String name = studentObj.getusername();	
//			notificationObject.sendNotification(NotificationTypeConstant.APPROVED, name, null, 0);
//		}
		return new ResponseEntity("Successfully approved all the pending approvals!", HttpStatus.OK);
	}
}
