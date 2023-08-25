/**
 * 
 */
package com.wibmo.bean;

import java.io.Serializable;

import com.wibmo.constants.GradeConstant;
/**
* Bean class for Registered course
*/
public class RegisteredCourse implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Course course;
    String studentId;
    GradeConstant grade;


    /**
     * Method to get the course details
     * @return course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Method to set the course details
     * @param course
     */
    public void setCourse(Course course) {
        this.course = new Course(course.getCourseCode(), course.getCourseName(), course.getInstructorId() ,course.getSeats(), course.getFee());
    }

    /**
     * Method to get the student ID
     * @return studentId
     */
    public String getstudentId() {
        return studentId;
    }

    /**
     * Method to set the student ID
     * @param studentId 
     */
    public void setstudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Method to get the grade of respective course
     * @return grade
     */
    public GradeConstant getGrade() {
        return grade;
    }

    /**
     * Method to set the grade of respective course
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = GradeConstant.valueOf(grade);
    }
}