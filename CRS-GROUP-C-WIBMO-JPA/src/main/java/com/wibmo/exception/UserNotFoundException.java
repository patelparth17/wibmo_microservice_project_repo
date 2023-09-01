/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class UserNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;

	/***
	 * Setter function for UserId
	 * @param userId
	 */
	public UserNotFoundException(String username) {
		this.username = username;
	}

	/**
	 * Message thrown by exception
	 */
	
	public String getMessage() {
		return "User with username: " + username + " is not found. Please get approved by Admin first if you are a Student or ask the Admin to add if you're a Professor!";
	}

}
