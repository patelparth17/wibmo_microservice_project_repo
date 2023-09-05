/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseAlreadyRegisteredException;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseLimitExceededForPrimaryException;
import com.wibmo.exception.CourseLimitExceededForSecondaryException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.CourseSizeViolation;
import com.wibmo.exception.ReportCardNotGeneratedException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentAlreadyRegisteredException;
import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserIdAlreadyExists;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Course;
import com.wibmo.model.Grade;
import com.wibmo.model.Notification;
import com.wibmo.model.Student;
import com.wibmo.model.User;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.NotificationRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.validator.StudentValidator;

/**
 * 
 */

@Service
public class StudentService implements StudentInterface{

	@Autowired
	private RegisteredCourseRepository registeredCourseRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@Autowired
	NotificationRepository notificationRepo;
	
	@Modifying
	@Transactional
	public void register(Student student) throws UserIdAlreadyExists, UserNotAddedException {
		if(userRepo.findById(student.getuserID()).isPresent()) {
			throw new UserIdAlreadyExists(student.getuserID());
		}
		
		try {
			addUser(student);
		} catch (UserNotAddedException e) {
			throw e;
		}
	}

	public int getRegistrationStatus(String studentName) throws UserNotFoundException {
		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		
		return studentRepo.getRegistrationStatus(userID);
	}

	public int getPaymentStatus(String studentName) throws UserNotFoundException  {
		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		return studentRepo.getPaymentStatus(userID);
	}

	public void setPaymentStatus(String studentName) {
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		studentRepo.setPaymentStatus(userID);		
	}

	public int getApprovalStatus(String studentName) throws StudentNotApprovedException, UserNotFoundException {

		if(userRepo.findByUsername(studentName).isEmpty()) {
			throw new UserNotFoundException(studentName);
		}
		String userID = userRepo.findByUsername(studentName).get().getuserID();

		int approvalStatus = studentRepo.getApprovalStatus(userID);
		if(approvalStatus == 0) {
			throw new StudentNotApprovedException(studentName);
		}
		return approvalStatus;
	}
	
	@Modifying
	public void addUser(User user) throws UserNotAddedException {
		if(userRepo.findByUsername(user.getusername()).isPresent()) {
			throw new UserNotAddedException(user.getusername());
		}
		user.setRole(user.getRole().toUpperCase());
		userRepo.save(user);	
			
	}
	
	@Override
	public List<Course> viewPrimaryCourses (String studentName) {
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
	
	@Override
	public List<Course> viewRegisteredCourses (String studentName) throws StudentNotRegisteredException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		if(studentRepo.getRegistrationStatus(userId)==0) {
			throw new StudentNotRegisteredException(studentName);
		}
		return viewPrimaryCourses(studentName);
	}
	
	@Override
	public List<Grade> viewGradeCard(String studentName) throws ReportCardNotGeneratedException, StudentNotRegisteredException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		if(studentRepo.getRegistrationStatus(userId)==0) {
			throw new StudentNotRegisteredException(studentName);
		}
		if(studentRepo.getGeneratedReportCardStatus(userId)==0) {
			throw new ReportCardNotGeneratedException(studentName);
		}
		List<Map<String, String>> registeredCourses = registeredCourseRepo.findByStudentId(userId);
		
