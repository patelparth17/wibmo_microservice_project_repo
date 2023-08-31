/**
 * 
 */
package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.model.Professor;
import com.wibmo.service.ProfessorService;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
public class TestProfessorRepository {
	
	@InjectMocks
	ProfessorService professorService;
	
	@Mock
	ProfessorRepository professorRepository;
	
	@Test
	void findProfessorExistence() {
		Professor newProfessor = new Professor();
		newProfessor.setProfessorID("18");
		newProfessor.setDepartment("CSE");
		newProfessor.setDesignation("HOD");
		newProfessor.setuserID("18");
		
		when(professorRepository.findByProfessorID("18")).thenReturn(Optional.of(newProfessor));
		boolean result = professorService.isProfessorExists("18");
		assertEquals(true, result);
	}

}
