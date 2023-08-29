/**
 * 
 */
package com.wibmo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */

@Entity
@Table(name="notification")
public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="notificationID")
	private int notificationID;
	
	@Column(name="studentName")
	private String studentName;
	
	@Column(name="type")
	private String notificationType;
	
	@Column(name="referenceID")
	private String referenceID;

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}
}
