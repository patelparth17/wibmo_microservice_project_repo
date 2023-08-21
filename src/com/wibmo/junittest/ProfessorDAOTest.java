package com.wibmo.junittest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public class ProfessorDAOTest {
	ProfessorDAOImpl professorDAOImpl = null;
	Professor professor = null;
	
	@Before
	public void setUp() throws Exception {
		professorDAOImpl = ProfessorDAOImpl.getInstance();
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
		List<EnrolledStudent> enrolledStudent = professorDAOImpl.getEnrolledStudents(professor.getName());
		assertThat(enrolledStudent, is(expectedEnrolledStudent));
//		assert.assertArrayEquals(enrolledStudent, expectedEnrolledStudent);
	}
	
	@Test
	public void testViewCourses() {
		List<Course> expectedListCourse = new ArrayList<>();
		expectedListCourse.add((Course) Arrays.asList(4, "224", "Eng", 9, "666", 50, "666", "ppp", "pwd", "PROFESSOR", "wsd", "FEMALE"));
		expectedListCourse.add((Course) Arrays.asList(5, "225", "Computer", 9, "666", 100, "666", "ppp", "pwd", "PROFESSOR", "wsd", "FEMALE"));
		
		List<Course> actualListCourse = professorDAOImpl.getCoursesByProfessor("ppp");
		
		assertThat(expectedListCourse, is(actualListCourse));
	}
	
//	@Test
//	public Boolean addGrade(String studentId,String courseCode,String grade) {
//		
//	}
}

