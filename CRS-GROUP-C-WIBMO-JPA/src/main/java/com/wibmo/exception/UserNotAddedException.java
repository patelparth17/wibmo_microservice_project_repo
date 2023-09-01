package com.wibmo.exception;

/**
 * Exception to check if user cannot be added
 * @author vedasree
 *
 */
public class UserNotAddedException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	public UserNotAddedException(String username) {
		this.username = username;
	}
	
	/**
	 * Getter function for username
	 * @return
	 */
	public String getUserId() {
		return username;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "UserName: " + username + " already exists!";
	}
}