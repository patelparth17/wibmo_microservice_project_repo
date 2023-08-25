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
import org.springframework.web.bind.annotation.RestController;

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
}
