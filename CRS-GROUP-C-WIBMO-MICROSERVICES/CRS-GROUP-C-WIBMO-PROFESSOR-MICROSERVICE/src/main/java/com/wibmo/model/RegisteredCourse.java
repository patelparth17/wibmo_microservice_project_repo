/**
 * 
 */
package com.wibmo.model;

 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
* 
*/

@Entity
@Table(name="registeredcourse")
public class RegisteredCourse implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
	Course course;
	
	@Id
	@Column(name="studentID")
    String studentId;
	
	@Column(name="grade")
    String grade;
	
	@Column(name="courseCode")
	String courseCode;

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
        this.course = new Course(course.getCourseCode(), course.getCourseName(), course.getProfessorID() ,course.getSeats(), course.getFee());
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
    public String getGrade() {
        return grade;
    }

    /**
     * Method to set the grade of respective course
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }
    

    public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
}