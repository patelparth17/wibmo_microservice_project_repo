/**
* 
*/
package com.wibmo.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

 

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.NotificationImpl;
import com.wibmo.service.StudentImpl;
import com.wibmo.service.UserImpl;

 

/**
* 
*/
@RestController
public class CRSApplicationController {

 

    @Autowired
    private StudentImpl studentImpl;

    @Autowired
    private UserImpl userImpl;

    @Autowired
    private  NotificationImpl notificationImpl;

    /**
     * Method to authenticate user
     * @param username
     * @param password
     * @param role
     * @return status
     * @throws UserNotFoundException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/authenticateUser", method = RequestMethod.GET)
    public ResponseEntity authenticateUser(
            @RequestParam String username, 
            @RequestParam String password, 
            @RequestParam String role) 
            throws UserNotFoundException {

        boolean authenticationStatus = userImpl.authenticateUser(username, password, role);
        if(authenticationStatus == true)
            return new ResponseEntity("Authentication Successful!", HttpStatus.FOUND);
        else
            return new ResponseEntity("Authentication failed. User deatils not found.", HttpStatus.NOT_FOUND);
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
    public ResponseEntity registerStudent(
            @RequestParam String name, 
            @RequestParam String userId,
            @RequestParam String password, 
            @RequestParam String gender,
            @RequestParam int gradYear, 
            @RequestParam String dept, 
            @RequestParam String address) throws StudentNotRegisteredException {

        GenderConstant selectedGender = GenderConstant.stringToGender(gender);

        try{
            studentImpl.register(name, userId, password, selectedGender, gradYear, dept, address);
            notificationImpl.sendNotification(NotificationTypeConstant.REGISTERATION, name, null,0);

            return new ResponseEntity("Student Details Registered Successfully!", HttpStatus.CREATED);
        }catch(StudentNotRegisteredException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            userImpl.updatePassword(userName, newPassword);
            return new ResponseEntity("Password Updated Successfully!", HttpStatus.OK);
        }catch(UserNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}