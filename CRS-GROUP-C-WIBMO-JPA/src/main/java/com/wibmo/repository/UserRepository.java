/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.User;

/**
 * 
 */
@Repository
public interface UserRepository extends CrudRepository<User, String>{

	User findByUsername(String username);

}
