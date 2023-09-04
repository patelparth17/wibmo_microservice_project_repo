/**
 * 
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ProfessorService;

/**
 * 
 */
@RestController
@RequestMapping("/professor")
public class ProfessorController {

	@Autowired
	ProfessorService professorService;
	
	/**
	 * Method to get the signed-up courses 
	 * @param username
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewCourses", method = RequestMethod.GET)
	public ResponseEntity getCourses(@RequestParam("username") String username) {
		return new ResponseEntity(professorService.viewCourses(username),HttpStatus.OK);
	}
	
	/**
	 * Method to view enrolled students
	 * @param username
	 * @return ResponseEntity
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/enrolledStudents", method = RequestMethod.GET)
	public ResponseEntity viewEnrolledStudents(@RequestParam("username") String username) {
	        try {
	            return new ResponseEntity(professorService.viewEnrolledStudents(username),HttpStatus.OK);
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
	@RequestMapping(value="/addGrade", method = RequestMethod.PUT)
	public ResponseEntity addGrade(@RequestParam("username") String username,
			@RequestParam("studentID") String studentID,
			@RequestParam("courseCode") String courseCode,
			@RequestParam("grade") String grade
			) {
		try {
			if(professorService.addGrade(username,studentID,courseCode,grade)) {
				return new ResponseEntity("Successfully updated!",HttpStatus.OK);
			} else {
				return new ResponseEntity("You didn't signup for the course: "+ courseCode+".",HttpStatus.NOT_FOUND);
			}		
		} catch(UserNotFoundException | StudentNotRegisteredException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}

