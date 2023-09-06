/**
 * User service test cases
 * @author vedasree
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.exception.PasswordAlreadyInUseException;
import com.wibmo.exception.StudentNotApprovedException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.Student;
import com.wibmo.model.User;
import com.wibmo.repository.NotificationRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	UserRepository userRepo;
	
	@Mock
	StudentRepository studentRepo;
	
	@Mock
	NotificationRepository notificationRepo;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void findUserNameTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");
		when(userRepo.findByUserID("1")).thenReturn(user);
		assertEquals("Veda", userService.findUserName("1"));
	}
	
	@Test
	public void addUserTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");		
		try {
			userService.addUser(user);
		} catch (UserNotAddedException e) {
		}
		verify(userRepo, times(1)).save(user);
	}
	
	@Test
	public void authenticateUserTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		try {
			assertTrue(userService.authenticateUser("Veda", "passwd", "STUDENT"));
		} catch (UserNotFoundException e) {
		}
	}
	
	@Test
	public void updatePasswordTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		try {
			userService.updatePassword("Veda", "password");
		} catch (UserNotFoundException | PasswordAlreadyInUseException e) {
		}
		verify(userRepo, times(1)).updatePassword("Veda", "password");
	}
	
	@Test
	public void getApprovalStatusTest() {
		User user = new User("1","Veda","STUDENT","passwd","Female","AP");	
		when(userRepo.findByUsername("Veda")).thenReturn(Optional.of(user));
		when(studentRepo.getApprovalStatus("1")).thenReturn(1);
		try {
			assertEquals(1, userService.getApprovalStatus("Veda"));
		} catch (UserNotFoundException | StudentNotApprovedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void registerTest() {
		Student student = new Student("1","Veda","Student","passwd","Female","AP",
				"CSE",2023,false,false,false,false);
		
		try {
			userService.addUser(student);
		} catch (UserNotAddedException e) {
		}
		verify(userRepo, times(1)).save(student);
	}
	
}
