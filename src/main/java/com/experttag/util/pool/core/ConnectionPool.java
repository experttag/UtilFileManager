/*
 * @(#) ConnectionPool 1.0 02/08/01
 */


package com.experttag.util.pool.core;

import org.apache.log4j.Logger;

import java.util.*;
import java.sql.*;


/**
 * This class implements the Pool interface and thus is responsible for 
 * managing a single pool of connections.
 */

public class ConnectionPool implements Pool , PoolMonitor {

    private PoolConfig config;
	private final String name;
	private final Debugger debug;
	private int currentPoolSize = 0;
	private int usedConnections = 0;
	private Vector connectionList;
	private Vector connectionListenerList;
	private Thread pollerThread;
	private ConnectionProvider connProvider = null;
	private String validatorQuery = null;
    private volatile boolean  shutDown = false;
    private Hashtable connectionHash = new Hashtable();

    Logger logger = Logger.getLogger(ConnectionPool.class);

    /**
	 * This method draws a raw connection from the database.
	 */
	private Connection loadConnection() throws ConnectionPoolException {


			try {
                Class.forName(config.getDriver());
			}
			catch(ClassNotFoundException classNotFound)	{
				throw new ConnectionPoolException("Could not load Driver",
					classNotFound);
			}
			Connection con = null;
			try {
				con = DriverManager.getConnection(config.getConnectionStringByName(name).getConnectString(),
					config.getUserName(), config.getPassword());
			}
			catch (Exception e ) {
				throw new ConnectionPoolException("Could not obtain Connection",
						e);
			}
			currentPoolSize++;
			return con;

	}

	/**
	 * @return Vector of connections in use.
	 */
	public Vector getConnectionsInUse() {

		return connectionList;

	}

	/**
	 * @return Vector of registered ConnectionLeakListeners.
	 */
	public Vector getConnectionLeakListeners() {

		return connectionListenerList;

	}

	/**
	 * @return Number of free connections in the pool.
	 */
	public int getNoOfFreeConnections() {

		return (currentPoolSize - usedConnections);

	}

	/**
	 * This method returns an instance of ConfigMonitor.
	 * @return ConfigMonitor for monitoring the configuration of the pool at
	 * 			runtime.
	 */
	public ConfigMonitor getConfigMonitor() {

		return (ConfigMonitor)config;

	}

    public String getName() {
        return name;
    }

    /**
     * This method draws the initial raw connections.
     */
    private void initialiseConnections() throws ConnectionPoolException {

            try {
                int minConnections = config.getMinConnections();
                for (int i = 0 ; i<minConnections ; i++) {
                    connectionHash.put(loadConnection(), Boolean.TRUE);
                }
            }
            catch (Exception e) {
                throw new ConnectionPoolException("Could not load initial connection" , e);
            }


    }

	/*
	 * This method is called when the existing connection is wrapped to another
	 * pool.
	 */
	private void returnConnectionToOtherPool(Connection conn)
			throws Exception {

		connProvider.returnConnection(conn);

	}


	/**
	 * Constructor initialises the pool as per the configuration specified by
	 * <code>config</code>
	 */
	ConnectionPool(PoolConfig config, String name) throws ConnectionPoolException {

        this.name = name;
        this.config = config;
		try {
			if (config.getConnectionLoaderClass() != null)
				connProvider = (ConnectionProvider)getClass().getClassLoader()
					.loadClass(config.getConnectionLoaderClassByName(name).getConnectionLoaderClass()).newInstance();
		}
		catch (Exception exp) {
			throw new ConnectionPoolException("Error loading Connection Loader Class",exp);
		}
		if (connProvider == null)
			initialiseConnections();

		debug = new Debugger(name, true);
		connectionList = new Vector(config.getMinConnections() , config.getIncrement());
		connectionListenerList = new Vector();
		try {
			if ( config.getDefaultListener() != null ) {
				String defaultListener = config.getDefaultListener();
				addConnectionLeakListener((ConnectionLeakListener)getClass().getClassLoader().loadClass(defaultListener).newInstance());
			}
		}
		catch (Exception e ) {
			throw new ConnectionPoolException("Could not load class "
				+ config.getDefaultListener() , e);
		}
		if (config.isDetectLeaks()) {
			pollerThread = new Thread(new ConnectionLeakPollThread(
						connectionList , connectionListenerList ,
						name, config.getPollThreadTime() ,
						config.getLeakTimeOut(), this));
			pollerThread.start();
		}

		validatorQuery = config.getValidatorQuery();

	}

	/**
	 * This method returns the current size of the pool.
	 * @return Current size of the pool.
	 */
	public int getCurrentPoolSize() {

		return currentPoolSize;

	}

