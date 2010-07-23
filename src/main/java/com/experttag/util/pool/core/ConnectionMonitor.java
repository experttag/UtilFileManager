/*
 * @(#) ConnectionMonitor 1.0 02/08/01
 */

package com.experttag.util.pool.core;

/**
 * This interface defines the behavior of the class used to monitor 
 * a Connection. This interface defines the methods which can
 * be used to monitor the runtime status of the Connection.  
 *
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */


public interface ConnectionMonitor {

	/**
	 * @return The owner of the connections, "N/A" if this is an
	 * anonymous connection.
	 */
    public String getOwner();

	/**
	 * @return Timestamp at which the connection was obtained from the pool. 
	 */
    public long getConnectionObtainedTime();

	/**
	 * @return Timestamp at which the connection was last accessed directly, 
	 * or through Statement, PreparedStatement, CallableStatement. 
	 */
    public long getLastAccessedTime();

	/**
	 * @return Whether the connection is used in a transaction. 
	 */
    public boolean isCurrentlyInTransaction();

    /**
     * returns the name of the pool that this connection belongs
     */
    public String getPoolName();

}
