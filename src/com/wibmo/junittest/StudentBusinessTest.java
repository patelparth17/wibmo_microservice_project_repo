package com.wibmo.junittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.business.StudentImpl;


/**
 * 
 */
public class StudentBusinessTest {
	StudentImpl studentImpl = null;
	Student student = null;
	
	@Before
	public void setUp() throws Exception {
		studentImpl = StudentImpl.getInstance();
		student = new Student();
	}
	
	
	@Test
	public void testStudentApproval() {
		student.setName("raj");
		
		Assert.assertEquals(true, studentImpl.isApproved(student.getName()));
	}
}