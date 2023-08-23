package com.wibmo.junittest;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.dao.RegistrationDAOImpl;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

public class RegistrationDAOTest {

	private static Logger logger = Logger.getLogger(RegistrationBusinessTest.class);
	RegistrationDAOImpl registrationDAOImpl = null;
	Course course = null;
	Student student = null;
	
	@Before
	public void setUp() throws Exception {
		registrationDAOImpl = RegistrationDAOImpl.getInstance();
		course = new Course();
		student = new Student();
	}
	
	@Test
	public void addCourseTest() {
		List<Course> courses = new ArrayList<>();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		student.setName("raj");
		courses.add(course);
		course.setCourseCode("225");
		course.setCourseName("Computer");
		courses.add(course);
		try {
			assertEquals(false,registrationDAOImpl.addCourse(course.getCourseCode(), student.getName()));
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@Test
	public void CalculateFeeTest() {
		student.setName("raj");
		try {
			assertEquals(680.0, registrationDAOImpl.calculateFee(student.getName()),0);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void isReportGeneratedTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationDAOImpl.isReportGenerated(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void getRegistrationStatusTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationDAOImpl.getRegistrationStatus(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test 
	public void getPaymentStatusTest() {
		student.setName("raj");
		try {
			assertEquals(true, registrationDAOImpl.getPaymentStatus(student.getName()));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
