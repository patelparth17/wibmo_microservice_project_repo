/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception to throw when user is not found
 */
public class UserNotFoundException extends Exception{
	private String userId;

	/***
	 * Setter function for UserId
	 * @param id
	 */
	public UserNotFoundException(String id) {
		userId = id;
	}

	/**
	 * Message thrown by exception
	 */
	
	public String getMessage() {
		return "User with userId: " + userId + " not found.";
	}

}
