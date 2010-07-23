/*
 * @(#) PoolManagerImpl 1.0 02/08/01
 */

package com.experttag.util.pool.core;


import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * This class implements the PoolManager interface.The basic responsibility of
 * this class is to load the configuration file , and initialise the pools and 
 * priovide a single point access to the pools.
 *
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */

public class PoolManagerImpl implements PoolManager {

	private	Map poolMap = new HashMap();
	private String defaultPool;
    private boolean shutDown = false;
    private static Logger logger = Logger.getLogger(PoolManagerImpl.class);

    private void loadConfig(File file ) throws ConnectionPoolException {


        PoolConfig[] config = null;
        try {
            config =	new ConfigFileProcessor().getPoolConfig(file.getAbsolutePath());
        } catch(Exception e) {
            throw new ConnectionPoolException("Error occurred during loading config file: "
                    + file.getAbsolutePath(), e);
        }

        for (int i=0; i<config.length; i++) {
            PoolConfig pc = config[i];
            String poolName = pc.getMultiPoolName();
            if (logger.isDebugEnabled()) {
                logger.debug("" + pc);
            }
            if (pc.isDefaultPool()) {
                if (defaultPool != null)
                    throw new ConnectionPoolException("More than one Connection Pools cannot have default set to 'true'");
                defaultPool = poolName;
            }
            poolMap.put(poolName , new MultiPoolImpl(pc));
        }

    }
	
	/**
	 * This constructor intialises the SmartPoolClass , reads the 
	 * configuration from <code>fileName</code> and loads the PoolManger.
	 * 
	 * @param file The absolute configuration file path.
	 * @exception ConnectionPoolException if there is any problem 
	 *			initialising the pools
	 */
	public PoolManagerImpl(String fileName) throws ConnectionPoolException {

		if (fileName == null || fileName.trim().equals(""))
		throw new IllegalArgumentException ("File Name cannot be null/empty");
		File f1 = new File(fileName);
		loadConfig(f1);

	}

	/**
	 * This constructor intialises the SmartPoolClass , reads the 
	 * configuration from <code>file</code> and loads the PoolManger.
	 * 
	 * @param file The configuration file.
	 * @exception ConnectionPoolException if there is any problem 
	 *			initialising the pools
	 */
	public PoolManagerImpl(File file) throws ConnectionPoolException {

		loadConfig(file);

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
	
	public Connection getConnection() throws ConnectionPoolException {

		if ( defaultPool == null )
			throw new ConnectionPoolException("No default pool specified");
		return (getConnection(defaultPool));

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
	public Connection getConnection(String poolName)
			throws ConnectionPoolException 	{

		MultiPool p = (MultiPool)poolMap.get(poolName);
		if (p==null)
			throw new ConnectionPoolException("No such pool:" + poolName);
		return p.getConnection();

	}

	/**
	 * This method returns a Connection from the  pool <code>poolName</code>. 
	 * The owner of this connection is identified by <code>owner</code> .
	 *
	 * <b>Note: This method blocks if the pool size has reached it's 
	 * maximum size and no free connections are available 
	 * until a free connection is available</b>. The time period for which this
	 * method blocks depends on the connection-wait-time-out specified in 
	 * the configuration file. 
	 *
	 * @param poolName Name of the pool.
	 * @param owner String identifying the owner.
 	 * @return Connection from the pool
	 * 
	 * @exception ConnectionPoolException if there is any problem 
	 *		getting connection.	
	 */
	public Connection getConnection(String poolName ,  String owner)
		throws ConnectionPoolException 	{

		MultiPool p = (MultiPool)poolMap.get(poolName);
		if (p==null)
			throw new ConnectionPoolException("No such pool:" + poolName);
		return p.getConnection(owner);

	}

	/**
	 * This method adds  a connection leak listener.The methods of 
	 * <code>cle</code> will be called when a leak is detected as per the
	 * pool configuration.
	 *
	 * @param poolName Name of the pool.
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException if there is any problem 
	 *	 adding ConnectionLeakListener.
	 */
	public void addConnectionLeakListener(String poolName
			, ConnectionLeakListener cle)  throws ConnectionPoolException {

			MultiPool p = (MultiPool)poolMap.get(poolName);
			if (p==null)
				throw new ConnectionPoolException("No such pool:" + poolName);
			p.addConnectionLeakListener(cle);

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
	public void removeConnectionLeakListener(String poolName
			, ConnectionLeakListener cle) throws ConnectionPoolException {

			MultiPool p = (MultiPool)poolMap.get(poolName);
			if (p==null)
				throw new ConnectionPoolException("No such pool:" + poolName);
			p.removeConnectionLeakListener(cle);

	}

	/**
	 * This method returns the instance of PoolMonitor for the pool 
	 * <code>poolName</code>
	 *
	 * @param poolName Name of the pool.
	 * @return PoolMonitor interface to monitor the state of the pool
	 * @exception ConnectionPoolException 
	 *	 
	 */
	public MultiPoolMonitor getMultiPoolMonitor(String poolName)
				throws ConnectionPoolException {

		MultiPoolMonitor p = (MultiPoolMonitor)poolMap.get(poolName);
		if (p==null)
			throw new ConnectionPoolException("No such pool:" + poolName);
		return p;

	}

    /**
     * This method shuts down the pool, that is closes all connections to the database,
     * the pool can no longer be used unless reinitialised using a new SmartPoolFactory instance.
     */
    public void shutDown() {
        shutDown = true;
        Set set = poolMap.entrySet();
        for (Iterator it= set.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            if (logger.isDebugEnabled()) {
                logger.debug("Shutting down: " + entry.getKey());
            }
            ((MultiPool)entry.getValue()).shutDown();
        }

    }

    // Main method , once upon a time this used to be the only method in
	// my java programs. That is how you do procedural programming in java.
	public static void main (String args[]) throws Exception {

		int k = 0;
		Thread arr[] = new Thread[40] ;
		try {
			PoolManagerImpl p1 = new PoolManagerImpl("c:\\windows\\desktop\\org.smartlib.pool.test.xml");
			System.exit(0);
			for (int i = 0 ; i < 20 ; i++ ) {

				Thread r = new Thread(new ThreadRunner(p1 , "Sachin" , "" + i
						, i*800 ));
				arr[k] = r;
				k++;
				r.start();
				r = new Thread(new ThreadRunner(p1 , "Shetty" , "" + i
						, i*800 ));
				r.start();
				arr[k] = r;
				k++;
			}

		}
		catch (Exception e ) {
            if (logger.isDebugEnabled()) {
                logger.debug(e,e);
            }
        }
		for ( int z =0 ; z<k ; z++ ) {
			System.out.println("Wating ------> for " + z );
			arr[z].join();
		}
		System.out.println("Child threads Finished -->Exiting");
	}


	public static class ThreadRunner implements Runnable {

		PoolManager p1;
		String poolName;
		String owner;
		int sleepTime ;

		public	ThreadRunner(PoolManager p1 , String poolName , String owner , int sleepTime) {

			this.p1 = p1;
			this.poolName = poolName;
			this.owner = owner;
			this.sleepTime= sleepTime;

		}

		public void run() {

			try {
				for (int i = 0 ; i <= 10 ; i++) {
					System.out.println("Thread " + owner + " running");
					Connection conn = p1.getConnection(poolName);
					try {
						Thread.sleep(sleepTime);
					}
					catch (InterruptedException ie) {}
					conn.close();
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}


		}

	}

}
