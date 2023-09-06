/**
 * Admin service test cases
 * @author vedasree
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Professor;
import com.wibmo.model.Student;
import com.wibmo.model.User;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.NotificationRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;

/**
 * 
 */

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@Mock
	CourseRepository courseRepo;

	@Mock
	StudentRepository studentRepo;
	
	@Mock
	ProfessorRepository professorRepo;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	RegisteredCourseRepository registeredCourseRepo;
	
	@Mock
	NotificationRepository notificationRepo;
	
	@InjectMocks
	AdminService adminService;
	
	@Test
	public void viewCoursesTest() {
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		
		when(courseRepo.findAll()).thenReturn(expectedCourses);
		assertEquals(expectedCourses, adminService.viewCourses());
	}
	
	@Test
	public void viewPendingAdmissionsTest() {
		Student student = new Student("1","Veda","Student","passwd","Female","AP",
				"CSE",2023,false,false,false,false);
		List<Student> expectedStudents = new ArrayList<Student>();
		expectedStudents.add(student);
		when(studentRepo.viewPendingAdmissions()).thenReturn(expectedStudents);
		assertEquals(expectedStudents, adminService.viewPendingAdmissions());
	}
	
	@Test
	public void approveAllStudentsTest() {
		adminService.approveAllStudents();
		verify(studentRepo, times(1)).approveAllStudents();
	}
	
	@Test
	public void addProfessorTest() {
		Professor professor = new Professor();
		professor.setDepartment("CSE");
		professor.setProfessorID("1");
		professor.setDesignation("HOD");
		professor.setRole("PROFESSOR");
		
		try {
			adminService.addProfessor(professor);
		} catch (UserNotAddedException | ProfessorNotAddedException e) {
		}
		verify(userRepo, times(1)).save(professor);
	}
	
	@Test
	public void assignCourseTest() {
		Professor professor = new Professor();
		professor.setDepartment("CSE");
		professor.setProfessorID("1");
		professor.setDesignation("HOD");
		
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		
		when(professorRepo.findByProfessorID("1")).thenReturn(Optional.of(professor));
		when(courseRepo.findByCourseCode("224")).thenReturn(Optional.of(course));
		try {
			adminService.assignCourse("224", "1");
		} catch (UserNotFoundException | CourseNotFoundException e) {
		}
		verify(courseRepo,times(1)).assignCourse("224", "1");
	}
	
	@Test
	public void isProfessorExistsTest() {
		Professor professor = new Professor();
		professor.setDepartment("CSE");
		professor.setProfessorID("1");
		professor.setDesignation("HOD");
		
		when(professorRepo.findByProfessorID("1")).thenReturn(Optional.of(professor));
		assertEquals(true, adminService.isProfessorExists("1"));
	}
	
	@Test
	public void approveStudentRegisterationTest() {
		Student student = new Student("1","Veda","Student","passwd","Female","AP",
				"CSE",2023,false,false,false,false);
		when(studentRepo.findByStudentId("1")).thenReturn(Optional.of(student));
		when(studentRepo.getRegistrationStatus("1")).thenReturn(0);
		try {
			adminService.approveStudentRegisteration("1");
		} catch (StudentAlreadyRegisteredException | UserNotFoundException e) {
		}
		verify(studentRepo, times(1)).setRegisterationStatus("1");
	}
	
	@Test
	public void findUserNameTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");
		when(userRepo.findByUserID("1")).thenReturn(user);
		assertEquals("Veda", adminService.findUserName("1"));
	}
	
	@Test
	public void addUserTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");		
		try {
			adminService.addUser(user);
		} catch (UserNotAddedException e) {
		}
		verify(userRepo, times(1)).save(user);
	}
}
