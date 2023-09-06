/**
 * Professor service test cases
 * @author vedasree
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.*;
/**
 * 
 */

@ExtendWith({MockitoExtension.class})
class ProfessorServiceTest {

	@InjectMocks
	ProfessorService professorService;
	
	@Mock
	ProfessorRepository professorRepo;
	
	@Mock
	CourseRepository courseRepo;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	RegisteredCourseRepository registeredCourseRepo;
	
	@Test
	public void viewCoursesTest() {
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		
		User user = new User("666","ppp","PROFESSOR","passwd","Female","wsd");
		
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		when(userRepo.findByUsername("ppp")).thenReturn(Optional.of(user));
		when(courseRepo.findByProfessorID("666")).thenReturn(expectedCourses) ;
		assertEquals(expectedCourses, professorService.viewCourses("ppp"));
	}
	
	@Test
	public void viewEnrolledStudentsTest()
	{
		EnrolledStudent enrolledStudent = new EnrolledStudent("224","Eng","1");
		List<EnrolledStudent> expectedEnrolledStudents = new ArrayList<EnrolledStudent>();
		expectedEnrolledStudents.add(enrolledStudent);
		
		Course course = new Course();
		course.setCourseCode("224");
		course.setCourseName("Eng");
		course.setFee(50);
		course.setProfessorID("666");
		course.setSeats(8);
		
		User user = new User("666","ppp","PROFESSOR","passwd","Female","wsd");
		RegisteredCourse registeredCourse = new RegisteredCourse();
		registeredCourse.setCourse(course);
		registeredCourse.setCourseCode("224");
		registeredCourse.setstudentId("1");
		
		List<RegisteredCourse> expectedRegCourses = new ArrayList<RegisteredCourse>();
		expectedRegCourses.add(registeredCourse);
		
		List<Course> expectedCourses = new ArrayList<Course>();
		expectedCourses.add(course);
		when(userRepo.findByUsername("ppp")).thenReturn(Optional.of(user));
		when(courseRepo.findAllByProfessorID("666")).thenReturn(expectedCourses) ;
		when(registeredCourseRepo.findAllByCourseCode("224")).thenReturn(expectedRegCourses);
		
		try {
			assertEquals(expectedEnrolledStudents,professorService.viewEnrolledStudents("ppp"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}	
	}

//	@Test
//	public void addGradeTest() {
//		User user = new User("666","ppp","PROFESSOR","passwd","Female","wsd");
//		
//		Course course = new Course();
//		course.setCourseCode("224");
//		course.setCourseName("Eng");
//		course.setFee(50);
//		course.setProfessorID("666");
//		course.setSeats(8);
//		RegisteredCourse registeredCourse = new RegisteredCourse();
//		registeredCourse.setCourse(course);
//		registeredCourse.setCourseCode("224");
//		registeredCourse.setstudentId("1");
//		
//		List<RegisteredCourse> expectedRegCourses = new ArrayList<RegisteredCourse>();
//		expectedRegCourses.add(registeredCourse);
//		
//		when(userRepo.findByUserID("1")).thenReturn(user);
//		when(registeredCourseRepo.findAllByStudentId("1")).thenReturn(expectedRegCourses);
//		when(registeredCourseRepo.addGrade("A", "224", "1"));
//		
//		try {
//			professorService.addGrade("ppp", "1", "224", "A");
//		} catch (UserNotFoundException | StudentNotRegisteredException | CourseNotAvailableException e) {
//			e.printStackTrace();
//		}
//		verify(registeredCourseRepo, times(1)).addGrade("A", "224", "1");
//		
//		
//	}
}
