package com.wibmo.junittest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.business.AdminOperationImpl;
import com.wibmo.business.AdminOperationInterface;

public class AdminBusinessTest {
	private AdminOperationInterface adminOperation;
	@Before
	public void setUp() throws Exception {
		adminOperation = AdminOperationImpl.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateGradeCard() {
		assertNotNull( adminOperation.generateGradeCard("6"));
	}
	
	@Test
	public void viewCoursesSuccess() {
		List<Course> course = null;
		course = adminOperation.viewCourses();
		assertNotNull(course);
	}
	
	@Test
	public void viewProfessorsSuccess() {
		List<Professor> professor = null;
		professor = adminOperation.viewProfessors();
		assertNotNull(professor);
	}
	
	@Test
	public void viewPendingAdmissionsSuccess() {
		List<Student> pendingAdmission = null;
		pendingAdmission = adminOperation.viewPendingAdmissions();
		assertNotNull(pendingAdmission);
	}

}