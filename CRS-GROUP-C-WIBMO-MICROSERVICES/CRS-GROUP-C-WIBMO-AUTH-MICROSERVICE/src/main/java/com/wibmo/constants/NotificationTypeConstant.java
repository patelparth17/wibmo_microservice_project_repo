/**
 * 
 */
package com.wibmo.constants;

/**
 * @author parth.patel
 *
 */
public enum NotificationTypeConstant

{

 REGISTERATION, APPROVED, PAID;

 

 public static NotificationTypeConstant stringToNotificationType(String val)

 {

 NotificationTypeConstant notificationType = NotificationTypeConstant.PAID;

 if(val.equalsIgnoreCase("REGISTERATION"))

 notificationType = NotificationTypeConstant.REGISTERATION;

 else if(val.equalsIgnoreCase("APPROVED"))

 notificationType = NotificationTypeConstant.APPROVED;

 else if(val.equalsIgnoreCase("other"))

 notificationType = NotificationTypeConstant.PAID;

 

 return notificationType;

 }

}