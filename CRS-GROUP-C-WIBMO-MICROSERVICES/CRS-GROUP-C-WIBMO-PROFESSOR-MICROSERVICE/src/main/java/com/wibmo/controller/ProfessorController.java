/**
 * Professor Microservice controller for CRS Application
 * @author vedasree
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.wibmo.exception.CourseNotAvailableException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ProfessorService;

@RequestMapping("/api/professor")
@CrossOrigin
@PreAuthorize("hasAuthority('Role.PROFESSOR')")
@RestController
public class ProfessorController {

	@Autowired
	ProfessorService professorService;
	
	/**
	 * Method to get the courses taught by the professor with given username
	 * @param username
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/viewCourses", method = RequestMethod.GET)
	public ResponseEntity getCourses(@RequestParam("username") String username) {
		return new ResponseEntity(professorService.viewCourses(username),HttpStatus.OK);
	}
	
	/**
	 * Method to view enrolled students under the professor
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
	 * Method to add grade in the course taught by the professor for the studentId provided
	 * @param username
	 * @param studentID
	 * @param courseCode
	 * @param grade
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
				return new ResponseEntity("Student: "+ studentID + " didn't register for the course: "+ courseCode+".",HttpStatus.NOT_FOUND);
			}		
		} catch(UserNotFoundException | StudentNotRegisteredException | CourseNotAvailableException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}

