package com.wibmo.bean;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

/**
 * @author parth.patel
 *
 */
public class Admin extends User
{
		
//		private String adminID;
	
		public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
		{
			super(userID, name, role, password, gender, address);
		}	
	
}
