/**
 * 
 */
package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.Professor;


@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {

	public Optional<Professor> findByProfessorID(String professorID);
	
	
}
