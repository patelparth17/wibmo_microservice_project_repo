/**
 * Student service test cases
 * @author vedasree
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import com.wibmo.repository.*;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseAlreadyRegisteredException;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.*;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	RegisteredCourseRepository registeredCourseRepo;
	
	@Mock
	CourseRepository courseRepo;
	
	@Mock
	NotificationRepository notificationRepo;
	
	@Mock
	PaymentRepository paymentRepo;
	
	@Mock
	StudentRepository studentRepo;
	
	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	StudentService studentService;
	
	@Test
	public void registerStudentTest() {
		Student student = new Student("1","Veda","Student","passwd","Female","AP",
				"CSE",2023,false,false,false,false);
		
		try {
			studentService.addUser(student);
		} catch (UserNotAddedException e) {
			e.printStackTrace();
		}
		verify(userRepo, times(1)).save(student);
		
		
	}
	
	@Test
	public void addUserTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");		
		try {
			studentService.addUser(user);
		} catch (UserNotAddedException e) {
			e.printStackTrace();
		}
		verify(userRepo, times(1)).save(user);
	}
	
	@Test
	public void getRegistrationStatusTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(studentRepo.getRegistrationStatus("1")).thenReturn(1);
		try {
			assertEquals(1, studentService.getRegistrationStatus("Veda"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPaymentStatusTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(studentRepo.getPaymentStatus("1")).thenReturn(1);
		try {
			assertEquals(1, studentService.getPaymentStatus("Veda"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getApprovalStatusTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(studentRepo.getApprovalStatus("1")).thenReturn(1);
		try {
			assertEquals(1, studentService.getApprovalStatus("Veda"));
		} catch (UserNotFoundException | StudentNotApprovedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setPaymentStatusTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		studentService.setPaymentStatus("Veda");
		verify(studentRepo, times(1)).setPaymentStatus("1");
	}
	
	@Test
	public void viewPrimaryCoursesTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		
		List<String> courseCodes = new ArrayList<String>();
		courseCodes.add("224");
		
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(registeredCourseRepo.getCourseCodes("1")).thenReturn(courseCodes);
		when(courseRepo.findByCourseCode("224")).thenReturn(Optional.of(course));
		assertEquals(expectedCourses.get(0).getCourseCode(), studentService.viewPrimaryCourses("Veda").get(0).getCourseCode());
	}
	
	@Test
	public void viewRegisteredCoursesTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		try {
			assertEquals(expectedCourses.get(0).getCourseCode(), studentService.viewRegisteredCourses("Veda").get(0).getCourseCode());
		} catch (StudentNotRegisteredException e) {
		}
	}
	
	@Test
	public void viewAvailableCoursesTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(courseRepo.viewCourses("1")).thenReturn(expectedCourses);
		assertEquals(expectedCourses, studentService.viewAvailableCourses("Veda"));
	}
	
	@Test
	public void addCourseTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		List<Course> courses = new ArrayList<Course>();
		courses.add(course);
		
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(studentRepo.getRegistrationStatus("1")).thenReturn(0);
		when(registeredCourseRepo.getRegisteredCourses("1", "224")).thenReturn(null);
		when(registeredCourseRepo.numOfRegisteredCourses("1")).thenReturn(3);
		when(courseRepo.findByCourseCode("224")).thenReturn(Optional.of(course));
		when(registeredCourseRepo.addCourse("1", "224", "NOT_GRADED")).thenReturn(1);
		
		try {
			assertEquals(1, studentService.addCourse("224", "Veda", courses));
		} catch (CourseNotFoundException | CourseLimitExceededException | SeatNotAvailableException
				| CourseAlreadyRegisteredException | StudentAlreadyRegisteredException e) {
		}
	}
	
	@Test
	public void addSecondaryCourseTest() {
		assertEquals(true, studentService.addSecondaryCourse("1", "224"));
	}
	
	@Test
	public void calculateFeeTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(courseRepo.calculateFee("1")).thenReturn(720.0);
		assertEquals(720.0, studentService.calculateFee("Veda"));
	}
	
	@Test
	public void addPaymentTest() {
		assertNotEquals("", studentService.addPayment("Veda", PaymentModeConstant.UPI, "PAID", 100));
	}
	
	@Test
	public void findByStudentNameTest() {
		Payment payment = new Payment(100.0,"12345","PAID",PaymentModeConstant.UPI);
		when(paymentRepo.findByStudentName("Veda")).thenReturn(payment);
		assertEquals("12345",studentService.findByStudentName("Veda"));
	}
}
