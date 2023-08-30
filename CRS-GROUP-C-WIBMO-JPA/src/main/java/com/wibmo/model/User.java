/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="userID")
	private String userID;
	
	@Column(name="username")
	private String username;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="role")
	protected String role;
	
	@Column(name="password")
	private String password;
	
	@Column(name="address")
	private String address;
	
	
	/**
	 * Constructor of User
	 * @param userID
	 */
	public User(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Constructor of User
	 * @param userID
	 * @param username
	 * @param role
	 * @param password
	 * @param gender
	 * @param address
	 */
	public User(String userID, String username,String role,String password ,String gender, String address) {
		this.userID = userID;
		this.username = username;
		this.gender = gender;
		this.role = role;
		this.password = password;
		this.address = address;
	}

	public User() {
		
	}
	
	/**
	 * Method to get the userusername
	 * @return username
	 */
	public String getusername() {
		return username;
	}
	
	/**
	 * Method to set the userusername
	 * @param username
	 */
	public void setusername(String username) {
		this.username = username;
	}
	
	/**
	 * Method to get the gender of user
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Method to set the gender of user
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * Method to get the role of user
	 * @return role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Method to set the role of user
	 * @param role
	 */
	public void setRole(String role) {
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
	 * @return userID
	 */
	public String getuserID() {
		return userID;
	}
	
	/**
	 * Method to set the userID
	 * @param userID
	 */
	public void setuserID(String userID)
	{
		this.userID = userID;
	}	
}
