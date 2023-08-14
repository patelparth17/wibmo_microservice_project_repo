package com.wibmo.constants;

/**
 * 
 * @author parth.patel
 * Enumeration class for Mode of Payments
 *
 */
public enum PaymentModeConstant {
	
	CREDIT_OR_DEBIT_CARDS,NET_BANKING,UPI;
	
	/**
	 * Method to get Mode of Payment
	 * @param value
	 * @return Mode of Payment
	 */
	public static PaymentModeConstant getPaymentMode(int value)
	{
		switch(value)
		{
			case 1:
				return PaymentModeConstant.CREDIT_OR_DEBIT_CARDS;
			case 2:
				return PaymentModeConstant.NET_BANKING;
			case 3:
				return PaymentModeConstant.UPI;
			default:
				return null;
				
		}
			
	}
	
}