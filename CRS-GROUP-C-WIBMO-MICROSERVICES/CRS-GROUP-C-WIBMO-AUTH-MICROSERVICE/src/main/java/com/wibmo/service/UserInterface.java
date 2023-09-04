/**
 * 
 */
package com.wibmo.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.exception.PasswordAlreadyInUseException;
import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Student;
import com.wibmo.model.User;

/**
 * 
 */
@Service
public interface UserInterface {

	/**
	 * Method to find username by userID
	 * @param studentId
	 * @return username
	 */
	public String findUserName(String studentId);
	
	/**
	 * Method to add user in user table
	 * @param user
	 * @throws UserNotAddedException
	 */
	public void addUser(User user) throws UserNotAddedException;

	/**
	 * Method to authenticate the user based on username, password and role
	 * @param username
	 * @param password
	 * @param role
	 * @return status
	 * @throws UserNotFoundException
	 */
	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException;

	/**
	 * Method to update password of a user with new password provided
	 * @param userName
	 * @param newPassword
	 * @throws UserNotFoundException
	 * @throws PasswordAlreadyInUseException
	 */
	public void updatePassword(String userName, String newPassword) throws UserNotFoundException, PasswordAlreadyInUseException;

	/**
	 * Method to find the User object details of a user from username
	 * @param username
	 * @return User
	 */
	public Optional <User> findByUsername(String username);
	
	public int getApprovalStatus(String studentName) throws StudentNotApprovedException, UserNotFoundException;
	
	/**
	 * Method to add student
	 * @param student
	 * @throws UserIdAlreadyExists
	 * @throws UserNotAddedException
	 */
	public void register(Student student) throws UserIdAlreadyExists, UserNotAddedException;
	
	/**
	 * Method to send notification
	 * @param type
	 * @param name
	 */
	public void sendNotification(NotificationTypeConstant type, String name);
}
