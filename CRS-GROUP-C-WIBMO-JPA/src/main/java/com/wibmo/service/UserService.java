/**
 * 
 */
package com.wibmo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.wibmo.exception.PasswordAlreadyInUseException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.User;
import com.wibmo.repository.UserRepository;

/**
 * 
 */

@Service
public class UserService implements UserInterface {

	@Autowired
	UserRepository userRepo;
	
	public String findUserName(String studentId) {
		return userRepo.findByUserID(studentId).getusername();
	}

	@Modifying
	public void addUser(User user) throws UserNotAddedException {
		if(userRepo.findByUsername(user.getusername()).isPresent()) {
			throw new UserNotAddedException(user.getusername());
		}
		userRepo.save(user);	
			
	}

	public boolean authenticateUser(String username, String password, String role) throws UserNotFoundException {
		if(userRepo.findByUsername(username).isEmpty()) {
			throw new UserNotFoundException(username);
		}
		User user = userRepo.findByUsername(username).get();
		if(user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {
			return true;
		}
		
		return false;
	}

	public void updatePassword(String userName, String newPassword) throws UserNotFoundException, PasswordAlreadyInUseException {
		if(userRepo.findByUsername(userName).isEmpty()) {
			throw new UserNotFoundException(userName);
		}
		String oldPassword = userRepo.findByUsername(userName).get().getPassword();
		if(oldPassword.equals(newPassword)) {
			throw new PasswordAlreadyInUseException();
		}
		userRepo.updatePassword(userName,newPassword);
	}

	public Optional <User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
}
