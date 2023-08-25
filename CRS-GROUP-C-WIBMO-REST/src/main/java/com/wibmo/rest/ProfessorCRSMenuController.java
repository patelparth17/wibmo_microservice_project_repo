/**
 * 
 */
package com.wibmo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.exception.GradeNotAllotedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ProfessorImpl;
import com.wibmo.validator.ProfessorValidator;

/**
 * REST Controller for Professor activities
 */
@RestController
@RequestMapping("/professor")
public class ProfessorCRSMenuController {

	@Autowired
	private ProfessorImpl professorImpl;
	
	private static Logger logger = Logger.getLogger(ProfessorCRSMenuController.class);
	
	/**
	 * Method to get the signed-up courses 
	 * @param username : Username 
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/courses/{username}")
	public ResponseEntity getCourses(@PathVariable("username") String username) {
			return new ResponseEntity(professorImpl.viewCourses(username),HttpStatus.OK);
	}

	/**
	 * Method to view the list of enrolled students
	 * @param username : Username 
	 * @return ResponseEntity
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/enrolledstudents")
	public ResponseEntity viewEnrolledStudents(@RequestParam("username") String username) {
	        try {
	            return new ResponseEntity(professorImpl.viewEnrolledStudents(username),HttpStatus.OK);
	        } catch(UserNotFoundException ex) {
	            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
	        }
	    }
	
	/**
	 * Method to add grade to the student's course
	 * @param username : Username 
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/addgrade/{username}")
	public ResponseEntity addGrade(@PathVariable("username") String username) {
		Scanner in = new Scanner(System.in);

		String courseCode, grade, studentId;
		try {
			List<EnrolledStudent> enrolledStudents = new ArrayList<>();
			try {
				enrolledStudents = professorImpl.viewEnrolledStudents(username);
			} catch (UserNotFoundException e) {
				e.getMessage();
			}
			logger.debug("The list of enrolled students:");
			logger.debug(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","Student ID" ));
			for (EnrolledStudent obj: enrolledStudents) {
				logger.debug(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}
			List<Course> coursesEnrolled = new ArrayList<>();
			coursesEnrolled	= professorImpl.viewCourses(username);
			logger.debug("----------------Add Grade--------------");
			logger.debug("Enter student id: ");
			studentId = in.nextLine();
			
			logger.debug("Enter course code: ");
			courseCode = in.nextLine();
			
			logger.debug("Enter grade: ");
			grade = in.nextLine();
			
			if (!(ProfessorValidator.isValidStudent(enrolledStudents, studentId)
					&& ProfessorValidator.isValidCourse(coursesEnrolled, courseCode))) {
				professorImpl.addGrade(studentId, courseCode, grade);
				logger.debug("GradeConstant added successfully for student ID : "+studentId);
				return new ResponseEntity("GradeConstant added successfully for student ID : "+studentId, HttpStatus.OK);
			} else {
				logger.debug("Invalid data entered, try again!");
				return new ResponseEntity("Invalid data entered", HttpStatus.NOT_FOUND);
			}
		} catch(GradeNotAllotedException ex) {
			logger.error("GradeConstant cannot be added for student ID : "+ex.getStudentId());
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
