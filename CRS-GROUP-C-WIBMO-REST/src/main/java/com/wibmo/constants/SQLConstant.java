/**
 * 
 */
package com.wibmo.constants;

/**
 * 
 */
public class SQLConstant {
	//Admin's Queries
	public static final String VIEW_COURSE_QUERY = "SELECT courseCode, courseName, professorId,seats,courseFee FROM course";
	public static final String APPROVE_STUDENT_QUERY = "UPDATE Student SET isApproved = 1 WHERE studentId = ?";
	
	public static final String APPROVE_ALL_STUDENTS_QUERY = "UPDATE student SET isApproved = 1";
	public static final String REMOVE_COURSE_QUERY = "DELETE FROM Course WHERE courseCode = ?";
	public static final String ADD_COURSE_QUERY = "INSERT INTO Course(courseCode, courseName, seats, professorId,courseFee) values (?, ?, ?, ?, ?)";
	public static final String ASSIGN_COURSE_QUERY = "UPDATE Course SET professorId = ? WHERE courseCode = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO User(userId, username, password, role, gender, address) values (?, ?, ?, ?, ?, ?)";
	public static final String GENERATE_REPORTCARD_QUERY = "SELECT * FROM course inner join registeredcourse ON course.courseCode = registeredcourse.courseCode WHERE registeredcourse.studentId = ?";
	public static final String VIEW_PENDING_ADMISSIONS_QUERY = "SELECT userId,username, password, role, gender, address, studentId FROM student, user WHERE isApproved = 0 AND studentId = userId";
	public static final String INSERT_PAYMENT_QUERY = "INSERT INTO payment(studentname,modeofPayment,amount,paymentstatus,referenceId) VALUES(?,?,?,?,?);";
	public static final String INSERT_NOTIFICATION_QUERY = "INSERT INTO notification(studentName,type,referenceId) VALUES(?,?,?);";
	public static final String SET_STUDENT_REPORT_GENERATION = "UPDATE student SET isReportGenerated = 1 WHERE studentId = ?";
	
	//Student's Queries
	public static final String CHECK_APPROVAL_STATUS = "SELECT isApproved FROM student WHERE studentId = ?";
	
	public static final String REGISTER_USER_QUERY = "INSERT INTO user values(?,?,?,?,?,?)";
	public static final String REGISTER_STUDENT_QUERY = "INSERT INTO student (studentID,gradYear,dept,isApproved,isReportGenerated,isRegistered,isPaid) values (?,?,?,?,?,?,?)";
	public static final String ISAPPROVED_QUERY = "SELECT isApproved FROM student JOIN user ON user.userId = student.studentId WHERE username = ? ";
	
	public static final String GET_REGISTRATION_STATUS=" SELECT isRegistered FROM student JOIN user ON user.userId = student.studentId WHERE username = ? ";
	public static final String SET_REGISTRATION_STATUS="UPDATE student JOIN user ON user.userId = student.studentId AND username = ? SET isRegistered = true";
	public static final String GET_PAYMENT_STATUS=" SELECT isPaid FROM student JOIN user ON user.userId = student.studentId WHERE username = ?";
	public static final String SET_PAYMENT_STATUS="UPDATE student JOIN user ON user.userId = student.studentId AND username = ? SET isPaid = true ";
	public static final String NUMBER_OF_REGISTERED_COURSES=" SELECT studentId FROM registeredcourse JOIN user ON user.userId = registeredCourse.studentId WHERE username =? ";
	public static final String IS_REGISTERED=" SELECT courseCode FROM registeredcourse JOIN user ON user.userId = registeredCourse.studentId WHERE username = ? AND courseCode=?";
	public static final String VIEW_REGISTERED_COURSES=" SELECT * FROM course INNER JOIN registeredcourse ON course.courseCode = registeredcourse.courseCode JOIN user ON user.userId = registeredCourse.studentId WHERE username = ?";
	
