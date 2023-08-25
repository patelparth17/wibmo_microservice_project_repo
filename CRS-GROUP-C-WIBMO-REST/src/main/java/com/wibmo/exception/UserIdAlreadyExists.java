package com.wibmo.exception;

/**
 * Exception to throw when UserID already exists
 * @author vedasree
 *
 */
public class UserIdAlreadyExists extends Exception{
	private String userId;
	
	
	/***
	 * Setter function for UserId
	 * @param id
	 */
	
	public UserIdAlreadyExists(String id) {
		userId = id;
	}
	
	/***
	 * Getter function for UserId
	 * @return userId
	 */
	
	public String getUserId() {
		return userId;
	}
	
	
	@Override
	public String getMessage() {
		return "userId: " + userId + " is already in use.";
	}

}