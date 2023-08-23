/**
 * 
 */
package com.wibmo.junittest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Professor;
import com.wibmo.business.ProfessorImpl;

/**
 * 
 */
public class ProfessorImplTest {
	ProfessorImpl professorImpl = null;
	Professor professor = null;
	
	@Before
	public void setUp() throws Exception {
		professorImpl = professorImpl.getInstance();
		professor = new Professor();
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
//		assert.assertArrayEquals(enrolledStudent, expectedEnrolledStudent);
	}
	
	@Test
	public void testViewCourses() {
		List<Course> expectedListCourse = new ArrayList<>();
		expectedListCourse.add((Course) Arrays.asList(4, "224", "Eng", 9, "666", 50, "666", "ppp", "pwd", "PROFESSOR", "wsd", "FEMALE"));
		expectedListCourse.add((Course) Arrays.asList(5, "225", "Computer", 9, "666", 100, "666", "ppp", "pwd", "PROFESSOR", "wsd", "FEMALE"));
		
		List<Course> actualListCourse = professorImpl.viewCourses("ppp");
		assertNotNull(actualListCourse);
	}
	
//	@Test
//	public void testAddGrade() {
//		
//	}
}
