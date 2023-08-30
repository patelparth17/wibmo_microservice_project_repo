/**
 * 
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Student;
import com.wibmo.service.NotificationService;
import com.wibmo.service.StudentService;
import com.wibmo.service.UserService;

/**
 * 
 */

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired 
	StudentService studentService;
	
	@Autowired
	NotificationService notificationService;
	
	/**
     * Method to authenticate user
     * @param username : String
     * @param password : String
     * @param role : String
     * @return ResponseEntity
     * @throws UserNotFoundException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.GET)
    public ResponseEntity authenticateUser(
            @RequestParam String username, 
            @RequestParam String password, 
            @RequestParam String role) {
    	try {
        boolean authenticationStatus = userService.authenticateUser(username, password, role);
        if(authenticationStatus == true)
            return new ResponseEntity("Authentication Successful!", HttpStatus.FOUND);
        else
            return new ResponseEntity("Authentication failed. User deatils not found.", HttpStatus.NOT_FOUND);
    	}catch(UserNotFoundException e)
    	{
    		return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Method for student registration
     * @param name
     * @param userId
     * @param password
     * @param gender
     * @param gradYear
     * @param dept
     * @param address
     * @return status
     * @throws StudentNotRegisteredException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/registerStudent")
    public ResponseEntity registerStudent(@RequestBody Student student) {
        try{
            studentService.register(student);
            notificationService.sendNotification(NotificationTypeConstant.REGISTERATION, student.getusername());

            return new ResponseEntity("Student Details Registered Successfully!", HttpStatus.CREATED);
        }catch(UserIdAlreadyExists | UserNotAddedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Method to update password
     * @param newPassword
     * @param userName
     * @return status
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatePassword/{newPassword}")
    public ResponseEntity updatePassword(
            @PathVariable String newPassword, 
            @RequestParam String userName) {
        try {
            userService.updatePassword(userName, newPassword);
            return new ResponseEntity("Password Updated Successfully!", HttpStatus.OK);
        }catch(UserNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
