/**
 * 
 */
package com.wibmo.constants;

/**
 * 
 */
public class SQLConstant {
	//Admin
	public static final String VIEW_COURSE_QUERY = "SELECT courseCode, courseName, professorId FROM course";
	public static final String APPROVE_STUDENT_QUERY = "UPDATE Student SET isApproved = 1 WHERE studentId = ?";
	public static final String REMOVE_COURSE_QUERY = "DELETE FROM Course WHERE courseCode = ?";
	public static final String ADD_COURSE_QUERY = "INSERT INTO Course(courseCode, courseName, seats, professorId) values (?, ?, ?, ?)";
	public static final String ASSIGN_COURSE_QUERY = "UPDATE Course SET professorId = ? WHERE courseCode = ?";
	public static final String ADD_USER_QUERY = "INSERT INTO User(userId, name, password, role, gender, address) values (?, ?, ?, ?, ?, ?)";
	public static final String GENERATE_REPORTCARD_QUERY = "SELECT * FROM course inner join registeredcourse ON course.courseCode = registeredcourse.courseCode WHERE registeredcourse.studentId = ?";
	public static final String VIEW_PENDING_ADMISSIONS_QUERY = "SELECT userId,name, password, role, gender, address, studentId FROM student, user WHERE isApproved = 0 AND studentId = userId";
	public static final String INSERT_PAYMENT_QUERY = "INSERT INTO  payment(studentId,modeofPayment,referenceId,amount) VALUES(?,?,?,?);";
	public static final String INSERT_NOTIFICATION_QUERY = "INSERT INTO notification(studentId,type,referenceId) VALUES(?,?,?);";
	
	//Student
	public static final String REGISTER_USER_QUERY = "INSERT INTO user values(?,?,?,?,?,?)";
	public static final String REGISTER_STUDENT_QUERY = "INSERT INTO student (userID,gradYear,dept,isApproved) values (?,?,?,?)";
	public static final String ISAPPROVED_QUERY = "SELECT isApproved FROM student WHERE studentId = ? ";
	
	//Professor
	public static final String VIEW_STUDENTLIST_QUERY = "SELECT course.courseCode,course.courseName,registeredcourse.studentId FROM course "
			+ "inner join registeredcourse ON course.courseCode = registeredcourse.courseCode "
			+ "WHERE course.professorId = ? ORDER BY course.courseCode";
	public static final String RECORD_GRADE_QUERY = "UPDATE registeredcourse SET GradeConstant=? WHERE courseCode=? AND studentId=?";
	public static final String ADD_PROFESSOR_QUERY = "INSERT INTO Professor(professorId, department, designation) VALUES (?, ?, ?)";
	public static final String VIEW_PROFESSOR_QUERY = "SELECT userId, name, gender, department, designation, address FROM Professor natural join User WHERE userId = professorId";
	
	//User
	public static final String VERIFY_CREDENTIALS = "SELECT password, role FROM user WHERE username = ?";
	public static final String UPDATE_PASSWORD="UPDATE user SET password=? WHERE username = ? ";
	public static final String GET_ROLE="SELECT role FROM user WHERE username = ?;";
}
