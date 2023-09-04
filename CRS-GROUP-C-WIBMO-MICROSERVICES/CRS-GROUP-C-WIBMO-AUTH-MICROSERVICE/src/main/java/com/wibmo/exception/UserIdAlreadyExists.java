package com.wibmo.exception;

/**
 * @author vedasree
 *
 */
public class UserIdAlreadyExists extends Exception{
	private String userId;
	
	
	/***
	 * Setter function for UserId
	 * @param userId
	 */
	
	public UserIdAlreadyExists(String id) {
		userId = id;
	}
	
	/***
	 * Getter function for UserId
	 * @param userId
	 */
	
	public String getUserId() {
		return userId;
	}
	
	
	@Override
	public String getMessage() {
		return "userId: " + userId + " is already in use.";
	}

}