/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.model.Course;
import com.wibmo.repository.ProfessorRepository;

/**
 * 
 */
@Service
public class ProfessorService implements ProfessorInterface {

	@Autowired 
	ProfessorRepository professorRepo;
	
	//Method to get the courses for the professor
	@Override
	public List<Course> viewCourses(String username) {
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered = professorRepo.getCoursesByProfessor(username);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return coursesOffered;
	}
}
