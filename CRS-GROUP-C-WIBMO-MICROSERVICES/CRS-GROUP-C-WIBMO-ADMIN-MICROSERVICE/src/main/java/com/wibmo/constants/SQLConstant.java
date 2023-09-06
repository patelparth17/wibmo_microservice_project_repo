/**
 * 
 */
package com.wibmo.constants;

/**
 * 
 */
public class SQLConstant {
	
	// Course custom queries
	public static final String ADD_COURSE_QUERY = "INSERT INTO Course(courseCode, courseName, seats, professorID,courseFee) values (?1, ?2, ?3, ?4, ?5)";
	public static final String DELETE_COURSE_QUERY = "DELETE FROM Course WHERE courseCode = ?1";
	public static final String ASSIGN_COURSE_QUERY = "UPDATE Course SET professorID = ?2 WHERE courseCode = ?1";
	public static final String VIEW_AVAILABLE_COURSE_QUERY = "SELECT * FROM course WHERE courseCode NOT IN  (SELECT courseCode  FROM registeredcourse WHERE studentId = ?1 UNION SELECT courseCode  FROM secondarycourse WHERE studentId = ?1) AND seats > 0";
	public static final String CALCULATE_FEE_QUERY = "SELECT sum(courseFee) FROM course WHERE courseCode IN (SELECT courseCode FROM registeredcourse WHERE studentId = ?1)";
	public static final String INCREMENT_SEATS_QUERY = "UPDATE course SET seats = seats+1 WHERE courseCode=?1";
	public static final String DECREMENT_SEATS_QUERY = "UPDATE course SET seats = seats-1 WHERE courseCode = ?1";
	
	// Registered course custom queries
	public static final String GET_GRADES_QUERY = "SELECT courseCode, grade FROM registeredcourse WHERE studentId = ?1";
	public static final String NUM_OF_REGISTERED_COURSES = "SELECT count(studentId) FROM registeredcourse WHERE studentId = ?1";
	public static final String NUM_OF_SECONDARY_COURSES = "SELECT count(studentId) FROM secondarycourse WHERE studentId = ?1";
	public static final String ADD_SECONDARY_COURSE_QUERY = "INSERT INTO secondarycourse (studentId, courseCode) VALUES (?1 , ?2)";
	public static final String ADD_PRIMARY_COURSE_QUERY = "INSERT INTO registeredcourse (studentId,courseCode,grade) VALUES ( ?1 , ?2, ?3)";
	public static final String GET_COURSES_QUERY = "SELECT DISTINCT courseCode FROM registeredcourse rc WHERE rc.studentID = :studentId";
	public static final String DROP_COURSE_QUERY = "DELETE registeredcourse FROM registeredcourse WHERE studentID = ?1 AND courseCode = ?2";
	public static final String GET_REGISTERED_COURSES = "SELECT courseCode FROM registeredcourse WHERE studentID = ?1 AND courseCode = ?2 ";
	public static final String GET_SECONDARY_COURSES = "SELECT courseCode FROM secondarycourse WHERE studentID = ?1 ";
	public static final String DROP_SECONDARY_COURSE = "DELETE secondarycourse FROM secondarycourse WHERE studentID = ?1 AND courseCode = ?2";
	public static final String ADD_GRADE_QUERY = "UPDATE registeredcourse SET Grade=?1 WHERE courseCode=?2 AND studentId=?3";
	
	// Student custom queries
	public static final String VIEW_PENDING_APPROVALS = "SELECT * FROM Student s INNER JOIN User u WHERE s.studentID = u.userID AND isApproved = 0";
	public static final String APPROVE_STUDENT_QUERY = "UPDATE Student SET isApproved = 1 WHERE studentId = ?1";
	public static final String APPROVE_ALL_STUDENTS = "UPDATE Student SET isApproved = 1";
	public static final String GET_REGISTRATION_STATUS = "SELECT isRegistered FROM Student WHERE StudentID = ?1";
	public static final String GET_PAYMENT_STATUS = "SELECT isPaid FROM Student WHERE StudentID = ?1";
	public static final String SET_PAYMENT_STATUS = "UPDATE Student SET isPaid = 1 WHERE studentId = ?1";
	public static final String GET_APPROVAL_STATUS = "SELECT isApproved FROM Student WHERE StudentID = ?1";
	public static final String SET_REGISTRATION_STATUS = "UPDATE Student SET isRegistered = 1 WHERE studentId = ?1";
	public static final String SET_REPORT_CARD_STATUS = "UPDATE Student SET isReportGenerated = 1 WHERE studentId = ?1";
	public static final String GET_REPORT_CARD_STATUS = "SELECT isReportGenerated FROM Student WHERE StudentID = ?1";
	
	// User custom queries
	public static final String UPDATE_PASSWORD_QUERY = "UPDATE User SET password=?2 WHERE username = ?1";
	
	// Payment Queries
	public static final String ADD_PAYMENT = "INSERT INTO payment (studentName, modeOfPayment, amount, referenceId, paymentStatus) VALUES ( ?1 , ?2, ?3, ?4, ?5)";

	//Notification Queries
	public static final String ADD_NOTIFICATION = "INSERT INTO notification (studentName, type, referenceID, message) VALUES ( ?1, ?2, ?3, ?4)";

}
