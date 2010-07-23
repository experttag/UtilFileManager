/*
 * @(#) SmartConnection 1.0 02/08/01
 */

package com.experttag.util.pool.core;

import java.sql.*;
import java.util.*;

/**
 * This  class encapsulates a Connection.
 * Dont expect me to document this class, if you want refer Sun's Documentation.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

public class SmartConnection implements  Connection ,
		Close , ConnectionMonitor {

	private Connection conn = null;
	private long lastAccessedTime = 0;
	private long connectionObtainedTime = 0;

	private ArrayList references;
	private boolean autoClose = false;
	private Debugger debug;

	private boolean isCurrentlyInTransaction;

	//Pool Ref to which this connection belongs
	private Pool poolReference;

	private boolean isClosed = false;

    private boolean isForcedClosed = false;
    private long forcedCloseTime = 0;

	private String owner ;

	// default contructor , can only be used within the package
	SmartConnection(Connection conn , Pool poolReference , String owner
				, boolean autoClose ) {

		if (conn == null)
			throw new IllegalArgumentException(
					"Connection Object is null/closed");
		this.conn = conn;
		this.poolReference = poolReference;
		this.owner = owner;
		lastAccessedTime = System.currentTimeMillis();
		connectionObtainedTime = lastAccessedTime;
		this.autoClose = autoClose;
		if (autoClose)
			references = new ArrayList();
		debug = new Debugger("SmartConnection" , true);

	}

	SmartConnection(Connection conn , Pool poolReference) {

		this(conn , poolReference , "N/A" , false);

	}



	private void preProcess() throws SQLException {

        if (isForcedClosed)
            throw new StaleConnectionException("SmartPool had withdrawn the connection since it was idle for more than the specified time. Connection withdrawn at " + new java.util.Date(forcedCloseTime));
		if (isClosed)
			throw new SQLException("Connection already closed");
		lastAccessedTime = System.currentTimeMillis();

	}

	public long getLastAccessedTime() {

		return lastAccessedTime;

	}

	void setLastAccessedTime() {
		
		lastAccessedTime = System.currentTimeMillis();

	}
	

	public boolean isCurrentlyInTransaction() {

		return isCurrentlyInTransaction;

	}

    public String getPoolName() {
        return ((PoolMonitor)poolReference).getName();
    }

    // default access , only accessible within the package
    public long getConnectionObtainedTime() {

        return connectionObtainedTime;

    }

	public String getOwner() {

		return owner;

	}

	public Statement createStatement() throws SQLException {

		preProcess();
		SmartStatement stmt = new SmartStatement(conn.createStatement() , this);
		if (autoClose)
			references.add(stmt);
		return  stmt;

	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {

		preProcess();
		SmartPreparedStatement pstmt = new SmartPreparedStatement(
			conn.prepareStatement(sql) , this);
		if (autoClose)
			references.add(pstmt);
		return pstmt;

	}

	public CallableStatement prepareCall(String sql) throws SQLException {

		preProcess();
		SmartCallableStatement cstmt = new SmartCallableStatement(
						conn.prepareCall(sql) , this );
		if (autoClose)
			references.add(cstmt);
		return cstmt; 

	}

	public String nativeSQL(String sql) throws SQLException {

		preProcess();
		return conn.nativeSQL(sql);

	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {

		preProcess();
		isCurrentlyInTransaction = autoCommit;
		conn.setAutoCommit(autoCommit);

	}

	public boolean getAutoCommit() throws SQLException {

		preProcess();
		return conn.getAutoCommit();

	}

	public void commit() throws SQLException {

		preProcess();
		conn.commit();

	}

	public void rollback() throws SQLException {

		preProcess();
		conn.rollback();

	}

    /**
     * This method is called when the connection is idle for more than
     * the specified time in max-conection-idle-time in configuration.
     * The PollThread calls this method to forcefully take the
     * connection back
     */ 
    void forcedClose() {
       
        try {
            //if (isForcedClosed)
                //return;
            synchronized (this) {
                forcedCloseTime = System.currentTimeMillis();
                debug.print("Forcefully closing connection for owner" + owner);
                close();
                isForcedClosed = true;  
            }
        }
        catch (SQLException sqe) {
            debug.writeException(sqe);        
        }

    }

	public void close() throws SQLException {

		preProcess();
		// returning connection to the pool
		if (autoClose)
			closeAll();
		// roll back and set autoCommit to true if in a transaction
		if (!conn.getAutoCommit() && ((ConnectionPool)poolReference).
                getConfigMonitor().getConnectionLoaderClass() != null ) {
			conn.rollback();
			conn.setAutoCommit(true);
		}
		debug.print("Connection Released for:" + getOwner());
		poolReference.returnConnection(this);
		isClosed = true;

	}

    

    
	private void closeAll() throws SQLException {

		for (int i=0 ; i<references.size() ; i++ ) {
			Close close = (Close)references.get(i);
			debug.print("Closing " + close);
			if (!close.isClosed())
				close.close();
		}

	}

	public boolean isClosed() throws SQLException {

		return isClosed;

	}

	public DatabaseMetaData getMetaData() throws SQLException {

		preProcess();
		return conn.getMetaData();

	}

	public	void setReadOnly(boolean readOnly) throws SQLException {

		preProcess();
		conn.setReadOnly(readOnly);

	}

	public boolean isReadOnly() throws SQLException {

		preProcess();
		return conn.isReadOnly();

	}

	public void setCatalog(String catalog) throws SQLException	{

		preProcess();
		conn.setCatalog(catalog);

	}

	public String getCatalog() throws SQLException {

		preProcess();
		return conn.getCatalog();

	}


	public void setTransactionIsolation(int level) throws SQLException {

		preProcess();
		conn.setTransactionIsolation(level);

	}

	public int getTransactionIsolation() throws SQLException {

		preProcess();
		return conn.getTransactionIsolation();

	}

	public SQLWarning getWarnings() throws SQLException {

		preProcess();
		return conn.getWarnings();

	}

	public void clearWarnings() throws SQLException {

		preProcess();
		conn.clearWarnings();

	}


	public Statement createStatement(int resultSetType,
			int resultSetConcurrency) throws SQLException {

		preProcess();
		SmartStatement stmt = new SmartStatement(
				conn.createStatement(resultSetType , resultSetConcurrency)
					,this);
		if (autoClose)
			references.add(stmt);
		return  stmt;

	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {

		preProcess();
		SmartPreparedStatement pstmt = new SmartPreparedStatement(
			conn.prepareStatement(sql,resultSetType,resultSetConcurrency) , this);
		if (autoClose)
			references.add(pstmt);
		return pstmt;

	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {

		preProcess();
		SmartCallableStatement cstmt = new SmartCallableStatement(
				conn.prepareCall(sql , resultSetType, 
					resultSetConcurrency) , this );
		if (autoClose)
			references.add(cstmt);
		return cstmt;

	}

	public java.util.Map getTypeMap() throws SQLException {

		preProcess();
		return conn.getTypeMap();

	}

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHoldability(int holdability) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getHoldability() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Savepoint setSavepoint() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


	// default access
	Connection returnConnection() {

			return conn;

	}

	public String toString() {

		return conn.toString();

	}

    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isValid(int timeout) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getClientInfo(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Properties getClientInfo() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
