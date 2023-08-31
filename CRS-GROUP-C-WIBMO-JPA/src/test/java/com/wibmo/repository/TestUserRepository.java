/**
 * 
 */
package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.exception.UserNotFoundException;
import com.wibmo.model.User;
import com.wibmo.service.UserService;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
public class TestUserRepository {
	
	@InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        User user = new User("11", "Samyak", "samyak", "PROFESSOR", "Maharashtra", "MALE");
        when(userRepository.findByUserID("11")).thenReturn(user);
        String result = userService.findUserName("11");
        assertEquals("Samyak", result);
    }
    
    @Test
    void updatePasswordTest() throws UserNotFoundException
    {
        User user = new User();
        user.setusername("veda");
        user.setPassword("pass");
        List<User> users = new ArrayList<User>();
        users.add(user);
        when(userService.findByUsername("veda")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).updatePassword("veda", "pass");
        userService.updatePassword("veda", "pass");
        verify(userRepository,times(1)).updatePassword("veda","pass");

    }
}
