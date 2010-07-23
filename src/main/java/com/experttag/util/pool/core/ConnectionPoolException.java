/*
 * @(#) ConnectionPoolException.java 1.0 02/08/01
 */
package com.experttag.util.pool.core;

import java.sql.SQLException;
/**
 * This class extends the Exception class and encapsulates any other exception.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */

public class ConnectionPoolException extends SQLException {

	private Throwable previousException = null;
	protected String  message = null;
	private Throwable nextException;
    Debugger debug = 
		new Debugger("org.smartlib.pool.core.ConnectionPoolException", true);

	  
	ConnectionPoolException() {

		super();
		
	}

	
	ConnectionPoolException(String messageId){
	
		super(messageId);
		message = messageId;
        debug.print("Exception Created:" + messageId);
		
	}

	ConnectionPoolException(String messageId ,Throwable prevException) {
	
		super(messageId);
		if((prevException instanceof ConnectionPoolException)
				||(message==null))
       message = messageId;
		this.previousException = prevException;
       debug.print("Exception Created: " + messageId);
       debug.writeException(this);
        
	}
	
	Throwable getPreviousException()	{
	
		return previousException;

	}

	/**
	 * This method prints the stack trace to System.err. It prints the 
	 * entire stackTrace of all the previous exceptions that it has 
	 * encapsulated.
	 */
	public void printStackTrace() {
	
		super.printStackTrace();
		if (previousException != null) {
			System.err.println("Caused by Prev: ");
			previousException.printStackTrace();
		}
		if (nextException != null) {
			System.err.println("Caused by Next: ");
			nextException.printStackTrace();
		}

	}	

	/**
	 * This method prints the stack trace to <code>ps</code>. It prints the 
	 * entire stackTrace of all the previous exceptions that it has 
	 * encapsulated.
	 * 
	 * @param ps PrintStream into which the stack trace will be written.
	 */
	public void printStackTrace(java.io.PrintStream ps)	{

		if(ps == null) {
			System.err.println("PrintStream passed to printStackTrace method is null.");
			printStackTrace();
		}
		super.printStackTrace(ps);
		if (previousException != null) {
			ps.println("Caused by:");
			previousException.printStackTrace(ps);
		}

	}

	/**
	 * This method returns the message id. 
	 */
	public String getMessageId() {
	
		if(message==null)
			return null;
		int index = message.indexOf(":");
		int len = message.length();

		if(index<=0)
			return null;
		return message.substring(0,index);

	}
	
	String getRootMessageId()  {
		ConnectionPoolException exp = null;
		Throwable prevExp = this;
		while((prevExp!=null)&&(prevExp instanceof ConnectionPoolException)) {
			exp  = (ConnectionPoolException)prevExp;
			prevExp = exp.previousException;
		}
		if(exp == null)
			return null;
		String message = exp.message;
		if(message==null)
			return null;
		int index = message.indexOf(":");
		int len = message.length();

		if(index<=0)
			return null;
		return message.substring(0,index);
		
	}
	

	/**
	 * This method prints the stack trace to <code>pw</code>. It prints the 
	 * entire stackTrace of all the previous exceptions that it has 
	 * encapsulated.
	 * 
	 * @param pw PrintWriter into which the stack trace will be written.
	 */
	public void printStackTrace(java.io.PrintWriter pw)	{
	
		if(pw == null) {
			System.err.println("PrintWriter passed to printStackTrace method is null.");
			printStackTrace();
		}
		super.printStackTrace(pw);
		if (previousException != null) {
			pw.println("Caused by:");
			previousException.printStackTrace(pw);
		}
		
	}
	

}
