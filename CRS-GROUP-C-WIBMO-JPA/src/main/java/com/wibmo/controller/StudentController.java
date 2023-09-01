
package com.wibmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.model.Course;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.*;
import com.wibmo.service.AdminService;
import com.wibmo.service.NotificationService;
import com.wibmo.service.PaymentService;
import com.wibmo.service.ProfessorService;
import com.wibmo.service.RegistrationService;
import com.wibmo.service.StudentService;

/**
 * JPA Controller for Student activities
 */
@RestController
@RequestMapping("/student")
public class StudentController {


	@Autowired
	RegistrationService registrationService;

	@Autowired
	AdminService adminService;

	@Autowired
	ProfessorService professorService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	StudentService studentService;
	
	@Autowired
	PaymentService paymentService;
	/**
	 * Method to view registered courses of student
	 * 
	 * @param studentName
	 * @return List of registered courses
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewRegisteredCourse", method = RequestMethod.GET)
	private ResponseEntity viewRegisteredCourse(@RequestParam("studentName") String studentName) {
		try {
			return new ResponseEntity(registrationService.viewRegisteredCourses(studentName), HttpStatus.OK);
		} catch (StudentNotRegisteredException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method to view available courses
	 * @param studentName
	 * @return List of available courses
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewAvailableCourses", method = RequestMethod.GET)
	private ResponseEntity viewAvailableCourse(@RequestParam("studentName") String studentName) {
		return new ResponseEntity(registrationService.viewAvailableCourses(studentName), HttpStatus.OK);
	}

	/**
	 * Method to view GradeCard of the student
	 * @param studentName
	 * @return List of registered courses with grades
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/viewGradeCard", method = RequestMethod.GET)
	private ResponseEntity viewGradeCard(@RequestParam("studentName") String studentName) {
		try {
			return new ResponseEntity(registrationService.viewGradeCard(studentName), HttpStatus.OK);
		} catch (ReportCardNotGeneratedException | StudentNotRegisteredException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method to check if student is already registered
	 * @param studentName
	 * @return ResponseEntity
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getRegistrationStatus", method = RequestMethod.GET)
	private ResponseEntity getRegistrationStatus(@RequestParam("studentName") String studentName) {
		try {
			if(studentService.getRegistrationStatus(studentName)==1)
				return new ResponseEntity("Student "+ studentName+" is registered!",HttpStatus.OK);
			else
				return new ResponseEntity("Student "+ studentName+" is not registered!",HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method for course registration
	 * @param studentName
	 * @param courseList
	 * @return course registration status
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/registerCourses", method = RequestMethod.POST)
	private ResponseEntity registerCourses(
			@RequestParam String studentName,
			@RequestBody List<String> courseList
	) {
		try {
			registrationService.registerCourse(studentName, courseList);
		} catch ( UserNotFoundException | CourseSizeViolation | CourseLimitExceededForPrimaryException | CourseLimitExceededForSecondaryException
				| StudentAlreadyRegisteredException  e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("Course is registered for " + studentName, HttpStatus.CREATED);
	}

	/**
	 * Method to add courses
	 * @param studentName
	 * @param courseCode
	 * @return status message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	private ResponseEntity addCourse(@RequestParam String studentName, @RequestParam String courseCode) {
		List<Course> courseList = registrationService.viewAvailableCourses(studentName);
		try {
			registrationService.addCourse(courseCode, studentName, courseList);
		} catch (CourseAlreadyRegisteredException se) {
			return new ResponseEntity("Course already added for student with username " + studentName, HttpStatus.NOT_FOUND);
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException | StudentAlreadyRegisteredException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity("Course registered for " + studentName + " successfully!", HttpStatus.CREATED);
	}

	/**
	 * Method to drop course
	 * @param studentName
	 * @param courseCode
	 * @return status message
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dropCourse", method = RequestMethod.DELETE)
	private ResponseEntity dropCourse(@RequestParam String studentName, @RequestParam String courseCode) {
		List<Course> registeredCourseList = registrationService.viewPrimaryCourses(studentName);
		try {
			registrationService.dropCourse(courseCode, studentName, registeredCourseList);
		} catch (CourseNotFoundException | StudentAlreadyRegisteredException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity("Course deleted for " + studentName, HttpStatus.OK);
	}

	/**
	 * Method to make payment
	 * @param studentName
	 * @param mode
	 * @return ResponseEntity 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/makePayment", method = RequestMethod.POST)
	private ResponseEntity makePayment(@RequestParam String studentName, @RequestParam String mode) {
		int isreg, ispaid = 0;
		double fee = 0.0;
		try {
			isreg = studentService.getRegistrationStatus(studentName);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		try {
			ispaid = studentService.getPaymentStatus(studentName);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		fee = registrationService.calculateFee(studentName);

		if (isreg==0) {
			return new ResponseEntity("You have not registered yet", HttpStatus.NOT_IMPLEMENTED);
		} else if (isreg==1 && ispaid==0) {
				PaymentModeConstant paymentMode = PaymentModeConstant.stringToPaymentMode(mode);
				studentService.setPaymentStatus(studentName);
				paymentService.addPayment(studentName, paymentMode, "PAID", fee);
				notificationService.sendNotification(NotificationTypeConstant.PAID, studentName);
				return new ResponseEntity("Payment Successful by studentName: " + studentName, HttpStatus.OK);
		}
		return new ResponseEntity("You have already paid the fees", HttpStatus.NOT_IMPLEMENTED);
	}
}