	public static final String VIEW_AVAILABLE_COURSES=" SELECT * FROM course WHERE courseCode NOT IN  (SELECT courseCode  FROM registeredcourse JOIN user ON user.userId = registeredCourse.studentId WHERE username = ? "
			+ "UNION SELECT courseCode  FROM secondarycourse JOIN user ON user.userId = secondarycourse.studentId WHERE username = ?) AND seats > 0";
	public static final String ADD_SECONDARY_COURSE = "INSERT INTO secondarycourse (studentId,courseCode) VALUES ( ? , ?)";
	public static final String GET_SECONDARY_COURSES = "SELECT courseCode FROM secondarycourse JOIN user ON user.userID = secondaryCourse.studentID WHERE user.username = ?";
	public static final String DROP_SECONDARY_COURSE = "DELETE secondarycourse FROM secondarycourse  JOIN user ON user.userId = secondarycourse.studentId WHERE username = ? AND courseCode = ?";
	public static final String NUMBER_OF_SECONDARY_COURSES = "SELECT studentId FROM secondarycourse JOIN user ON user.userId = secondarycourse.studentId WHERE username =?";
	
	public static final String CHECK_COURSE_AVAILABILITY=" SELECT courseCode from registeredcourse JOIN user ON user.userId = registeredCourse.studentId WHERE username = ?";
	public static final String DECREMENT_COURSE_SEATS="UPDATE course SET seats = seats-1 WHERE courseCode = ? ";
	public static final String ADD_COURSE="INSERT INTO registeredcourse (studentId,courseCode,grade) VALUES ( ? , ?, ?)";
	public static final String DROP_COURSE_QUERY = "DELETE registeredcourse FROM registeredcourse  JOIN user ON user.userId = registeredCourse.studentId WHERE username = ? AND courseCode = ?";
	public static final String INCREMENT_SEAT_QUERY  = "UPDATE course SET seats = seats + 1 WHERE  courseCode = ?";
	public static final String CALCULATE_FEES  = "SELECT sum(courseFee) FROM course WHERE courseCode IN (SELECT courseCode FROM registeredcourse JOIN user ON user.userId = registeredCourse.studentId WHERE username = ?);";
	public static final String VIEW_GRADE = "SELECT course.courseCode,course.courseName,registeredcourse.grade FROM course inner join registeredcourse on course.courseCode = registeredcourse.courseCode  JOIN user ON user.userId = registeredCourse.studentId WHERE username = ?;";	
	public static final String GET_SEATS = "SELECT seats FROM course WHERE courseCode = ?;";
	public static final String GET_GENERATED_REPORT_CARD_TRUE = "SELECT isReportGenerated FROM student JOIN user ON user.userId = student.studentId WHERE username = ?";
	
	//Professor DDL Queries
	public static final String VIEW_STUDENTLIST_QUERY = "SELECT course.courseCode,course.courseName,registeredcourse.studentId FROM course "
			+ "inner join registeredcourse ON course.courseCode = registeredcourse.courseCode "
			+ "WHERE course.professorId = ? ORDER BY course.courseCode";
	public static final String RECORD_GRADE_QUERY = "UPDATE registeredcourse SET GradeConstant=? WHERE courseCode=? AND studentId=?";
	public static final String ADD_PROFESSOR_QUERY = "INSERT INTO Professor(professorID, dept, designation) VALUES (?, ?, ?)";
	public static final String VIEW_PROFESSOR_QUERY = "SELECT userId, username, gender, dept, designation, address FROM Professor natural join User WHERE userId = professorId";
	public static final String CHECK_PROFESSOR_QUERY = "SELECT professorId FROM PROFESSOR WHERE professorId = ?";
	public static final String GET_COURSES="SELECT * FROM course JOIN user ON user.userId=course.professorID WHERE username=?";
	public static final String GET_ENROLLED_STUDENTS="SELECT course.courseCode,course.courseName,registeredcourse.studentId FROM course INNER JOIN registeredcourse ON course.courseCode = registeredcourse.courseCode JOIN user ON course.professorID = user.userID WHERE user.username = ? order by course.courseCode";
	public static final String ADD_GRADE="UPDATE registeredcourse SET Grade=? WHERE courseCode=? AND studentId=?";
	
	
	//User DDL Queries
	public static final String VERIFY_CREDENTIALS = "SELECT password, role FROM user WHERE username = ?";
	public static final String UPDATE_PASSWORD="UPDATE user SET password=? WHERE username = ? ";
	public static final String GET_ROLE="SELECT role FROM user WHERE username = ?;";
	public static final String GET_USER_ID = "SELECT userID FROM user WHERE username = ?";
	public static final String GET_USER_NAME = "SELECT username FROM user WHERE userID = ?";
	

}
