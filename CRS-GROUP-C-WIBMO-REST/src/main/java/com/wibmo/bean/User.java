/**
 * 
 */
package com.wibmo.bean;

import java.io.Serializable;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

//import jakarta.persistence.Entity;

/**
 * @author parth.patel
 *
 */
//@Entity
public abstract class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	private GenderConstant gender;
	protected RoleConstant role;
	private String password;
	private String address;
	
	/**
	 * Constructor of User
	 * @param userId
	 */
	public User(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Constructor of User
	 * @param userId
	 * @param name
	 * @param role
	 * @param password
	 * @param gender
	 * @param address
	 */
	public User(String userId, String name,RoleConstant role,String password ,GenderConstant gender, String address) {
		this.userId = userId;
		this.name = name;
		this.gender = gender;
		this.role = role;
		this.password = password;
		this.address = address;
	}

	public User() {
		
	}
	
	/**
	 * Method to get the username
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to set the username
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to get the gender of user
	 * @return gender
	 */
	public GenderConstant getGender() {
		return gender;
	}
	
	/**
	 * Method to set the gender of user
	 * @param gender
	 */
	public void setGender(GenderConstant gender) {
		this.gender = gender;
	}
	
	/**
	 * Method to get the role of user
	 * @return role
	 */
	public RoleConstant getRole() {
		return role;
	}
	
	/**
	 * Method to set the role of user
	 * @param role
	 */
	public void setRole(RoleConstant role) {
		this.role = role;
	}
	
	/**
	 * Method to get the user's password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Method to set the user's password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Method to get the user's address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Method to set the user's password
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Method to get the userID
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Method to set the userID
	 * @param userId
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}	
}
