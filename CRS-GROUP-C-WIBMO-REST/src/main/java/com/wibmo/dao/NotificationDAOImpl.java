/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLConstant;
import com.wibmo.utils.DBUtils;
import org.apache.log4j.Logger;

/**
 *
 */
@Repository
public class NotificationDAOImpl implements NotificationDAOInterface{
	
	@Autowired
	private static Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	
	Connection connection=DBUtils.getConnection();
	
	@Override
	public void sendNotification(NotificationTypeConstant type, String studentName,PaymentModeConstant modeOfPayment,double amount) {
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQLConstant.INSERT_NOTIFICATION_QUERY);
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
				logger.debug("Registration successfull. Administration will verify the details and approve it!");
				break;
			case APPROVED:
				logger.debug("Student with id "+studentName+" has been approved!");
				break;
			case PAID:
				logger.debug("Student with id "+studentName+" fee has been paid");
			}
			
		}
		catch(SQLException ex)
		{
//			logger.error(ex.getMessage());
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
			PreparedStatement statement = connection.prepareStatement(SQLConstant.INSERT_PAYMENT_QUERY);
			statement.setString(1, studentId);
			statement.setString(2, modeOfPayment.toString());
			statement.setDouble(3, amount);
			statement.setString(4, "PAID");
			statement.setString(5, referenceId.toString());
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return referenceId;
	}
	
}