	/**
	 * This method returns a Connection from the connection pool.
	 * The owner of this pool is marked as N/A indicating unknown/anonymous.
	 *
	 * <b>Note: This method blocks if the pool size has reached it's
	 * maximum size and no free connections are available
	 * until a free connection is available.</b> The time period for which this
	 * method blocks depends on the connection-wait-time-out specified in
	 * the configuration file.
	 *
	 *
 	 * @return Connection from the pool.
	 * @exception ConnectionPoolException if there is any problem
	 *		getting connection.
	 */
	public Connection getConnection() throws ConnectionPoolException {

		if (config.isAllowAnonymousConnections())
			return getConnection("N/A");
		else
			throw new ConnectionPoolException ("You are not allowed to take anonumous connections, please provide an owner name");

	}

	// Checks if the Connection is valid
	private boolean checkIfValid(Connection conn) {

		try {
			debug.print(" Checking Connection for '" + validatorQuery + "'");
			if (validatorQuery != null && !validatorQuery.trim().equals("")) {
				Statement stmt = conn.createStatement();
				boolean bool = stmt.execute(validatorQuery);
				stmt.close();
				return bool;
			}
			else {
				return true;
			}
		}
		catch (SQLException exp) {
            if (logger.isDebugEnabled()) {
                logger.debug("Exception occurred while trying to test connection validity", exp);
            }
            return false;
		}

	}

	/*
	 * This method is called when the existing connection is wrapped to another
	 * pool.
	 */
	private  Connection getConnectionFromOtherPool(String owner)
			throws ConnectionPoolException {

		try {
			synchronized (this) {
				if (config.getMaxConnections() == usedConnections) {
					try {
						debug.print("Hey the value is "
								+ config.getConnectionWaitTimeOut());
						wait(config.getConnectionWaitTimeOut());
						if (config.getMaxConnections() == usedConnections) {
							throw new TimeOutException("Timed-out while "
								+ "waiting for free connection");
						}
					}
					catch (InterruptedException ie) {
					}
				}

				Connection conn = connProvider.getConnection();
				usedConnections++;
				currentPoolSize++;
				// Checking if the connection is still live and active
				// if not get replace the old one with new one
				if (checkIfValid(conn)) {
					SmartConnection smt = new SmartConnection(conn,
							this,owner, config.isAutoClose());
					connectionList.add(smt);
					return smt;
				}
				else {
					boolean valid = false;
					int i=1;
					while (!valid) {
						conn = connProvider.getConnection();
						valid = checkIfValid(conn);
						i++;
						if (i == 3 && !valid)
							throw new ConnectionPoolException("Three consecutive cnnections failes the Validator Query org.smartlib.pool.test");
					}
					SmartConnection smt = new SmartConnection(conn,
							this,owner, config.isAutoClose());
					connectionList.add(smt);
					return smt;
				}
			}
		}
		catch (ConnectionPoolException cpe) {
			throw cpe;
		}
		catch (Exception exp) {
			throw new ConnectionPoolException("Error while getting connections from the Connection Loader Class", exp);
		}

	}



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
			throws ConnectionPoolException {


		// Check for external pooling
		if (connProvider != null )
			return getConnectionFromOtherPool(owner);


		Enumeration cons = connectionHash.keys();
		//debug.print("Getting Connection " + usedConnections);
		synchronized (connectionHash) {
			// Checking if max-conn is less than used connection  , if not wait
			if (config.getMaxConnections() == usedConnections) {
				try {
					//debug.print("Waiting for Connection " + usedConnections);
					debug.print("Hey the value is " + config.getConnectionWaitTimeOut());
					connectionHash.wait(config.getConnectionWaitTimeOut());
					if (config.getMaxConnections() == usedConnections) {
						throw new TimeOutException("Timed-out while "
							+ "waiting for free connection");
					}

				}
				catch (InterruptedException ie) {
				}
			}

			// Reached here indicates that free conn is available or
			// currentpool size is less and thus new conn can be added to pool
			while (cons.hasMoreElements()) {
				// Checking if any unused connection is available
				Connection con = (Connection)cons.nextElement();
		        Boolean b = (Boolean)connectionHash.get(con);
				if ( b == Boolean.TRUE ) {
					//Unused Connection is available
					connectionHash.put(con , Boolean.FALSE);
					usedConnections++;
					debug.print("Hey After Incrementing conn " +usedConnections);
					//debug.print("Connection Obtained " + usedConnections);
					// Checking if the connection is still live and active
					// if not get replace the old one with new one
					if (checkIfValid(con)) {
						SmartConnection smt = new SmartConnection(con,
								this,owner, config.isAutoClose());
						connectionList.add(smt);
						return smt;
					}
					else {
						boolean valid = false;
						int failCounter = 1;
						while (!valid) {
							connectionHash.remove(con);
							con = loadConnection();
							connectionHash.put(con , Boolean.FALSE);
							failCounter++;
							valid = checkIfValid(con);
							if (failCounter == 3 && !valid)
								throw new ConnectionPoolException("Three consecutive connections failed the Validator Query org.smartlib.pool.test");
						}
						SmartConnection smt = new SmartConnection(con,
								this,owner, config.isAutoClose());
						connectionList.add(smt);
						return smt;
					}
				}
			}

			// No Connection available hence increase the pool size
			int increment = config.getIncrement();
			Connection	c = null;
			SmartConnection smt = null;
			for (int i = 0 ; i < increment
					&& i + currentPoolSize <= config.getMaxConnections(); i++){
				c = loadConnection();
				boolean valid = checkIfValid(c);
				int failCounter = 1;
				while (!valid) {
					c= loadConnection();
					failCounter++;
					valid = checkIfValid(c);
					if (failCounter == 3 && !valid)
						throw new ConnectionPoolException("Three consecutive connections failed the Validator Query org.smartlib.pool.test");
				}
				if (i==0) {
					smt = new SmartConnection(c , this ,
							owner , config.isAutoClose());
					connectionHash.put(c , Boolean.FALSE);
				}
				else
					connectionHash.put(c , Boolean.TRUE);
			}

			//debug.print("Connection Incremented " + usedConnections);
			//debug.print("Pool Size" + currentPoolSize);
			usedConnections++;
			connectionList.add(smt);
			return smt;

		}

	}

