package com.wibmo.bean;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

/**
 * @author parth.patel
 *
 */
public class Admin extends User
{
	/**
	 * Parametrized Constructor 
	 * @param userID
	 * @param name
	 * @param gender
	 * @param role
	 * @param password
	 * @param address
	 */
	public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
	{
		super(userID, name, role, password, gender, address);
	}	
}