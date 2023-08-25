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
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyApprovedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.AdminOperationInterface;
import com.wibmo.service.NotificationInterface;



/**
 * REST Controller for Admin activities
 */

@RestController
@RequestMapping("/admin")
public class AdminCRSMenuController {
	
	
	@Autowired
	AdminOperationInterface adminOperation;
	
	@Autowired
	NotificationInterface notificationObject;
	
	/**
	 * Method to view courses in catalog
	 * @return List of courses in catalg
	 */
	@RequestMapping(value = "/viewCourseList", method = RequestMethod.GET)
	public List<Course> viewCoursesInCatalogue(){
		return adminOperation.viewCourses();
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
		
		List<Course> courseList = adminOperation.viewCourses();
		Course course = new Course(courseCode,courseName,"0",10,courseFee);
		try {
			adminOperation.addCourse(course, courseList);
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
	
	/**
	 * Method to view pending approvals 
	 * @return List of students having pending approvals
	 */
	@RequestMapping(value = "/viewPendingAdmissions", method = RequestMethod.GET)
	public List<Student> viewPendingAdmissions(){
		List<Student> pendingStudentsList= adminOperation.viewPendingAdmissions();
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
		List<Student> studentList = adminOperation.viewPendingAdmissions();
		System.out.println(studentId);
		try {
			adminOperation.approveStudent(studentId, studentList);
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
		
		adminOperation.approveAllStudents(studentList);
		for(Student studentObj:studentList) {
			String name = studentObj.getName();	
			notificationObject.sendNotification(NotificationTypeConstant.APPROVED, name, null, 0);
		}
		return new ResponseEntity("Successfully approved all the pending approvals!", HttpStatus.OK);
	}
	
	/**
	 * Method to add professors
	 * @param professor
	 * @return status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addProfessor", method = RequestMethod.POST)
	public ResponseEntity addProfessor(@RequestBody Professor professor){
		try {
			adminOperation.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyExists e) {
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
	@RequestMapping(value = "/assignCourseToProfessor/{professorId}", method = RequestMethod.PUT)
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
	
	
	/**
	 * Method to generate report card of student
	 * @param studentId
	 * @return report card
	 */
	@RequestMapping(value = "/generateReportCard/{studentId}", method = RequestMethod.GET)
	public List<RegisteredCourse> generateReportCard(@PathVariable String studentId){
		return adminOperation.generateGradeCard(studentId);
	}
}
