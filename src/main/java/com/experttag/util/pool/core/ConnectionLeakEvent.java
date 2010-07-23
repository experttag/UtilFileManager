/*
 * @(#) ConnectionLeakEvent.java 1.0 02/08/01
 */

package com.experttag.util.pool.core;


/**
 * This method defines the behavior of the class encapsulting a connection 
 * leak event. When ever a connection is blocked by the consumer for more than
 * a then the specified time, a connection leak is said to have occurred. 
 * Information regarding this leak is encapsulated by an object whose behavior 
 * is defined by this interface.
 * 
 * @author Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

import java.sql.*;

public interface ConnectionLeakEvent {

	/**
	 * @return Connection which has been blocked by the consumer for more 
	 * than the specified time.
	 */
	public Connection getConnection();

	/**
	 * @return The owner of the connection.
	 */
	public String getOwner();

	/**
	 * @return Timestamp when the connection was accessed last time.
	 */
	public long getLastAccessedTime();

	/**
	 * @return Timestamp when connection was drawn from the pool.
	 */
	public long getConnectionObtainedTime();

	/**
	 * @return Name of the pool.
	 */
	public String getPoolName();

}
