package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author parth.patel
 *
 */

@Entity
@Table(name="admin")
public class Admin extends User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Parametrized Constructor 
	 * @param userID
	 * @param name
	 * @param gender
	 * @param role
	 * @param password
	 * @param address
	 */
	public Admin(String userID, String name, String gender, String role, String password, String address) 
	{
		super(userID, name, role, password, gender, address);
	}
	
	public Admin() {
		
	}
}