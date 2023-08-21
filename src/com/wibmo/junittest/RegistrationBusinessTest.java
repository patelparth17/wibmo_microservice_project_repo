/**
 * 
 */
package com.wibmo.junittest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
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
	public void testViewRegisteredCourses() throws CourseNotFoundException, SQLException {
		List<Course> expectedRegisteredCourseList = new ArrayList<>();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setInstructorId("666");
		course.setSeats(9);
		course.setFee(50);
		expectedRegisteredCourseList.add(course);
		
		course.setCourseCode("225");
		course.setCourseName("Computer");
		course.setInstructorId("666");
		course.setSeats(9);
		course.setFee(100);
		expectedRegisteredCourseList.add(course);
		
		course.setCourseCode("226");
		course.setCourseName("Sci");
		course.setInstructorId("777");
		course.setSeats(8);
		course.setFee(150);
		expectedRegisteredCourseList.add(course);
		
		course.setCourseCode("227");
		course.setCourseName("AI");
		course.setInstructorId("777");
		course.setSeats(8);
		course.setFee(170);
		expectedRegisteredCourseList.add(course);
		
		course.setCourseCode("228");
		course.setCourseName("ML");
		course.setInstructorId("123");
		course.setSeats(8);
		course.setFee(150);
		expectedRegisteredCourseList.add(course);
		
		assertEquals(expectedRegisteredCourseList, registrationImpl.viewRegisteredCourses("raj"));
	}	
	
	@Test
	public void testViewGradeCard() {
		List<Grade> expectedGradeCard = new ArrayList<Grade>();
		String courseCode = "224";
		String courseName = "4";
		String grade = "A";
		Grade gradeObject = new Grade(courseCode, courseName,grade);
		expectedGradeCard.add(gradeObject);
		try {
			assertEquals(expectedGradeCard, registrationImpl.viewGradeCard("4"));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void testViewAvailableCourses() throws CourseNotFoundException, SQLException {
		List<Course> expectedAvailableCourseList = new ArrayList<>();
		course.setCourseCode("229");
		course.setCourseName("Art");
		course.setInstructorId("444");
		course.setSeats(10);
		course.setFee(100);
		expectedAvailableCourseList.add(course);
		
		course.setCourseCode("230");
		course.setCourseName("Hindi");
		course.setInstructorId("0");
		course.setSeats(14);
		course.setFee(60);
		expectedAvailableCourseList.add(course);

		assertEquals(expectedAvailableCourseList, registrationImpl.viewCourses("raj"));
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

}
