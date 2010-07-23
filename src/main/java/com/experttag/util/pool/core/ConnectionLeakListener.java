/*
 * @(#) ConnectionLeakListener 1.0 02/08/01
 */


package com.experttag.util.pool.core;

/**
 * This interface defines the behavior of the class listenening to connection 
 * leak events. A class requiring to listen to connection leaks must implement 
 * this interface.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

public interface ConnectionLeakListener {

	/**
	 * This method is called when a connection leak is detected.
	 * When ever a connection is blocked by the consumer for more than
	 * a then the specified time, a connection leak is said to have occurred. 
	 */
	public void connectionTimeOut(ConnectionLeakEvent cle);

}
