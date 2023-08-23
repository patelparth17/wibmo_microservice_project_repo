/**
 * 
 */
package com.wibmo.junittest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.business.RegistrationImpl;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

/**
 * 
 */
public class RegistrationBusinessTest {
	private static Logger logger = Logger.getLogger(RegistrationBusinessTest.class);
	RegistrationImpl registrationImpl = null;
	Course course = null;
	Student student = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		registrationImpl = RegistrationImpl.getInstance();
		course = new Course();
		student = new Student();
	}

	@Test
	public void testAddCourse() {
		List<Course> courses = new ArrayList<>();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		courses.add(course);
		student.setName("satwika");
		try {
			assertTrue(registrationImpl.addCourse(course.getCourseCode(), student.getName(), courses));
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException | SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testDropCourse() throws CourseNotFoundException, SQLException {
		List<Course> courses = new ArrayList<>();
		
		course.setCourseCode("230");
		course.setCourseName("Hindi");
		courses.add(course);
		student.setName("raj");
		assertTrue(registrationImpl.dropCourse(course.getCourseCode(), student.getName(), courses));
	}	
	
	@Test
	public void CalculateFeeTest() {
		student.setName("raj");
		try {
			assertEquals(620.0, registrationImpl.calculateFee(student.getName()),0);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void isReportGeneratedTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationImpl.isReportGenerated(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void getRegistrationStatusTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationImpl.getRegistrationStatus(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test 
	public void getPaymentStatusTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationImpl.getPaymentStatus(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

}
