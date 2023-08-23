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

@RestController
public class ProfessorCRSMenuController {

	@Autowired
	private static Logger logger = Logger.getLogger(ProfessorCRSMenuController.class);
	
	@Autowired
	private ProfessorImpl professorImpl;
	
	/**
	 * Method to get the signed-up courses 
	 * @param username
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/professor/courses/{username}")
	public ResponseEntity getCourses(@PathVariable("username") String username) {
			return new ResponseEntity(professorImpl.viewCourses(username),HttpStatus.OK);
	}

	/**
	 * Method to view the list of enrolled students
	 * @param username
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })

	    @RequestMapping(value = "/professor/enrolledstudents")

	    public ResponseEntity viewEnrolledStudents(@RequestParam("username") String username) {
	        try {
	            return new ResponseEntity(professorImpl.viewEnrolledStudents(username),HttpStatus.OK);
	        } catch(UserNotFoundException ex) {
	            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
	        }
	    }
	
	/**
	 * Method to add grade to the student's course
	 * @param username
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/professor/addgrade/{username}")
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
			System.out.println("The list of enrolled students:");
			System.out.println(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","Student ID" ));
			for (EnrolledStudent obj: enrolledStudents) {
				System.out.println(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}
			List<Course> coursesEnrolled = new ArrayList<>();
			coursesEnrolled	= professorImpl.viewCourses(username);
			System.out.println("----------------Add Grade--------------");
			System.out.println("Enter student id: ");
			studentId = in.nextLine();
			
			System.out.println("Enter course code: ");
			courseCode = in.nextLine();
			
			System.out.println("Enter grade: ");
			grade = in.nextLine();
			
			if (!(ProfessorValidator.isValidStudent(enrolledStudents, studentId)
					&& ProfessorValidator.isValidCourse(coursesEnrolled, courseCode))) {
				professorImpl.addGrade(studentId, courseCode, grade);
				System.out.println("GradeConstant added successfully for student ID : "+studentId);
				return new ResponseEntity("GradeConstant added successfully for student ID : "+studentId, HttpStatus.OK);
			} else {
				System.out.println("Invalid data entered, try again!");
				return new ResponseEntity("Invalid data entered", HttpStatus.NOT_FOUND);
			}
		} catch(GradeNotAllotedException ex) {
			System.out.println("GradeConstant cannot be added for student ID : "+ex.getStudentId());
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
