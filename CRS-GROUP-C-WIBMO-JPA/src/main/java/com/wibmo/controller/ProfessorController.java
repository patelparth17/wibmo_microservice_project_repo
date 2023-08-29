/**
 * 
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/courses/{username}", method = RequestMethod.GET)
	public ResponseEntity getCourses(@PathVariable("username") String username) {
		return new ResponseEntity(professorService.viewCourses(username),HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/enrolledstudents")
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
	@RequestMapping("/addgrade/{username}/{studentID}/{courseCode}/{grade}")
	public ResponseEntity addGrade(@PathVariable("username") String username,
			@PathVariable("studentID") String studentID,
			@PathVariable("courseCode") String courseCode,
			@PathVariable("grade") String grade
			) {
		if(professorService.addGrade(username,studentID,courseCode,grade))
			return new ResponseEntity("Successfully updated!",HttpStatus.OK);
		
		return new ResponseEntity("You didn't signup for the course: "+ courseCode+".",HttpStatus.NOT_FOUND);
	}
}

