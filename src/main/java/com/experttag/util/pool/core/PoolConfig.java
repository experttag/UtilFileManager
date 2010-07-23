/*
 * @(#) PoolConfig 1.0 02/08/01
 */

package com.experttag.util.pool.core;

/**
 * This  class encapsulates the configuration of the pool 
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */


public class PoolConfig implements ConfigMonitor  {


    //Default values when not specified
    public static final boolean DEFAULT_POOL_VALUE = false;
    public static final boolean AUTO_CLOSE = false;
    public static final boolean ALLOW_ANONYMOUS_CONNECTIONS = false;
    public static final String VALIDATION_QUERY = null;
    public static final int INCREMENT_BY = 1;
    public static final int MAX_FREE_CONNECTIONS_FOR_RELEASE = -1;
    public static final int CONNECTION_WAIT_TIME_OUT = 10000;
    public static final int MAX_CONNECTION_IDLE_TIME = -1;
    public static final int POLL_THREAD_TIME = 5000;
    public static final int LEAK_TIME_OUT = -1;
    public static final boolean DETECT_LEAKS = false;



    private String poolName;
	private int maxConnections;
	private int minConnections;
	private int increment = INCREMENT_BY;
	private String userName;
	private String password;
	private ConnectionString connectionString[];
	private String driver;
	private boolean isDefaultPool = DEFAULT_POOL_VALUE;
	private boolean detectLeaks = DETECT_LEAKS;

    //Leaktime out in milliseconds
    private long leakTimeOut = LEAK_TIME_OUT;
	private String defaultListener;

    // Sleep time in Milliseconds
    private long pollThreadTime = POLL_THREAD_TIME;
	private boolean autoClose = AUTO_CLOSE;
	private int maxConnectionsForRelease = MAX_FREE_CONNECTIONS_FOR_RELEASE;
	private boolean allowAnonymousConnections = ALLOW_ANONYMOUS_CONNECTIONS;
	private boolean externalPooling;
	private ConnectionLoaderClass connectionLoaderClass[] = null;

    // Wait time for connections in milliseconds
    private long connectionWaitTimeOut = CONNECTION_WAIT_TIME_OUT;
	private String validatorQuery = VALIDATION_QUERY;


    // Connection Idle time in milliseconds
    private long maxConnectionIdleTime = MAX_CONNECTION_IDLE_TIME;
    private boolean threadStickiness;


    PoolConfig() {
    }

    /**
	 * parameter list is too long to document, SELF HELP IS THE BEST HELP.
	 */
	PoolConfig(String poolName, int maxConnections, 
						int minConnections, String userName, 
						String password, ConnectionString connectionString[],
						int increment, String driver
						) {
								
		this.poolName = poolName;
		this.maxConnections = maxConnections;
		this.minConnections = minConnections;
		this.increment = increment;
		this.userName = userName; 
		this.password = password; 
		this.connectionString = connectionString; 
		this.driver = driver;

	}

	
	/**
	 * This method returns the name of the pool.
	 * @return name of the pool.
	 */
	public  String getMultiPoolName() {

		return poolName;

	}

	
 	void setMultiPoolName(String poolName) {

		this.poolName = poolName;

	}

    public boolean isThreadStickiness() {
        return threadStickiness;
    }

    public void setThreadStickiness(boolean threadStickiness) {
        this.threadStickiness = threadStickiness;
    }

    /**
	 * @return Max connections allowed in the pool.
	 */
	public  int getMaxConnections() {

		return maxConnections;

	}

	
 	void setMaxConnections(int maxConnections) { 

		this.maxConnections = maxConnections;

	}

	
	/**
	 * @return minimun connections in the pool.
	 */
	public int getMinConnections() {

		return minConnections;

	}

	void setMinConnections(int minConnections) { 

		this.minConnections = minConnections;

	}

	/**
	 * @return size of blocks of connections withdrawn at a time when no free
	 * 			connections are available.
	 */
	public int getIncrement() {

		return increment;

	}

	
	void setIncrement(int increment) { 

		this.increment = increment;

	}

	/**
	 * @return username to connect to the database.
	 */
	public String getUserName() {

		return userName;
	
	}

	void setUserName(String userName) {

		this.userName = userName; 
		
	}

	/**
	 * This method returns the password to connect to the database.
	 * @return password to connect to the database.
	 */
	public String getPassword() {

		return password;
	
	}

