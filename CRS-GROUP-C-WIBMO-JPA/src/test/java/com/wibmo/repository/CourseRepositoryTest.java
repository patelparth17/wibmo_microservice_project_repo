/**
 * @author parth.patel
 */
package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import com.wibmo.exception.CourseAlreadyExistsException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.service.AdminService;
import com.wibmo.service.ProfessorService;

/**
 * Test cases to test the queries in the courseRepo Class
 */
@ExtendWith(MockitoExtension.class)
public class CourseRepositoryTest {

	@InjectMocks
    AdminService adminService;
	
	@Mock
	ProfessorService professorService;
	
	@Mock
	CourseRepository courseRepo;
	
	@Test
	void addCourseTest() throws CourseAlreadyExistsException {
		Course expectedCourse = new Course();
		expectedCourse.setCourseCode("CS-103");
		expectedCourse.setCourseName("DBMS");
		expectedCourse.setFee(150.5);
        
		List<Course> expectedCourseList = adminService.viewCourses();
        adminService.addCourse(expectedCourse, expectedCourseList);
        
        List<Course> actualCourseList = adminService.viewCourses();
        System.out.println(expectedCourseList);
        assertEquals(expectedCourseList, actualCourseList);
        verify(courseRepo,times(1)).addCourse("CS-103", "DBMS", 10, null, 150.5);
	}
	
	@Test
	void removeCourseTest() throws CourseNotFoundException, CourseNotDeletedException {
		Course temporaryCourse = new Course();
		temporaryCourse.setCourseCode("252");
        List<Course> actualCourseList = new ArrayList<Course>();
        actualCourseList.add(temporaryCourse);
        
        doNothing().when(courseRepo).removeCourse("252");
        adminService.removeCourse("252", actualCourseList);
        verify(courseRepo,times(1)).removeCourse("252");
	}
	
	@Test
	void assignCourseTest() throws UserNotFoundException, CourseNotFoundException {
		Course temporaryCourse = new Course();
		temporaryCourse.setCourseCode("252");
        List<Course> actualCourseList = new ArrayList<Course>();
        actualCourseList.add(temporaryCourse);
        
        when(professorService.isProfessorExists("666")).thenReturn(true);
        when(courseRepo.findByCourseCode("252")).thenReturn(Optional.of(temporaryCourse));
        doNothing().when(courseRepo).assignCourse("252", "666");
        adminService.assignCourse("252", "666");
        verify(courseRepo,times(1)).assignCourse("252", "666");
	}
	
//	@Test
//	void viewCoursesTest() {
//		Course temporaryCourse = new Course();
//		temporaryCourse.setCourseCode("252");
//        List<Course> actualCourseList = new ArrayList<Course>();
//        actualCourseList.add(temporaryCourse);
//        
//        when(professorService.isProfessorExists("666")).thenReturn(true);
//        when(courseRepo.findByCourseCode("252")).thenReturn(Optional.of(temporaryCourse));
//        doNothing().when(courseRepo).assignCourse("252", "666");
////        adminService.viewCourses("252", "666");
//        verify(courseRepo,times(1)).assignCourse("252", "666");
//	}
}