		List<Grade> grades = new ArrayList<Grade>();
		for(Map<String, String> map : registeredCourses) {
			for(Map.Entry<String,String> entry : map.entrySet()) {
				if(courseRepo.findByCourseCode(entry.getValue()).isPresent()) {
					Course course = courseRepo.findByCourseCode(entry.getValue()).get();
					Grade grade = new Grade();
					grade.setCourseCode(entry.getValue());
					grade.setCourseName(course.getCourseName());
					grade.setGrade(map.get("grade"));
					grades.add(grade);
				}
			}
		}
		return grades;
	}
	
	@Override
	public List<Course> viewAvailableCourses(String studentName) {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		return courseRepo.viewCourses(userId);
	}
	
	@Override
	public boolean registerCourse(String studentName, List<String> courseList) throws UserNotFoundException, CourseSizeViolation,
			CourseLimitExceededForPrimaryException, CourseLimitExceededForSecondaryException, StudentAlreadyRegisteredException, UserNotFoundException, CourseLimitExceededException {
		String userID = userRepo.findByUsername(studentName).get().getuserID();
		if (courseList.size() != 6) {
			throw new CourseSizeViolation();
		} else if (getRegistrationStatus(studentName)==1) {
			throw new StudentAlreadyRegisteredException(studentName);
		} else if (registeredCourseRepo.numOfRegisteredCourses(userID) >= 4
				&& registeredCourseRepo.numSecondaryCourses(userID) >= 2) {
			throw new CourseLimitExceededException(4);
		}else if (registeredCourseRepo.numOfRegisteredCourses(userID) > 4
				&& registeredCourseRepo.numSecondaryCourses(userID) <= 2) {
			throw new CourseLimitExceededForPrimaryException();
		} else if (registeredCourseRepo.numOfRegisteredCourses(userID) <= 4
				&& registeredCourseRepo.numSecondaryCourses(userID) > 2) {
			throw new CourseLimitExceededForSecondaryException();
		} 
		int indexOfUnregisteredCourse = (viewPrimaryCourses(studentName) == null) ? 1: viewPrimaryCourses(studentName).size() + 1;
		int index = 0;
		while (indexOfUnregisteredCourse <= 4) {
			if (registeredCourseRepo.addCourse(userID, courseList.get(index), "NOT_GRADED")>0) {
				indexOfUnregisteredCourse++;
			} 
			index++;
		}     

		int indexOfSecondaryCourse = registeredCourseRepo.numSecondaryCourses(userID)+1;
		while (indexOfSecondaryCourse <= 2) {
			if (addSecondaryCourse(userID, courseList.get(index))) {
				indexOfSecondaryCourse++;
			} 
			index++;
		}
		
		return true;

	}

	@Override
	public boolean addSecondaryCourse(String userId, String courseCode) {
		registeredCourseRepo.addSecondaryCourse(userId, courseCode);
		return true;
	}
	
	@Override
	public int addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, CourseAlreadyRegisteredException, StudentAlreadyRegisteredException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();

		if (studentRepo.getRegistrationStatus(userId)==1) {
			throw new StudentAlreadyRegisteredException(userId);
		} else if (registeredCourseRepo.getRegisteredCourses(userId, courseCode)!= null) {
			throw new CourseAlreadyRegisteredException(courseCode);
		}
		else if (registeredCourseRepo.numOfRegisteredCourses(userId) >= 4
				&& registeredCourseRepo.numSecondaryCourses(userId) >= 2) {
			throw new CourseLimitExceededException(4);
		} else if (courseRepo.findByCourseCode(courseCode).get().getSeats() < 0) {
			throw new SeatNotAvailableException(courseCode);
		} else if (!StudentValidator.isValidCourseCode(courseCode, availableCourseList)) {
			throw new CourseNotFoundException(courseCode);
		} 
		else {
			if(registeredCourseRepo.numOfRegisteredCourses(userId) < 4) {
				return registeredCourseRepo.addCourse(userId, courseCode, "NOT_GRADED");
			}
			else { 
				addSecondaryCourse(userId, courseCode);
				return 1;
			}
		}
	}

	@Override
	public void dropCourse(String courseCode, String studentName,List<Course> registeredCourseList) throws CourseNotFoundException, StudentAlreadyRegisteredException {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		if (studentRepo.getRegistrationStatus(userId)==1) {
			throw new StudentAlreadyRegisteredException(userId);
		}   
		if(!StudentValidator.isRegistered(courseCode, studentName, registeredCourseList))
	        	throw new CourseNotFoundException(courseCode);
		registeredCourseRepo.dropCourse(userId, courseCode);
		courseRepo.incrementSeats(courseCode);
		if( registeredCourseRepo.getSecondaryCourses(userId).isPresent())
		{
			String course = registeredCourseRepo.getSecondaryCourses(userId).get().get(0);
			registeredCourseRepo.addCourse(userId, course, "NOT_GRADED");
			registeredCourseRepo.dropSecondaryCourse(userId, course);
			courseRepo.decrementSeats(course);
		}
	}

	@Override
	public double calculateFee(String studentName) {
		String userId = userRepo.findByUsername(studentName).get().getuserID();
		return courseRepo.calculateFee(userId);
	}
	
	@Modifying
	@Transactional
	public void sendNotification(NotificationTypeConstant type, String name) {
		String referenceID = "";
		if(type==NotificationTypeConstant.PAID)
		{
			referenceID=findByStudentName(name);
		}
		Notification notification = new Notification();
		notification.setNotificationType(type.toString());
		notification.setReferenceID(referenceID);
		notification.setStudentName(name);
		
		notificationRepo.save(notification);
	}
	
	@Modifying
	public String addPayment(String name,PaymentModeConstant mode, String type, double fee) {
		String referenceId=UUID.randomUUID().toString();
		paymentRepo.addPayment(name, mode.toString(), fee, referenceId, type);
		return referenceId;
	}

	public String findByStudentName(String name) {
		return paymentRepo.findByStudentName(name).getTranscationId();
	}

}