	void setPassword(String password) {

		this.password = password; 
		
	}

	/**
	 * @return connection string to connect to the database.
	 */
	public ConnectionString[] getConnectionString() {

		return connectionString;
	
	}

	void setConnectionString(ConnectionString connectionString[]) {

		this.connectionString = connectionString; 
		
	}

	/**
	 * @return driver name used to connect to database.
	 */
	public String getDriver() {

		return driver;

	}

	void setDriver(String driver) {

		this.driver = driver;

	}

	/**
	 * @return true if this pool is the default pool.
	 */ 
	public boolean isDefaultPool() {

		return isDefaultPool;

	}

	void setDefaultPool(boolean b) {

		isDefaultPool = b;

	}

	/**
	 * @return true if connection leak monitoring is enabled.
	 */
	public boolean isDetectLeaks() {

		return detectLeaks;

	}

	void setDetectLeaks(boolean b) {

		detectLeaks = b;

	}

	/**
	 * @return time out for detecting leaks.
	 */
	public long getLeakTimeOut() {

		return leakTimeOut;

	}

	void setLeakTimeOut(long leakTimeOut) {

		this.leakTimeOut = leakTimeOut;	

	}

	/**
	 * @return default listener class for connection leak.
	 */
	public String getDefaultListener() {

		return defaultListener;

	}

	void setDefaultListener(String defaultListener) {

		this.defaultListener= defaultListener ;	

	}
	/**
	 * @return true if automotic closing of Statement ,PreparedStatement ,
	 * 			CallableStatement is enabled once the connection is closed is
	 *			enabled.
	 */
	public long getPollThreadTime() {

		return pollThreadTime;

	}

	void setPollThreadTime(long pollThreadTime) {

		this.pollThreadTime = pollThreadTime;

	}

	/**
	 * @return true if automatic closing of Statement ,PreparedStatement ,
	 * 			CallableStatement is enabled once the connection is closed is
	 *			enabled.
	 */
	public boolean isAutoClose() {

		return autoClose;

	}

	void setAutoClose(boolean autoClose) {

		this.autoClose = autoClose;
	
	}

	/**
	 * This method returns the name of the pool.
	 * @return maximum number of free connections allowed after which 
	 * excessive connections are released .
	 */
	public int getMaxConnectionsForRelease() {

		return maxConnectionsForRelease;

	}

	void setMaxConnectionsForRelease(int maxConnectionsForRelease) {

		this.maxConnectionsForRelease = maxConnectionsForRelease;	

	}

	void setAllowAnonymousConnections(
			boolean allowAnonymousConnections) {
			
		this.allowAnonymousConnections = allowAnonymousConnections;

	}

	/**
	 * This method returns the name of the pool.
	 * @return true if anonymous connections are allowed , i.e 
	 * without specifying the owner .
	 */
	public boolean isAllowAnonymousConnections() {
			
		return (allowAnonymousConnections);

	}


	/**
	 * This method sets the fully qualified name of the Connection Loader class.
	 * @param connectionLoaderClass the fully qualified name of the Connection Loader class
	 */
	
	void setConnectionLoaderClass(ConnectionLoaderClass connectionLoaderClass[]) {

        if (connectionLoaderClass != null)
            externalPooling = true;
        this.connectionLoaderClass = connectionLoaderClass;
		
	}

	/**
	 * This method returns the fully qualified name of the Connection Loader class.
	 * @return Fully qualified name of the class
	 */
	
	public ConnectionLoaderClass[] getConnectionLoaderClass() {
		
		return (connectionLoaderClass);

	}

	/**
	 * This method returs a boolean.
	 *
	 * @return true- If Smart pool is wrapped to another pool
	 * 		   	
	 */
	public boolean isExternalPooling() {
	
		return externalPooling;	

	}


	/**
	 * This method sets the isExternalPooling value.
	 *
	 * @param externalPooling true- If Smart pool is wrapped to another pool.
	 * 
	 */ 
	void setExternalPooling(boolean externalPooling) {

		this.externalPooling = externalPooling;

	}

	/**
	 * This method returns the connectionWaitTimeOut value.
	 */ 
	public long getConnectionWaitTimeOut() {

		return connectionWaitTimeOut;
		
	}
	
