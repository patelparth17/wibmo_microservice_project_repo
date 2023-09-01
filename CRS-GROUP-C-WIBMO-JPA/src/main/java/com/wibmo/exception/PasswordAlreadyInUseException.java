package com.wibmo.exception;

/**
 * Exception to check if user cannot be added
 * @author parth.patel
 *
 */
public class PasswordAlreadyInUseException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "New password must not be same as previous password. Please choose a different one!";
	}
}