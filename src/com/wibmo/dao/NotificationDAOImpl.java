/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;

/**
 *
 */
public class NotificationDAOImpl implements NotificationDAOInterface{
	private static Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	private static volatile NotificationDAOImpl instance=null;
	/**
	 * Default Constructor
	 */
	private NotificationDAOImpl(){}
	
	/**
	 * Method to make NotificationDaoImpl Singleton
	 * @return instance
	 */
	public static NotificationDAOImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(NotificationDAOImpl.class){
				instance=new NotificationDAOImpl();
			}
		}
		return instance;
	}
	
	Connection connection=DBUtils.getConnection();
	PreparedStatement ps = null;
	
	@Override
	public void sendNotification(NotificationTypeConstant type, String studentName,PaymentModeConstant modeOfPayment,double amount) {
		try
		{
			ps = connection.prepareStatement(SQLConstant.INSERT_NOTIFICATION_QUERY);
			ps.setString(1, studentName);
			ps.setString(2,type.toString());
			if(type==NotificationTypeConstant.PAID)
			{
					UUID referenceId=addPayment(studentName, modeOfPayment,amount);
					ps.setString(3, referenceId.toString());
			}
			else
				ps.setString(3,"");
				
			ps.executeUpdate();
			switch(type)
			{
			case REGISTERATION:
				logger.info("Registration successfull. Administration will verify the details and approve it!");
				break;
			case APPROVED:
				logger.info("Student with name "+studentName+" has been approved!");
				break;
			case PAID:
				logger.info("Student with name "+studentName+" has paid the fee!");
			}
			
		}
		catch(SQLException ex)
		{
			logger.error(ex.getMessage());
		}
	}

	/**
	 * Method to perform payment
	 * @param studentName
	 * @param modeOfPayment
	 * @param amount 
	 * @return reference id of the transaction
	 * @throws SQLException
	 */
	public UUID addPayment(String studentId, PaymentModeConstant modeOfPayment,double amount) throws SQLException
	{
		UUID referenceId = null;
		try
		{
			referenceId=UUID.randomUUID();
			ps = connection.prepareStatement(SQLConstant.INSERT_PAYMENT_QUERY);
			ps.setString(1, studentId);
			ps.setString(2, modeOfPayment.toString());
			ps.setDouble(3, amount);
			ps.setString(4, "PAID");
			ps.setString(5, referenceId.toString());
			ps.executeUpdate();
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return referenceId;
	}
	
}