	/**
	 * This method sets the connectionWaitTimeOut value.
	 *
	 * @param connectionWaitTimeOut 
	 * 
	 */ 
	void setConnectionWaitTimeOut(long connectionWaitTimeOut) {

		this.connectionWaitTimeOut = connectionWaitTimeOut;
		
	}

  	/**
	 * This method returns the max-connection-idle-time value.
	 */ 
	public long getMaxConnectionIdleTime() {

		return maxConnectionIdleTime;
		
	}
	
	/**
	 * This method sets the max-connection-idle-time value.
	 *
	 * @param maxConnectionIdleTime 
	 * 
	 */ 
	void setMaxConnectionIdleTime(long maxConnectionIdleTime) {

		this.maxConnectionIdleTime = maxConnectionIdleTime;
		
	}

	/**
	 * This method sets the Validator-Query. 
	 * 
	 * @param validatorQuery 
	 */

	void setValidatorQuery(String validatorQuery) {

		this.validatorQuery = validatorQuery;
		
	}

	/**
	 * This method returns the validatorQuery.
	 *
	 * @return validatorQuery 
	 */
	public String getValidatorQuery() {

		return validatorQuery;

	}

    /**
     * This metod returns the ConnectionString object with the name <code>name</code>
     */
    public ConnectionString getConnectionStringByName(String name) {
        for (int i=0; i<connectionString.length; i++) {
            if (connectionString[i].getName().equals(name)) {
                return connectionString[i];
            }
        }
        return null;
    }

    /**
     * This metod returns the ConnectionLoaderClass object with the name <code>name</code>
     */
    public ConnectionLoaderClass getConnectionLoaderClassByName(String name) {
        for (int i=0; i<connectionLoaderClass.length; i++) {
            if (connectionLoaderClass[i].getName().equals(name)) {
                return connectionLoaderClass[i];
            }
        }
        return null;
    }


    /**
	 * You know what this does. 
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("\n\nConnection Pool Configuration ======>");
		sb.append("\n\tPoolName: " + poolName);
		sb.append("\n\tMax Connections: " + maxConnections);
		sb.append("\n\tMin Connections: " + minConnections);
		sb.append("\n\tIncrement-Connections by: " + increment);
		sb.append("\n\tUser Name: " + userName);
		sb.append("\n\tPassword: " + password);
		sb.append("\n\tConnection String: " + connectionString);
        if (connectionString != null) {
            for (int i=0; i<connectionString.length; i++) {
                sb.append("\n\t                   " + connectionString[i]);
            }
        }
        sb.append("\n\tThread Skickiness: " + threadStickiness);
        sb.append("\n\tDriver: " + driver);
		sb.append("\n\tVakidator Query: " + validatorQuery);
		sb.append("\n\tDefault Pool: " + isDefaultPool);
		sb.append("\n\tDetect Leaks: " + detectLeaks);
		sb.append("\n\tLeak Timeout: " +  leakTimeOut + " Milli Seconds");
		sb.append("\n\tDefault Listener: " + defaultListener);
		sb.append("\n\tPool Thread Time: " + pollThreadTime + " Milli Seconds");
		sb.append("\n\tAuto Close: " + autoClose);
		sb.append("\n\tMax connection for release: " + maxConnectionsForRelease);
		sb.append("\n\tConnection Loader Class: " + connectionLoaderClass);
        if (connectionLoaderClass != null) {
            for (int i=0; i<connectionLoaderClass.length; i++) {
                sb.append("\n\t                   " + connectionLoaderClass[i]);
            }
        }
        sb.append("\n\tConnection Wait Time Out: " + connectionWaitTimeOut + " Milli Seconds");
		sb.append("\n\tMaximum Connection Idle Time: " + maxConnectionIdleTime + " Milli Seconds");
		return sb.toString();

	}

    public static class ConnectionString {

        private String name;
        private String connectString;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConnectString() {
            return connectString;
        }

        public void setConnectString(String connectString) {
            this.connectString = connectString;
        }

        public String toString() {
            return "ConnectionString{" + name + "," + connectString +"}";
        }

    }

    public static class ConnectionLoaderClass {

        private String name;
        private String connectionLoaderClass;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConnectionLoaderClass() {
            return connectionLoaderClass;
        }

        public void setConnectionLoaderClass(String connectionLoaderClass) {
            this.connectionLoaderClass = connectionLoaderClass;
        }

        public String toString() {
            return "ConnectionLoaderClass{" + name + "," + connectionLoaderClass +"}";
        }

    }
 
}
