/**
 * 
 */
package com.wibmo.junittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAOImpl;


/**
 * 
 */
public class StudentDAOTest {
	StudentDAOImpl studentDAOImpl = null;
	Student student = null;
	
	@Before
	public void setUp() throws Exception {
		studentDAOImpl = StudentDAOImpl.getInstance();
		student = new Student();
	}
	
	
	@Test
	public void testStudentApproval() {
		student.setName("raj");
		
		Assert.assertEquals(true, studentDAOImpl.isApproved(student.getName()));
	}
}
