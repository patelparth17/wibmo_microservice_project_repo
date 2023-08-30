/**
 * 
 */
package com.wibmo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.User;

/**
 * 
 */
@Repository
public interface UserRepository extends CrudRepository<User, String>{

	Optional<User> findByUsername(String username);
	
	User findByUserID(String userID);

	@Modifying
	@Transactional
	@Query(value="UPDATE User SET password=?2 WHERE username = ?1",nativeQuery=true)
	void updatePassword(String userName, String newPassword);
}
