/**
 *
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;


/**
 *
 */
@Service
public interface NotificationInterface {

    /**
     * Method to send notification
     * @param type
     * @param studentId
     * @param modeOfPayment
     * @param amount
     */
    public void sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,double amount);

}