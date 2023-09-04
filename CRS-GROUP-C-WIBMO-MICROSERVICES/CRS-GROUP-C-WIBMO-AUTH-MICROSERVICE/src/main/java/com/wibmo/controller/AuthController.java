/**
 * Authentication Micro-service controller for CRS Application
 * @author parth.patel
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.PasswordAlreadyInUseException;
import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Student;
import com.wibmo.service.UserService;

/**
 * 
 */

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	UserService userService;
	
	/**
     * Method to authenticate user
     * @param username : String
     * @param password : String
     * @param role : String
     * @return ResponseEntity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.GET)
    public ResponseEntity authenticateUser(
            @RequestParam String username, 
            @RequestParam String password, 
            @RequestParam String role) {
    	boolean authenticationStatus = false; 
    	if(role.equalsIgnoreCase("student")) {
	    	try {
	    		userService.getApprovalStatus(username);
	    	} catch (StudentNotApprovedException | UserNotFoundException e) {
	    		return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
	    	}
    	}
    	try {
    		authenticationStatus = userService.authenticateUser(username, password, role);
    	}catch(UserNotFoundException e)
    	{
    		return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
    	}
    	
        if(authenticationStatus == true)
            return new ResponseEntity("Authentication Successful!", HttpStatus.FOUND);
        else
            return new ResponseEntity("Authentication failed. Invalid Credentials!", HttpStatus.NOT_FOUND);
 
    }
    
    /**
     * Method for student registration
     * @param student 
     * @return ResponseEntity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/registerStudent", method = RequestMethod.POST)
    public ResponseEntity registerStudent(@RequestBody Student student) {
        try{
        	userService.register(student);
            userService.sendNotification(NotificationTypeConstant.REGISTERATION, student.getusername());
            return new ResponseEntity("Student Details Registered Successfully!", HttpStatus.CREATED);
        }catch(UserIdAlreadyExists | UserNotAddedException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Method to update password
     * @param newPassword
     * @param userName
     * @return ResponseEntity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public ResponseEntity updatePassword(
    		@RequestParam String newPassword, 
            @RequestParam String userName) {
        try {
            userService.updatePassword(userName, newPassword);
            return new ResponseEntity("Password Updated Successfully!", HttpStatus.OK);
        }catch(UserNotFoundException | PasswordAlreadyInUseException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
