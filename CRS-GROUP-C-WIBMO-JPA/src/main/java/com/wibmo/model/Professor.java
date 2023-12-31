/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="professor")
public class Professor extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="professorID")
	private String professorID;
	
	@Column(name="dept")
	private String department;
	
	@Column(name="designation")
	private String designation;
	
	/**
	 * Constructor
	 * @param userID
	 */
	public Professor(String userID) {
		super(userID);
	}
	
	/**
	 * Parametrized Constructor
	 * @param userID
	 * @param name
	 * @param gender
	 * @param role
	 * @param password
	 * @param address
	 */
	public Professor(String userID, String name, String gender, String role, String password, String address) {
		super(userID, name, role, password, gender, address);
	}
	
	/**
	 * Default constructor
	 */
	public Professor() {
	}
	
	/**
	 * Method to get the professor ID
	 * @return professorID
	 */
	public String getProfessorID() {
		return professorID;
	}
	
	/**
	 * Method to set the professor ID
	 * @param professorID
	 */
	public void setProfessorID(String professorID) {
		this.professorID = professorID;
	}
	
	/**
	 * Method to get the department of professor
	 * @return department
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * Method to set the department of professor
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * Method to get the designation of professor
	 * @return designation
	 */
	public String getDesignation() {
		return designation;
	}
	
	/**
	 * Method to set the designation of professor
	 * @param designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
}
