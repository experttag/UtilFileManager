/*
 * @(#) SmartPoolFactory	1.0 02/08/01
 */



package com.experttag.util.pool.core;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;

/**
 * <tt>SmartPoolFactory</tt> provides a Singleton interface to the 
 * implementation of the PoolManager Interface.
 * An object of <tt>SmartPoolFactory</tt> should be loaded with the 
 * configuration file prior to calling any static methods on the class.
 * 
 * Once a object is loaded it maintains a single instance of PoolManager 
 * across all objects and static methods can be directly invoked on the class
 * without creating any other instance of <tt>SmartPoolFactory<tt>.
 *
 * // Creating an instance .
 * <pre>
 *	SmartPoolFactory = new SmartPoolFactory(); 
 * </pre><p>
 *
 *
 *  // using <tt>SmartPoolFactory</tt> once it is initialised.
 *  <pre>
 *	 Connection conn = SmartPoolFactory.getConnection();	
 *  </pre><p>
 *
 *  It is <b>advisable</b> to load the <tt>SmartPoolFactory</tt> on your 
 *  application boot up (start-up-servlet in a web application), so that 
 *  it is available for other components from the word go.
 *
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */


public class SmartPoolFactory {


    private Logger logger = Logger.getLogger(SmartPoolFactory.class);

    //static for Singleton
	private static  PoolManager pm = null;

    private static boolean shutDown = false;

    private void startPool(File file) throws ConnectionPoolException {

		// maintaining Singleton
		if (pm == null)
			pm = new PoolManagerImpl(file);

	}

	/**
	 * This constructor intialises the SmartPoolClass , reads the 
	 * configuration from <code>file</code> and loads the PoolManger.
	 * 
	 * @param file The configuration file.
	 * @exception ConnectionPoolException if there is any problem 
	 *			initialising the pools
	 */
	public SmartPoolFactory(File file) throws ConnectionPoolException {
	
		if (pm != null)
			throw new ConnectionPoolException("This Class follows a Singleton Pattern. Instance has already been created and initialized.");	
		if (file == null )	
			throw new IllegalArgumentException("file cannot be null");
		startPool(file);
		
	}

    /**
     * This constructor intialises the SmartPoolClass , reads the
     * configuration from system property and loads the PoolManger.
     *
     * @exception ConnectionPoolException if there is any problem
     *			initialising the pools
     */
    public SmartPoolFactory() throws ConnectionPoolException {

        String fileName = System.getProperty(PoolConstants.CONFIG_FILE_SYSTEM_PROPERTY);
        if (logger.isDebugEnabled()) {
            logger.debug("Config File System Property: " + fileName);
        }

        if (pm != null && !shutDown)
            throw new ConnectionPoolException("This Class follows a Singleton Pattern. Instance has already been created and is active.");
        if (fileName == null && fileName.trim().length() != 0)
            throw new IllegalArgumentException("System Property:" + PoolConstants.CONFIG_FILE_SYSTEM_PROPERTY
                    + " cannnot be null for this constructor invocation, set it to location of the smart pool config file.");


        startPool(new File(fileName));

    }


    /**
	 * This constructor intialises the SmartPoolClass , reads the 
	 * configuration from <code>fileName</code> and loads the PoolManger.
	 * 
	 * @param file The absolute configuration file path.
	 * @exception ConnectionPoolException if there is any problem 
	 *			initialising the pools
	 */
	public SmartPoolFactory(String fileName) throws ConnectionPoolException	{
	
		if (pm != null)
			throw new ConnectionPoolException("This Class follows a Singleton Pattern. Instance has already been created and initialized.");	
		if (fileName == null || fileName.trim().equals(""))
			throw new IllegalArgumentException("filename cannot be null/empty");
		File file  = new File(fileName);	
		startPool(file);

	}

	private static void checkAndThrow() throws ConnectionPoolException {
		
		if (pm == null )
			throw new ConnectionPoolException("PoolManager is not initialised ,"
				+ "Please create an object first with the configuration");

	}

