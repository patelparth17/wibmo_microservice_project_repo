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
     * @param type: type of the notification to be sent
     * @param studentId: student to be notified
     * @param modeOfPayment: payment mode used
     * @return notification id for the record added in the database
     */
    public void sendNotification(NotificationTypeConstant type,String studentId,PaymentModeConstant modeOfPayment,double amount);

}