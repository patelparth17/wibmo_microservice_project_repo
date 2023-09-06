/**
 * 
 */
package com.wibmo.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.wibmo.model.Notification;
import com.wibmo.constants.SQLConstant;

/**
 * 
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
	
	@Modifying
	@Transactional
	@Query(value=SQLConstant.ADD_NOTIFICATION, nativeQuery = true)
	public void addNotification(String name, String type, String referenceId);
}
