/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.model.Notification;

/**
 * 
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
	
}