	/**
	 * This method returns a Connection from the default connection pool. 
	 * The owner of this pool is marked as N/A indicating unknown.
	 *
	 * <b>Note: This method blocks if the pool size has reached it's 
	 * maximum size and no free connections are available 
	 * until a free connection is available.</b> The time period for which this
	 * method blocks depends on the connection-wait-time-out specified in 
	 * the configuration file.
	 * 	 	 
	 * 
 	 * @return Connection from the default pool
	 * @exception ConnectionPoolException if there is any problem 
	 *		getting connection.	
	 */
	
	public static Connection getConnection() throws ConnectionPoolException {

		checkAndThrow();
		return 	pm.getConnection();
		
	}

	/**
	 * This method returns a Connection from the  pool <code>poolName</code>. 
	 * The owner of this pool is marked as N/A indicating unknown.
	 *
	 * <b>Note: This method blocks if the pool size has reached it's 
	 * maximum size and no free connections are available 
	 * until a free connection is available.</b> The time period for which this
	 * method blocks depends on the connection-wait-time-out specified in 
	 * the configuration file.
	 * 	 	 
	 *
	 * @param poolName Name of the pool.
 	 * @return Connection from the pool
	 * @exception ConnectionPoolException if there is any problem 
	 *		getting connection.	
	 */

	public static  Connection getConnection(String poolName)
		throws ConnectionPoolException {

		checkAndThrow();
		return pm.getConnection(poolName);

	}

	/**
	 * This method returns a Connection from the  pool <code>poolName</code>. 
	 * The owner of this connection is identified by <code>owner</code> .
	 *
	 * <b>Note: This method blocks if the pool size has reached it's 
	 * maximum size and no free connections are available 
	 * until a free connection is available.</b> The time period for which this
	 * method blocks depends on the connection-wait-time-out specified in 
	 * the configuration file.
	 * 	 	 
	 *
	 * @param poolName Name of the pool.
	 * @param owner String identifying the owner.
 	 * @return Connection from the pool
	 * 
	 * @exception ConnectionPoolException if there is any problem 
	 *		getting connection.	
	 */

	public static Connection getConnection(String poolName , String owner)
		throws ConnectionPoolException {

		checkAndThrow();
		return pm.getConnection(poolName,owner);

	}

	/**
	 * This method adds  a connection leak listener.The methods of 
	 * <code>cle</code> will be called when a leak is detected as per the
	 * pool configuration.
	 *
	 * @param poolName Name of the pool.
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException If there is any problem 
	 *	 adding ConnectionLeakListener.
	 */
	public static void addConnectionLeakListener(String poolName
			, ConnectionLeakListener cle)  throws ConnectionPoolException {

		checkAndThrow();
		pm.addConnectionLeakListener(poolName , cle );

	}

	/**
	 * This method removes a connection leak listener.<code>cle</code> will
	 * not get any  further notifications.
	 *
	 * @param poolName Name of the pool.
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException if there is any problem 
	 *	 removing ConnectionLeakListener.
	 */

	public static void removeConnectionLeakListener(String poolName
			, ConnectionLeakListener cle) throws ConnectionPoolException {

		checkAndThrow();
		pm.removeConnectionLeakListener(poolName,cle);
		
	}

	/**
	 * This method returns the instance of PoolMonitor for the pool 
	 * <code>poolName</code>
	 *
	 * @param poolName Name of the pool.
	 * @return Instance of PoolMonitor to monitor the state of the pool.
	 * @exception ConnectionPoolException 
	 *	 
	 */

	public static MultiPoolMonitor getPoolMonitor(String poolName)
			throws ConnectionPoolException {

		checkAndThrow();
		return pm.getMultiPoolMonitor(poolName);
	}

    /**
     * This method shuts down the pool, that is closes all connections to the database,
     * the pool can no longer be used unless reinitialised using a new SmartPoolFactory instance.
     */
    public static void shutDown() {
        shutDown = true;
        pm.shutDown();
    }


    public static void main (String arg[]) throws Exception {

		SmartPoolFactory smp= new SmartPoolFactory("c:\\windows\\desktop\\org.smartlib.pool.test.xml");
		Connection conn = 	smp.getConnection();
			
		
	}

}

