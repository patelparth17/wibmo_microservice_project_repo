/**
 * 
 */
package com.wibmo.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.service.AdminOperationInterface;
import com.wibmo.service.NotificationInterface;
import com.wibmo.service.ProfessorInterface;
import com.wibmo.service.RegistrationInterface;


/**
 * 
 */
@RestController
public class StudentCRSMenuController {
		
	@Autowired
	RegistrationInterface registrationInterface;
	
	@Autowired
	AdminOperationInterface adminInterface;
	
	@Autowired
	ProfessorInterface professorInterface;
	
	@Autowired
	NotificationInterface notificationInterface;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/viewRegisteredCourse/{studentName}")
	private ResponseEntity viewRegisteredCourse(@PathVariable("studentName") String studentName) {
		try {
			return new ResponseEntity(registrationInterface.viewRegisteredCourses(studentName), HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/viewCourse/{studentName}")
	private ResponseEntity viewCourse(@PathVariable("studentName") String studentName) {
		try {
			return new ResponseEntity(registrationInterface.viewCourses(studentName), HttpStatus.OK);

		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/viewGradeCard/{studentName}")
	private ResponseEntity viewGradeCard(@PathVariable("studentName") String studentName){
		try {
			return new ResponseEntity(registrationInterface.viewGradeCard(studentName), HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/registerCourses")
	private ResponseEntity registerCourses( @PathVariable("studentName")
			@RequestParam String studentName, 
			@RequestParam String courseCode
			) {
		List<Course> courseList;
		try {
			courseList = registrationInterface.viewCourses(studentName);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		try {
			registrationInterface.addCourse(courseCode,studentName,courseList);
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException | SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course is registered for "+ studentName, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/addCourse")
	private ResponseEntity addCourse( @PathVariable("studentName")
			@RequestParam String studentName,
			@RequestParam String courseCode
			) {
		List<Course> courseList;
		try {
			courseList = registrationInterface.viewCourses(studentName);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		try {
			registrationInterface.addCourse(courseCode,studentName,courseList);
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException | SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course is registered for "+ studentName, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/student/dropCourse")
	private ResponseEntity dropCourse( @PathVariable("studentName")
			@RequestParam String studentName,
			@RequestParam String courseCode
			) {
		List<Course> registeredCourseList;
		try {
			registeredCourseList = registrationInterface.viewRegisteredCourses(studentName);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		try {
			registrationInterface.dropCourse(courseCode, studentName, registeredCourseList);
		} catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Course deleted for "+ studentName, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/student/makePayment")
	private ResponseEntity makePayment( @PathVariable("studentName")
			@RequestParam String notificationType,
			@RequestParam String studentName,
			@RequestParam String mode,
			@RequestParam double fee
			) {
		NotificationTypeConstant notificationType1 = NotificationTypeConstant.stringToNotificationType(notificationType);
		PaymentModeConstant paymentMode = PaymentModeConstant.stringToPaymentMode(mode);
		notificationInterface.sendNotification(notificationType1, studentName, paymentMode, fee);
		return new ResponseEntity("Course deleted for "+ studentName, HttpStatus.OK);
	}
}
