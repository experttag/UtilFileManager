/*
 * @(#) ConnectionMonitor 1.0 02/08/01
 */

package com.experttag.util.pool.core;

/**
 * This interface defines the behavior of the class used to monitor 
 * a Pool. This interface defines the methods which can
 * be used to monitor the runtime status of the pools. 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */

import java.util.*;

public interface PoolMonitor {

	/**
	 * @return Current size of the pool.
	 */
	public int getCurrentPoolSize();

	/**
	 * @return Number of free connections in the pool
	 */
	public int getNoOfFreeConnections();

	/**
	 * @return Vector containing the connections that are currently in use.
	 */
	public Vector getConnectionsInUse();

	/**
	 * @return Instance of ConfigMonitor to examine the 
	 * configuration of the pool.
	 */
	public ConfigMonitor getConfigMonitor();

    /**
     * This method returns the name of the pool
     */
    public String getName();

}
