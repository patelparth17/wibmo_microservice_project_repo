/**
 * 
 */
package com.wibmo.security;


import com.wibmo.model.User;
import com.wibmo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Set<SimpleGrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority("Role." + user.get().getRole()));
		return new org.springframework.security.core.userdetails.User(user.get().getusername(), user.get().getPassword(), roles);
	}

	public User save(User user) {
		User newUser = new User();
		newUser.setusername(user.getusername());
		newUser.setPassword(user.getPassword());
		newUser.setRole(user.getRole());
		userRepo.save(newUser);
		return newUser;
	}
}