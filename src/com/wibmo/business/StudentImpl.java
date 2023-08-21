/**
 * 
 */
package com.wibmo.business;

import org.apache.log4j.Logger;

import com.wibmo.bean.Student;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAOInterface;
import com.wibmo.dao.UserDAOImpl;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * 
 */
public class StudentImpl implements StudentInterface{
	private static Logger logger = Logger.getLogger(StudentImpl.class);
	private static volatile StudentImpl instance=null;
	StudentDAOInterface studentDaoInterface=StudentDAOImpl.getInstance();
	private StudentImpl(){}
	
	/**
	 * Method to make StudentOperation Singleton
	 * @return
	 */
	public static StudentImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(StudentImpl.class){
				instance=new StudentImpl();
			}
		}
		return instance;
	}
	
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
