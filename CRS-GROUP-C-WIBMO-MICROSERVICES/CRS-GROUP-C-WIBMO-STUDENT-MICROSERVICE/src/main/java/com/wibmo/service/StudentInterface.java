/**
 * 
 */
package com.wibmo.service;

import java.util.List;
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
import com.wibmo.model.Student;
import com.wibmo.model.User;

/**
 * 
 */

@Service
public interface StudentInterface {

	/**
	 * Method to add student
	 * @param student
	 * @throws UserIdAlreadyExists
	 * @throws UserNotAddedException
	 */
	public void register(Student student) throws UserIdAlreadyExists, UserNotAddedException;
	
	/**
	 * Method to check the registration status of the student
	 * @param studentName
	 * @return status(0 or 1)
	 * @throws UserNotFoundException
	 */
	public int getRegistrationStatus(String studentName) throws UserNotFoundException;

	/**
	 * Method to get the Payment status of the student
	 * @param studentName
	 * @return status(0 or 1)
	 * @throws UserNotFoundException
	 */
	public int getPaymentStatus(String studentName) throws UserNotFoundException;

	/**
	 * Method to set payment status of Student to 1 after successful fee payment
	 * @param studentName
	 */
	public void setPaymentStatus(String studentName);

	/**
	 * Method to get Approval status of Student
	 * @param studentName
	 * @return status(0 or 1)
	 * @throws StudentNotApprovedException
	 * @throws UserNotFoundException
	 */
	public int getApprovalStatus(String studentName) throws StudentNotApprovedException, UserNotFoundException;
	
	/**
	 * Method to add User details related to Student
	 * @param user
	 * @throws UserNotAddedException
	 */
	public void addUser(User user) throws UserNotAddedException;

	/**
	 * Method to view Primary courses registered by the student
	 * @param studentName
	 * @return List of Courses
	 */
	List<Course> viewPrimaryCourses(String studentName);

	/**
	 * Method to view Primary and Secondary courses registered by the student
	 * @param studentName
	 * @return List of Courses
	 * @throws StudentNotRegisteredException
	 */
	List<Course> viewRegisteredCourses(String studentName) throws StudentNotRegisteredException;

	/**
	 * Method to view Grade Card of the student
	 * @param studentName
	 * @return List of Grades 
	 * @throws ReportCardNotGeneratedException
	 * @throws StudentNotRegisteredException
	 */
	List<Grade> viewGradeCard(String studentName) throws ReportCardNotGeneratedException, StudentNotRegisteredException;

	/**
	 * Method to view Available courses which are not registered by the student
	 * @param studentName
	 * @return List of Courses
	 */
	List<Course> viewAvailableCourses(String studentName);

	/**
	 * Method to register for 6 courses at once : 4 Primary Courses and 2 Secondary Courses
	 * @param studentName
	 * @param courseList
	 * @return status
	 * @throws UserNotFoundException
	 * @throws CourseSizeViolation
	 * @throws CourseLimitExceededForPrimaryException
	 * @throws CourseLimitExceededForSecondaryException
	 * @throws StudentAlreadyRegisteredException
	 * @throws UserNotFoundException
	 */
	boolean registerCourse(String studentName, List<String> courseList)
			throws UserNotFoundException, CourseSizeViolation, CourseLimitExceededForPrimaryException,
			CourseLimitExceededForSecondaryException, StudentAlreadyRegisteredException, UserNotFoundException;

	/**
	 * Method to add Secondary Course
	 * @param userId
	 * @param courseCode
	 * @return status
	 */
	boolean addSecondaryCourse(String userId, String courseCode);

	/**
	 * Method to add Primary Course
	 * @param courseCode
	 * @param studentName
	 * @param availableCourseList
	 * @return status(0 or 1)
	 * @throws CourseNotFoundException
	 * @throws CourseLimitExceededException
	 * @throws SeatNotAvailableException
	 * @throws CourseAlreadyRegisteredException
	 * @throws StudentAlreadyRegisteredException
	 */
	int addCourse(String courseCode, String studentName, List<Course> availableCourseList)
			throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException,
			CourseAlreadyRegisteredException, StudentAlreadyRegisteredException;

	/**
	 * Method to drop the registered Primary Course
	 * @param courseCode
	 * @param studentName
	 * @param registeredCourseList
	 * @throws CourseNotFoundException
	 * @throws StudentAlreadyRegisteredException
	 */
	void dropCourse(String courseCode, String studentName, List<Course> registeredCourseList)
			throws CourseNotFoundException, StudentAlreadyRegisteredException;

	/**
	 * Method to calculate Fees for the registered courses
	 * @param studentName
	 * @return amount
	 */
	public double calculateFee(String studentName);
	
	/**
	 * Method to send Notification to the Student
	 * @param type
	 * @param name
	 */
	public void sendNotification(NotificationTypeConstant type, String name);
	
	/**
	 * Method to add Payment details to Payment table
	 * @param name
	 * @param mode
	 * @param type
	 * @param fee
	 * @return
	 */
	public String addPayment(String name,PaymentModeConstant mode, String type, double fee);

	/**
	 * Method to find studentId from username of student
	 * @param name
	 * @return
	 */
	public String findByStudentName(String name);
}
