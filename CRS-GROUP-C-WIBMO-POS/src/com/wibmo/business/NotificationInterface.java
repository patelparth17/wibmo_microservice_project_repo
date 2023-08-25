/**
 *
 */
package com.wibmo.business;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;


/**
 *
 */
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