/**
 * 
 */
package com.wibmo.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.AdminOperationInterface;
import com.wibmo.service.NotificationInterface;



/**
 * 
 */

@RestController
public class AdminCRSMenuController {
	
	
	@Autowired
	AdminOperationInterface adminOperation;
	
	@Autowired
	NotificationInterface notificationObject;
	
	@RequestMapping(value = "/admin/viewCourseList", method = RequestMethod.GET)
	public List<Course> viewCoursesInCatalogue(){
		return adminOperation.viewCourses();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/addCourseToCatalogue", method = RequestMethod.POST)
	public ResponseEntity addCourseToCatalogue(@RequestBody Course course) {
		
		List<Course> courseList = adminOperation.viewCourses();
		try {
			adminOperation.addCourse(course, courseList);
		} catch (CourseAlreadyExistsException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course Added to Catalog Successfully!", HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/deleteCourse/{courseCode}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCourse(@PathVariable String courseCode) {
		List<Course> courseList = adminOperation.viewCourses();
		try {
			adminOperation.removeCourse(courseCode, courseList);
		} catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (CourseNotDeletedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity("Course with "+ courseCode +" is deleted.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/viewPendingAdmissions", method = RequestMethod.GET)
	public List<Student> viewPendingAdmissions(){
		List<Student> pendingStudentsList= adminOperation.viewPendingAdmissions();
		
		return pendingStudentsList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/approveStudent/{studentId}", method = RequestMethod.PUT)
	public ResponseEntity approveStudent(@PathVariable String studentId) {
		List<Student> studentList = adminOperation.viewPendingAdmissions();
		try {
			adminOperation.approveStudent(studentId, studentList);
		} catch (StudentNotFoundForApprovalException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Student with "+ studentId + "is approved!", HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/addProfessor", method = RequestMethod.POST)
	public ResponseEntity addProfessor(@RequestBody Professor professor){
		try {
			adminOperation.addProfessor(professor);
		} catch (ProfessorNotAddedException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserIdAlreadyExists e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Professor added Successfully!", HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/assignCourseToProfessor/{professorId}", method = RequestMethod.PUT)
	public ResponseEntity assignCourseToProfessor(
			@PathVariable String professorId, 
			@RequestParam String courseCode)  {
		try {
			
			adminOperation.assignCourse(courseCode, professorId);
			
		} catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity("Professor assigned to " + courseCode +"!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/generateReportCard/{studentId}", method = RequestMethod.GET)
	public List<RegisteredCourse> generateReportCard(@PathVariable String studentId){
		return adminOperation.generateGradeCard(studentId);
	}
}
