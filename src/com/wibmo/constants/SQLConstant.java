/**
 * 
 */
package com.wibmo.constants;

/**
 * 
 */
public class SQLConstant {
	//Admin
	public static final String VIEW_COURSE_QUERY = "select courseCode, courseName, professorId from course";
	public static final String APPROVE_STUDENT_QUERY = "update Student set isApproved = 1 where studentId = ?";
	public static final String REMOVE_COURSE_QUERY = "delete from Course where courseCode = ?";
	public static final String ADD_COURSE_QUERY = "insert into Course(courseCode, courseName, seats, professorId) values (?, ?, ?, ?)";
	public static final String ASSIGN_COURSE_QUERY = "update Course set professorId = ? where courseCode = ?";
	public static final String ADD_USER_QUERY = "insert into User(userId, name, password, role, gender, address) values (?, ?, ?, ?, ?, ?)";
	public static final String GENERATE_REPORTCARD_QUERY = "select * from course inner join registeredcourse on course.courseCode = registeredcourse.courseCode where registeredcourse.studentId = ?";
	
	//Student
	public static final String REGISTER_USER_QUERY = "insert into user values(?,?,?,?,?,?)";
	public static final String REGISTER_STUDENT_QUERY = "insert into student (userID,gradYear,dept,isApproved) values (?,?,?,?)";
	public static final String ISAPPROVED_QUERY = "select isApproved from student where studentId = ? ";
	
	//Professor
	public static final String VIEW_STUDENTLIST_QUERY = "select course.courseCode,course.courseName,registeredcourse.studentId from course "
			+ "inner join registeredcourse on course.courseCode = registeredcourse.courseCode "
			+ "where course.professorId = ? order by course.courseCode";
	public static final String RECORD_GRADE_QUERY = "update registeredcourse set GradeConstant=? where courseCode=? and studentId=?";
	
	//User
}
