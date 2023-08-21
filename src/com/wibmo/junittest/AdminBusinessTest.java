package com.wibmo.junittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;


import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.business.AdminOperationImpl;
import com.wibmo.business.AdminOperationInterface;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.exception.StudentNotFoundForApprovalException;

public class AdminBusinessTest {
	private static Logger logger = Logger.getLogger(AdminBusinessTest.class);
	private AdminOperationInterface adminOperation;
	Course course = null;
	RegisteredCourse regCourse = null;
	Professor professor = null;
	Student student = null;
	
	@Before
	public void setUp() throws Exception {
		adminOperation = AdminOperationImpl.getInstance();
		course = new Course();
		regCourse = new RegisteredCourse();
		professor = new Professor();
		student = new Student();
	}

	@Test
	public void testGenerateGradeCard() {
		List<RegisteredCourse> expectedGradeCard = new ArrayList<RegisteredCourse>();
		course.setCourseCode("224");
		course.setCourseName("Maths");
		course.setInstructorId("444");
		course.setSeats(5);
		regCourse.setCourse(course);
		regCourse.setstudentId("123");
		regCourse.setGrade("A");
		expectedGradeCard.add(regCourse);
		assertEquals(expectedGradeCard, adminOperation.generateGradeCard("6"));
	}
	
	@Test
	public void testApproveStudent() {
		List<Student> studentList = new ArrayList<Student>();
		Student student = new Student();
		student.setStudentId("1");
		studentList.add(student);
		try {
			assertEquals("raj", adminOperation.approveStudent("1", studentList));
		} catch (StudentNotFoundForApprovalException e) {
			logger.info(e.getMessage());
		}
	}
	
	@Test
	public void viewCoursesSuccess() {
		
		List<Course> courseList = new ArrayList<>();
		course.setCourseCode("231");
		course.setCourseName("EVS");
		course.setInstructorId("444");
		courseList.add(course);
		
		assertEquals(courseList, adminOperation.viewCourses());
	}
	
	@Test
	public void viewProfessorsSuccess() {
		
		List<Professor> professorList = new ArrayList<Professor>();

		professor.setUserId("367");
		professor.setName("Aman");
		professor.setGender(GenderConstant.stringToGender("Male"));
		professor.setDepartment("Mechanical");
		professor.setDesignation("Assistant");
		professor.setAddress("Assam");
		professor.setRole(RoleConstant.PROFESSOR);
		professor.setPassword("*********");
		professorList.add(professor);
		
		assertEquals(professorList, adminOperation.viewProfessors());
	}
	
	@Test
	public void viewPendingAdmissionsSuccess() {
		List<Student> studentList = new ArrayList<Student>();
		student.setUserId("1");
		student.setName("raj");
		student.setPassword("pw");
		student.setRole(RoleConstant.stringToName("Student"));
		student.setGender(GenderConstant.stringToGender("Male"));
		student.setAddress("vlr");
		student.setStudentId("1");
		studentList.add(student);
		
		assertEquals(studentList, adminOperation.viewPendingAdmissions());
	}

}