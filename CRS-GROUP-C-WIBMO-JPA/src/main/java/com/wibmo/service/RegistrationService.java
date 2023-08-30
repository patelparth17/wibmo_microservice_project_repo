/**
 * 
 */
package com.wibmo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.model.Course;
import com.wibmo.model.Grade;
import com.wibmo.model.RegisteredCourse;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.repository.CourseRepository;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.validator.StudentValidator;

/**
 * Implementation of methods which calls Registration DAO methods
 */
@Service
public class RegistrationService implements RegistrationInterface {

	@Autowired
	private RegisteredCourseRepository registeredCourseRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	CourseRepository courseRepo;

	@Override
	public boolean addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();

		if (registeredCourseRepo.numOfRegisteredCourses(userId) >= 4
				&& registeredCourseRepo.numSecondaryCourses(userId) >= 2) {
			throw new CourseLimitExceededException(4);
		} else if (registeredCourseRepo.numSecondaryCourses(userId) < 2) {
			addSecondaryCourse(userId, courseCode);
			return true;
		} else if (studentRepo.findById(userId).get().isRegistered()) {
			return false;
		} else if (courseRepo.findByCourseCode(courseCode).get().getSeats() < 0) {
			throw new SeatNotAvailableException(courseCode);
		} else if (!StudentValidator.isValidCourseCode(courseCode, availableCourseList)) {
			throw new CourseNotFoundException(courseCode);
		}
		return registeredCourseRepo.addCourse(studentName, courseCode, "NOT_GRADED");

	}

//	@Override
//	public boolean dropCourse(String courseCode, String studentName,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
//		  if(!StudentValidator.isRegistered(courseCode, studentName, registeredCourseList))
//	        	throw new CourseNotFoundException(courseCode);	
//		return registeredCourseRepo.dropCourse(courseCode, studentName);
//	}
//
	@Override
	public double calculateFee(String studentName) {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		return courseRepo.calculateFee(userId);
	}

	@Override
	public List<Grade> viewGradeCard(String studentName) throws SQLException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();

		List<Grade> grades = new ArrayList<Grade>();
		List<RegisteredCourse> regCourses = registeredCourseRepo.findAllByStudentId(userId);
		System.out.println(regCourses.get(0).getCourseCode());
		for (RegisteredCourse registeredCourse : regCourses) {
			Grade grade = new Grade();
			grade.setCourseCode(courseRepo.findByCourseCode(registeredCourse.getCourseCode()).get().getCourseCode());
			grade.setCourseName(courseRepo.findByCourseCode(registeredCourse.getCourseCode()).get().getCourseName());
			grade.setGrade(registeredCourse.getGrade());
			grades.add(grade);
		}
		return grades;
	}

	@Override
	public List<Course> viewCourses(String studentName) throws SQLException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		return courseRepo.viewCourses(userId);
	}

	@Override
	public List<Course> viewRegisteredCourses(String studentName) throws SQLException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();

		List<Course> courses = new ArrayList<Course>();
		List<String> regCourses = registeredCourseRepo.getCourseCodes(userId);
		for (String registeredCourse : regCourses) {
			Course course = new Course();
			course.setCourseCode(courseRepo.findByCourseCode(registeredCourse).get().getCourseCode());
			course.setCourseName(courseRepo.findByCourseCode(registeredCourse).get().getCourseName());
			course.setFee(courseRepo.findByCourseCode(registeredCourse).get().getFee());
			course.setProfessorID(courseRepo.findByCourseCode(registeredCourse).get().getProfessorID());
			course.setSeats(courseRepo.findByCourseCode(registeredCourse).get().getSeats());
			courses.add(course);
		}
		return courses;
	}
	

//	@Override
//	public boolean registerCourse(String studentName, List<String> courseList) throws SQLException, CourseSizeViolation,
//			CourseLimitExceededForPrimaryException, CourseLimitExceededForSecondaryException, StudentAlreadyRegistered {
//		String userID = userRepo.findByUsername(studentName).get().getuserID();
//		if (courseList.size() != 6) {
//			throw new CourseSizeViolation();
//		} else if (registeredCourseRepo.numOfRegisteredCourses(studentName) > 4
//				&& registeredCourseRepo.numSecondaryCourses(studentName) <= 2) {
//			throw new CourseLimitExceededForPrimaryException();
//		} else if (registeredCourseRepo.numOfRegisteredCourses(studentName) <= 4
//				&& registeredCourseRepo.numSecondaryCourses(studentName) > 2) {
//			throw new CourseLimitExceededForSecondaryException();
//		} else if (studentService.getRegistrationStatus(userID)) {
//			throw new StudentAlreadyRegistered(studentName);
//		}
//		int indexOfUnregisteredCourse = (viewRegisteredCourses(studentName) == null) ? 1
//				: viewRegisteredCourses(studentName).size() + 1;
//		int index = 0;
//		while (indexOfUnregisteredCourse <= 4) {
//			if (registeredCourseRepo.addCourse(courseList.get(index), studentName)) {
////                    logger.info("You have successfully enrolled for "+ courseList.get(index));
//				indexOfUnregisteredCourse++;
//			} else {
////                    logger.info(" You have already registered for Course : " + courseList.get(index));
//			}
//			index++;
//		}
////        logger.debug("\n*******************************************************");
////        logger.debug("        Primary Course Registration Successful");
////        logger.debug("*******************************************************\n");
////        
//
//		int indexOfSecondaryCourse = 1;
//		while (indexOfSecondaryCourse <= 2) {
//			if (registeredCourseRepo.addSecondaryCourse(courseList.get(index), studentName)) {
////                    logger.info("You have successfully enrolled for "+ courseList.get(index));
//				indexOfSecondaryCourse++;
//			} else {
////                    logger.info(" You have already registered for Course : " + courseList.get(index));
//			}
//			index++;
//		}
////        logger.debug("\n*******************************************************");
////        logger.debug("        Secondary Course Registration Successful");
////        logger.debug("*******************************************************\n");
////        registeredCourseRepo.setRegistrationStatus(studentName);
//		return false;
//
//	}

	@Override
	public boolean addSecondaryCourse(String userId, String courseCode) {
		registeredCourseRepo.addSecondaryCourse(userId, courseCode);
		registeredCourseRepo.decrementSeats(courseCode);
		return true;
	}

}