package com.experttag.util.pool.core;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: kerneldebugger
 * Date: Oct 1, 2005
 * Time: 6:44:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MultiPool {

    /**
     * This method returns a Connection from the connection pool.
     * The owner of this pool is marked as N/A indicating unknown.
     *
     * <b>Note: This method blocks if the pool size has reached it's
     * maximum size and no free connections are available
     * until a free connection is available</b>. The time period for which this
     * method blocks depends on the connection-wait-time-out specified in
     * the configuration file.
     *
     *
      * @return Connection from the pool
     * @exception ConnectionPoolException if there is any problem
     *		getting connection.
     */
    public Connection getConnection()
        throws ConnectionPoolException;


    /**
     * This method returns a Connection from the  pool.
     * The owner of this connection is identified by <code>owner</code> .
     *
     * <b>Note: This method blocks if the pool size has reached it's
     * maximum size and no free connections are available
     * until a free connection is available</b>. The time period for which this
     * method blocks depends on the connection-wait-time-out specified in
     * the configuration file.
     *
     *
     * @param owner String identifying the owner.
      * @return Connection from the pool
     *
     * @exception ConnectionPoolException if there is any problem
     *		getting connection.
     */

    public Connection getConnection(String owner)
        throws ConnectionPoolException;


    /**
	 * This method adds  a connection leak listener. The methods of
	 * <code>cle</code> will be called when a leak is detected as per the
	 * pool configuration.
	 *
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException If there is any problem
	 *	 adding ConnectionLeakListener.
	 */
	public void addConnectionLeakListener(ConnectionLeakListener cle )
		throws ConnectionPoolException;

	/**
	 * This method removes a connection leak listener.<code>cle</code> will
	 * not get any further notifications.
	 *
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException If there is any problem
	 *	 removing ConnectionLeakListener.
	 */
	public void removeConnectionLeakListener(ConnectionLeakListener cle)
		throws ConnectionPoolException;

    /**
     * This method shuts down the pool
     */
    public void shutDown();

}
