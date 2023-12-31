/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.model.EnrolledStudent;
import com.wibmo.model.RegisteredCourse;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.validator.ProfessorValidator;

/**
 * 
 */
@Service
public class ProfessorService implements ProfessorInterface {

	@Autowired 
	ProfessorRepository professorRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RegisteredCourseRepository registeredCourseRepo;
	
	//Method to get the courses for the professor
	@Override
	public List<Course> viewCourses(String username) {
		List<Course> coursesOffered= new ArrayList<Course>();
		try
		{
			String userID = userRepo.findByUsername(username).get().getuserID();
			coursesOffered = courseRepo.findByProfessorID(userID);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return coursesOffered;
	}
	
	@Override
	public List<EnrolledStudent> viewEnrolledStudents(String username) throws UserNotFoundException{
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		String userID = userRepo.findByUsername(username).get().getuserID();
		
		List<Course> courses = courseRepo.findAllByProfessorID(userID);
			
		for(Course course : courses ) {
			List<RegisteredCourse> registeredCourses = registeredCourseRepo.findAllByCourseCode(course.getCourseCode());
			for(RegisteredCourse regCourse : registeredCourses) {
				EnrolledStudent enrolledStudent = new EnrolledStudent();
				enrolledStudent.setCourseCode(course.getCourseCode());
				enrolledStudent.setCourseName(course.getCourseName());
				enrolledStudent.setStudentId(regCourse.getstudentId());
				enrolledStudents.add(enrolledStudent);
			}
		}
		return enrolledStudents;
	}


	public boolean addGrade(String username, String studentID, String courseCode, String grade) throws StudentNotRegisteredException, UserNotFoundException {
		String studentName = userRepo.findByUserID(studentID).getusername();
		try {
			if (!ProfessorValidator.isValidStudent(viewEnrolledStudents(username), studentID)) {
				throw new StudentNotRegisteredException(studentName);
			}
		} catch (UserNotFoundException e) {
			throw e;
		} 
		String userID = userRepo.findByUsername(username).get().getuserID();
		List<Course> courses = courseRepo.findAllByProfessorID(userID);
		
		for(Course course : courses ) {
			if(course.getCourseCode().equals(courseCode))
			{
				registeredCourseRepo.addGrade(grade, courseCode, studentID);
				return true;
			}
		}
		
		return false;
		
	}

	public boolean isProfessorExists(String professorId) {
		if(professorRepo.findByProfessorID(professorId).isPresent()) {
			return true;
		}
		return false;
	}
}
