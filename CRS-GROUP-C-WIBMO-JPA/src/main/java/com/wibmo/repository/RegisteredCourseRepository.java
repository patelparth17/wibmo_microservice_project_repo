package com.wibmo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.RegisteredCourse;

@Repository
public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse, String>{

	List<RegisteredCourse> findAllByCourseCode(String courseCode);

}
