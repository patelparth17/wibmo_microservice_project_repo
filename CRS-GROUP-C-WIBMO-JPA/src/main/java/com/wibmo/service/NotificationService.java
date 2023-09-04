/**
 * 
 */
package com.wibmo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.model.Notification;
import com.wibmo.repository.NotificationRepository;

/**
 * 
 */

@Service
public class NotificationService implements NotificationInterface{

	@Autowired
	NotificationRepository notificationRepo;
	
	@Autowired
	PaymentService paymentService;
	
	@Modifying
	@Transactional
	public void sendNotification(NotificationTypeConstant type, String name) {
		String referenceID = "";
		if(type==NotificationTypeConstant.PAID)
		{
			referenceID=paymentService.findByStudentName(name);
		}
		Notification notification = new Notification();
		notification.setNotificationType(type.toString());
		notification.setReferenceID(referenceID);
		notification.setStudentName(name);
		
		notificationRepo.save(notification);
	}
}
