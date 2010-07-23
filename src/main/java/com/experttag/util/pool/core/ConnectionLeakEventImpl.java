/*
 * @(#) ConnectionLeakEventImpl.java 1.0 02/08/01
 */

package com.experttag.util.pool.core;

import java.sql.*;

/**
 * This class provides an implementation of the ConnectionLeakEvent interface
 * thus encapsulating a connection leak event.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

public class ConnectionLeakEventImpl implements ConnectionLeakEvent {

	private Connection conn;
	private String owner;
	private long lastAccessedTime;
	private long connectionObtainedTime;
	private String poolName;

	/**
	 * @param conn Connection for which the time out has occured.
	 * @param owner Owner of the connection.
	 * @param lastAccessedTime Timestamp when the connection was last accessed 
	 *			directly or through Statements, PreparedStatements, 
	 *			CallableStatements.
	 * @param connectionObtainedTime Timestamp when the connection was obtained 
	 *		from the pool.	 
	 * @param poolName Name of the pool to which the connection belongs.
	 */
	ConnectionLeakEventImpl(Connection conn, String owner,
					long lastAccessedTime, long connectionObtainedTime, 
					String poolName) {
		
		this.conn = conn;
		this.owner= owner;
		this.lastAccessedTime = lastAccessedTime;
		this.connectionObtainedTime = connectionObtainedTime;
		this.poolName = poolName;
	
	}

	/**
	 * @return Connection which has been blocked by the consumer for more 
	 * than the specified time.
	 */
	public Connection getConnection() {
		
		return conn;
		
	}

	/**
	 * @return Owner of the connection.
	 */
	public String getOwner() {

		return owner;

	}

	/**
	 * @return Timestamp when the connection was last accessed.
	 */
	public long getLastAccessedTime() {

		return lastAccessedTime;

	}

	/**
	 * @return Timestamp when connection was drawn from the pool.
	 */
	public long getConnectionObtainedTime() {

		return connectionObtainedTime;
		
	}

	/**
	 * @return Name of the pool.
	 */
	public String getPoolName() {

		return poolName;
		
	}
	// toString method , makes debugging easy.
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("\nConnectionLeakEvent--------->");
		sb.append("\n\tConnection: " + conn);
		sb.append("\n\tOwner: " + owner);
		sb.append("\n\tlastAccessedTime:  " + lastAccessedTime  );
		sb.append("\n\tconnectionObtainedTime :  " + connectionObtainedTime );
		sb.append("\n\tpoolName :  " + poolName );
		return (sb.toString());	

	}
	
}
