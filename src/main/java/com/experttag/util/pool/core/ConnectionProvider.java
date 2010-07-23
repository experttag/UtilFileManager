/*
 * @(#) ConnectionProvider 1.0 02/08/01
 */
package com.experttag.util.pool.core;

import java.sql.*;

/**
 * This interface is used to wrap the SmartPool to an existing connection pool.
 *  
 * <p>To wrap the SmartPool to an existing pool, provide an instance of 
 * this interface in the configuration file. SmartPool will then draw 
 * connection from this pool rather then drawing raw connections to the 
 * pool. </p>
 *
 * <p>Also if the SmartPool is wrapped around another pool
 * it will draw a connection on every call by calling 
 * ConnectionProvider.getConnection().</p>
 *
 * <p>Similarly when you call Connection.close(), SmartPool returns the 
 * connection back to the original pool immediately by calling 
 * ConnectionProvider.returnConnection().</p> 
 *
 * <p>In short when this feature is used, SmartPool is just another layer of 
 * indirection. SmartPool does not maintain any pool. This has been 
 * specifically designed to do so. This is because the external pool to which
 * SmartPool is wrapped is expected to do all the pooling. SmartPool can be 
 * used to detect leaks and monitor live connections etc. <p>
 *
 */
  
/* Note from the Author: I realized this when I tested this feature against an
 * Application Server DataSource pool and to my surprise (I call my self
 * a J2EE expert :) ) I discovered that the application server requires the
 * connection to be returned to the pool.
 * The Application Server manages the state of the connections and thus it is 
 * imperative to return the connection back to the Application Server after 
 * each use. 
 */

public interface ConnectionProvider {
	
	/**
	 * <p>This method implementation should fetch the connection.
	 * This method is expected to block if a connection is not available untill
	 * a connection a available.</p>
	 *
	 * <p>Whatever preprocessing you want to do, do it here. SmartPool will not 
	 * manuplate the connection state in anyway.</p>
	 * e.g. of preprocessing:<br> 
	 * <li>		1. Testing if the connection is valid.
	 * <li>		2. con.setAutoCommit(false|true)
	 * 
	 * <p>If you throw any Exception while doing this, SmartPool will wrap this 
	 * exception with ConnectionPoolException and throw it back to you in the 
	 * SmartPoolFactory.getConnection() method. So better behave your self and 
	 * handle all the cases here itself.</p>
	 *
	 *  
	 */
	public Connection getConnection() throws Exception;

	/**
	 *<p>This method implementation should release the connection.</p>	 
	 * 
	 * <p>Whatever postprocessing you want to do, do it here.</p>
	 * e.g. of postprocessing<br> 
	 * <li>		1. if (!conn.getAutoCommit()) conn.commit();
	 * 	
	 * <p>If you throw any Exception while doing this, SmartPool will wrap this 
	 * exception with SQLException and throw it back to you in the 
	 * Connection.close() method. So better behave your self and 
	 * handle all the cases here itself.</p>
	 * 	
	 */
	public void returnConnection(Connection conn) throws Exception;

}