	/**
	 * This method releases the connection back to the pool.
	 * @param ret connection to be released
	 */
	public void returnConnection (Connection ret) {

		if (connProvider != null )	{
			try {
				synchronized (this) {
					Connection conn = ((SmartConnection)ret).returnConnection();
					returnConnectionToOtherPool(conn);
					usedConnections--;
					currentPoolSize--;
					debug.print("Removed value is " + connectionList.removeElement(ret));
					notifyAll();
				}
			}
			catch (Exception exp) {
				// Error while returning
				debug.print("Error " + exp);
			}
			return;
		}

		Object tempRef = ret;
		SmartConnection smt = (SmartConnection)ret;
		ret = smt.returnConnection();
		Connection con;
		Enumeration cons = connectionHash.keys();
		synchronized(connectionHash) {
			while (cons.hasMoreElements()) {
				con = (Connection)cons.nextElement();
				if (con == ret) {
					connectionHash.put(con, Boolean.TRUE);
					break;
				}
			}
			debug.print("Connection Released " + usedConnections);
			usedConnections--;
			debug.print("Connection contains list " + connectionList.contains(tempRef));
			debug.print("Removed value is " + connectionList.removeElement(tempRef));

			connectionHash.notifyAll();
		}

	}

	/**
	 * This method adds a connection leak listener. The methods of
	 * <code>cle</code> will be called when a leak is detected as per the
	 * pool configuration.
	 *
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException if there is any problem
	 *	 adding ConnectionLeakListener.
	 */
	public void addConnectionLeakListener(ConnectionLeakListener cle )
			throws ConnectionPoolException {

		if (cle == null)
			throw new IllegalArgumentException("ConnectionLeakListener cannot be null");
		debug.print("Added is " + cle);
		connectionListenerList.add(cle);

	}

	/**
	 * This method removes a connection leak listener. <code>cle</code> will
	 * not get any  further notifications.
	 *
	 * @param cle Class implementing ConnectionLeakListener interface.
	 * @exception ConnectionPoolException If there is any problem
	 *	 removing ConnectionLeakListener.
	 */
	public void removeConnectionLeakListener(ConnectionLeakListener cle)
			throws ConnectionPoolException {

			if (cle == null )
				throw new IllegalArgumentException("ConnectionLeakListener cannot be null");
			debug.print("Trying to remove " + cle);
			boolean found = connectionListenerList.remove(cle);
			if (!found)
				throw new ConnectionPoolException("No Such Listener");

	}

	/**
	 * This method releases excessive connections, i.e it actully closes
	 * them.
	 */
	public void releaseConnections() {


		if (config.getMaxConnectionsForRelease() == -1)
			return ;
		if (config.getMaxConnectionsForRelease()
				< getNoOfFreeConnections()) {
			int i = config.getIncrement();
			synchronized (connectionHash) {
				Enumeration cons = connectionHash.keys();
				while (cons.hasMoreElements() && i > 0) {
					Connection con = (Connection)cons.nextElement();
					Boolean b = (Boolean)connectionHash.get(con);
					if (b == Boolean.TRUE) {
						connectionHash.remove(con);
						try {
							con.close();
							i--;
							currentPoolSize = connectionHash.size();
							debug.print("Releasing conn" + con);
						}
						catch (SQLException e) {
							debug.print("Error in closing connection" + e );
						}
					}
				}
			}
		}

	}

    public void shutDown() {
        if (logger.isDebugEnabled()) {
            logger.debug("Shutting down connections for pool:" + name);
        }
        shutDown = true;
        Enumeration cons = connectionHash.keys();
        while (cons.hasMoreElements()) {
            Connection con = (Connection)cons.nextElement();
            try {
                con.close();
            }
            catch (Exception e) {
                logger.warn("Exception occurred during connections close", e);
            }
        }

    }





}
