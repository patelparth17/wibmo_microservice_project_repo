/**
 * 
 */
package com.wibmo.business;

import com.wibmo.constants.GenderConstant;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAOInterface;

/**
 * 
 */
public class StudentImpl implements StudentInterface{
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
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(StudentImpl.class){
				instance=new StudentImpl();
			}
		}
		return instance;
	}
	/**
	 * 
	 * @param name
	 * @param userId
	 * @param password
	 * @param gender
	 * @param batch
	 * @param branch
	 * @param address
	 * @return registration status: String
	 */
	@Override
	public String register(String name, String userId, String password, GenderConstant gender, int batch, String branch,
			String address) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param userId
	 * @return studentId: String
	 */
	@Override
	public String getStudentId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param studentId
	 * @return student approval status: boolean
	 */
	@Override
	public boolean isApproved(String studentName) {
		return studentDaoInterface.isApproved(studentName);
	}

}
