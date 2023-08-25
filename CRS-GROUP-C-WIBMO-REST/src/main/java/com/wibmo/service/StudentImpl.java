/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.apache.log4j.Logger;

import com.wibmo.bean.Student;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.dao.StudentDAOInterface;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * Implementation of methods which calls Student DAO methods 
 */
@Service
public class StudentImpl implements StudentInterface{
	
	@Autowired
	private StudentDAOInterface studentDaoInterface;
	
	@Override
	public void register(String name, String userId, String password, GenderConstant gender, int gradYear, String dept,
			String address) throws StudentNotRegisteredException{
		try
		{
			Student newStudent=new Student(userId,name,RoleConstant.STUDENT,password,gender,address,dept,userId,gradYear,false);
			studentDaoInterface.registerStudent(newStudent);
		}
		catch(StudentNotRegisteredException ex)
		{
			throw ex;
		}
	}
	
	@Override
	public boolean isApproved(String studentName) {
		return studentDaoInterface.isApproved(studentName);
	}
}
