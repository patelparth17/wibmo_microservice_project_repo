/**
 * 
 */
package com.wibmo.junittest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Professor;
import com.wibmo.business.ProfessorImpl;
import com.wibmo.exception.GradeNotAllotedException;

/**
 * 
 */
public class ProfessorImplTest {
	private static Logger logger = Logger.getLogger(ProfessorImplTest.class);
	ProfessorImpl professorImpl = null;
	Professor professor = null;
	Course course = null;
	
	@Before
	public void setUp() throws Exception {
		professorImpl = professorImpl.getInstance();
		professor = new Professor();
		course = new Course();
	}
	
	@Test
	public void testViewEnrolledStudents() {
		EnrolledStudent enrolledStudent1 = new EnrolledStudent("224", "Eng", "1");
		EnrolledStudent enrolledStudent2 = new EnrolledStudent("225", "Computer", "1");
		List<EnrolledStudent> expectedEnrolledStudent = new ArrayList<>();
		expectedEnrolledStudent.add(enrolledStudent1);
		expectedEnrolledStudent.add(enrolledStudent2);

		professor.setName("PPP");
		List<EnrolledStudent> enrolledStudent = professorImpl.viewEnrolledStudents(professor.getName());
		assertEquals(enrolledStudent, expectedEnrolledStudent);
	}
	
	@Test
	public void testViewCourses() {
		List<Course> expectedListCourse = new ArrayList<>();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setInstructorId("666");
		course.setSeats(9);
		course.setFee(50);
		expectedListCourse.add(course);
		assertEquals(expectedListCourse, professorImpl.viewCourses("ppp"));
	}
	
	@Test
	public void testAddGrade() {
		try {
			assertTrue(professorImpl.addGrade("123", "224", "B_plus"));
		} catch (GradeNotAllotedException e) {
			logger.info(e.getMessage());
		}
	}
}
