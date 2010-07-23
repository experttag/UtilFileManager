/*
 * @(#) ConfigMonitor 1.0 02/08/01
 */

package com.experttag.util.pool.core;

/**
 * This interface defines the behavior of the class used to monitor the
 * configuration of a pool. This interface defines the methods which can
 * be used to monitor the runtime status of the pool configuration.
 *
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */


public interface ConfigMonitor {

	/**
	 * @return Name of the pool.
	 */
	public String getMultiPoolName();

	/**
	 * @return Max connections allowed in the pool.
	 */
	public  int getMaxConnections();

	/**
	 * @return Minimun connections in the pool.
	 */
	public int getMinConnections();
	
	/**
	 * @return Size of blocks of connections withdrawn at a time when no free
	 * 			connections are available.
	 */
	public int getIncrement();
	
	/**
	 * @return Username to connect to the database.
	 */
	public String getUserName();
	
	/**
	 * @return Password to connect to the database.
	 */
	public String getPassword() ;
	
	/**
	 * @return Connection string to connect to the database.
	 */
	public PoolConfig.ConnectionString[] getConnectionString();

	/**
	 * @return Driver name used to connect to database.
	 */
	public String getDriver();
	
	/**
	 * @return True if connction leak monitoring is enabled.
	 */
	public boolean isDetectLeaks();
	
	/**
	 * @return True if this pool is the default pool.
	 */ 
	public boolean isDefaultPool();

	/**
	 * @return Time out for detecting leaks.
	 */
	public long getLeakTimeOut();
	
	/**
	 * @return Default listener class for connection leak.
	 */
	public String getDefaultListener();
	
	/**
	 * @return Poll time interval for thread detecting leaks and managing 
	 * the pool size.
	 */
	public long getPollThreadTime();

	/**
	 * @return True if automotic closing of Statement, PreparedStatement,
	 * CallableStatement is enabled.
	 */
	public boolean isAutoClose();
	
	/**
	 * @return True if anonymous connections are allowed, i.e 
	 * without specifying the owner.
	 */
	public boolean isAllowAnonymousConnections();
	
	/**
	 * @return Maximum number of free connections allowed after which 
	 * excessive connections are released.
	 */
	public int  getMaxConnectionsForRelease();

	/**
	 * @return The maximum waiting period for a thread to get a Connection. 
	 * After this time interval, and ConnectionPoolException is thrown.
	 * 
	 */
	public long getConnectionWaitTimeOut();

	/**
	 * @return The Connection loader class when external pooling is enabled.
	 */
	public PoolConfig.ConnectionLoaderClass[] getConnectionLoaderClass();

	/**
	 * This method returns the validatorQuery. This query is run each time to
	 * check for its validity before the connection is leased out.
	 *
	 * @return validatorQuery 
	 */
	public String getValidatorQuery(); 

  /**
	 * This method returns the max-connection-idle-time value.
	 */ 
	public long getMaxConnectionIdleTime();


    /**
     * This method returns the thread stickiness value
     * @return
     */
    public boolean isThreadStickiness();


    /**
     * This method sets the thread stickiness value
     * @return
     */
    public void setThreadStickiness(boolean threadStickiness);
  
}
