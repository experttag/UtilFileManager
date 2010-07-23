/*
 * @(#) SmartPreparedStatement 1.0 02/08/01
 */

package com.experttag.util.pool.core;

import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.net.URL;


/**
 * This  class encapsulates  a SmartPreparedStatement.
 * Dont expect me to document this class, if you want refer Sun's Documentation.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

public class SmartPreparedStatement extends SmartStatement
                implements PreparedStatement , Close {

    private PreparedStatement pstmt;
    private SmartConnection sConn ;
    private boolean isClosed;
    Debugger debug =
        new Debugger("org.smartlib.pool.core.SmartPreparedStatement", true);


    // default access
    SmartPreparedStatement(PreparedStatement pstmt , SmartConnection sConn) {

        super(pstmt,sConn);
        this.pstmt = pstmt;
        this.sConn = sConn;

    }

    private void preProcess() throws SQLException {

        if (isClosed())
            throw new SQLException("Prepared Statement already closed");
        sConn.setLastAccessedTime();

    }


    public ResultSet executeQuery() throws SQLException {

        preProcess();
        return pstmt.executeQuery();

    }

    public int executeUpdate() throws SQLException {

        preProcess();
        return pstmt.executeUpdate();

    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {

        preProcess();
        pstmt.setNull(parameterIndex , sqlType);

    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {

        preProcess();
        pstmt.setBoolean(parameterIndex , x );

    }

    public void setByte(int parameterIndex, byte x) throws SQLException {

        preProcess();
        pstmt.setByte(parameterIndex , x);

    }

    public void setShort(int parameterIndex, short x) throws SQLException {

        preProcess();
        pstmt.setShort(parameterIndex , x);

    }

    public void setInt(int parameterIndex, int x) throws SQLException {

        preProcess();
        pstmt.setInt(parameterIndex , x);

    }

    public void setLong(int parameterIndex, long x) throws SQLException {

        preProcess();
        pstmt.setLong(parameterIndex , x );

    }

    public void setFloat(int parameterIndex, float x) throws SQLException {

        preProcess();
        pstmt.setFloat(parameterIndex , x );

    }

    public void setDouble(int parameterIndex, double x) throws SQLException {

        preProcess();
        pstmt.setDouble(parameterIndex , x );

    }

    public  void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

        preProcess();
        pstmt.setBigDecimal(parameterIndex , x);

    }

    public void setString(int parameterIndex, String x)
            throws SQLException {

        preProcess();
        pstmt.setString(parameterIndex , x );

    }

    public void setBytes(int parameterIndex, byte x[])
            throws SQLException {

        preProcess();
        pstmt.setBytes(parameterIndex , x);

    }

    public void setDate(int parameterIndex, java.sql.Date x)
        throws SQLException {

        preProcess();
        pstmt.setDate(parameterIndex , x);

    }

    public void setTime(int parameterIndex, java.sql.Time x)
        throws SQLException {

        preProcess();
        pstmt.setTime(parameterIndex , x );

    }

    public void setTimestamp(int parameterIndex, java.sql.Timestamp x)
        throws SQLException {

        preProcess();
        pstmt.setTimestamp(parameterIndex, x);

    }

    public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length)
        throws SQLException {

        preProcess();
        pstmt.setAsciiStream( parameterIndex , x , length);

    }

    public void setUnicodeStream(int parameterIndex, java.io.InputStream x,
                                 int length) throws SQLException {

        preProcess();
        pstmt.setUnicodeStream(parameterIndex , x , length);

    }

    public void setBinaryStream(int parameterIndex, java.io.InputStream x,
                                int length) throws SQLException {

        preProcess();
        pstmt.setBinaryStream(parameterIndex , x , length);

    }

    public void clearParameters() throws SQLException {

        preProcess();
        pstmt.clearParameters();

    }

    public void setObject(int parameterIndex, Object x,
                          int targetSqlType, int scale) throws SQLException {

        preProcess();
        pstmt.setObject(parameterIndex , x , targetSqlType , scale);

    }

    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException {

        preProcess();
        pstmt.setObject(parameterIndex , x , targetSqlType);

    }

    public void setObject(int parameterIndex, Object x) throws SQLException {

        preProcess();
        pstmt.setObject(parameterIndex , x);

    }

    public boolean execute() throws SQLException {

        preProcess();
        return pstmt.execute();

    }

    public void addBatch() throws SQLException {

        preProcess();
        pstmt.addBatch();

    }

    public void setCharacterStream(int parameterIndex,
                                   java.io.Reader reader, int length) throws SQLException {

        preProcess();
        pstmt.setCharacterStream(parameterIndex , reader , length );

    }
    public void setRef (int i, Ref x) throws SQLException {

        preProcess();
        pstmt.setRef(i , x);

    }
    public void setBlob (int i, Blob x) throws SQLException {

        preProcess();
        pstmt.setBlob(i,x);

    }

    public void setClob (int i, Clob x) throws SQLException {

        preProcess();
        pstmt.setClob(i , x );

    }

    public void setArray (int i, Array x) throws SQLException {

        preProcess();
        pstmt.setArray(i , x);

    }

    public ResultSetMetaData getMetaData() throws SQLException {

        preProcess();
        return	pstmt.getMetaData();

    }

    public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
        throws SQLException {

        preProcess();
        pstmt.setDate(parameterIndex , x , cal);

    }

    public void setTime(int parameterIndex, java.sql.Time x, Calendar cal)
        throws SQLException {

        preProcess();
        pstmt.setTime(parameterIndex , x , cal);

    }

    public void setTimestamp(int parameterIndex, java.sql.Timestamp x
            , Calendar cal) throws SQLException {

        preProcess();
        pstmt.setTimestamp(parameterIndex , x , cal);

    }

    public void setNull (int paramIndex, int sqlType, String typeName)
        throws SQLException {

        preProcess();
        pstmt.setNull(paramIndex , sqlType , typeName);

    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Connection getConnection()  throws SQLException {

        preProcess();
        return pstmt.getConnection();

    }

    public int[] executeBatch() throws SQLException {

        preProcess();
        return pstmt.executeBatch();

    }

    public void clearBatch() throws SQLException {

        preProcess();
        pstmt.clearBatch();

    }

    public void addBatch( String sql ) throws SQLException {

        preProcess();
        pstmt.addBatch(sql);

    }

    public ResultSet executeQuery(String sql) throws SQLException {

        preProcess();
        return pstmt.executeQuery(sql);

    }

    public int executeUpdate(String sql) throws SQLException {

        preProcess();
        return pstmt.executeUpdate(sql);

    }

    public void close() throws SQLException {

        debug.print("PreparedStatement Closed for:" + sConn.getOwner());
        if (isClosed)
            throw new SQLException("PreparedStatement already closed");
        pstmt.close();
        isClosed=true;

    }

    public boolean isClosed() throws SQLException {

        return isClosed;

    }

    public int getMaxFieldSize() throws SQLException {

        preProcess();
        return pstmt.getMaxFieldSize();

    }

    public void setMaxFieldSize(int max) throws SQLException {

        preProcess();
        pstmt.setMaxFieldSize(max);

    }

    public int getMaxRows() throws SQLException {

        preProcess();
        return pstmt.getMaxRows();

    }

    public void setMaxRows(int max) throws SQLException {

        preProcess();
        pstmt.setMaxRows(max);

    }

    public void setEscapeProcessing(boolean enable) throws SQLException {

        preProcess();
        pstmt.setEscapeProcessing(enable);

    }

    public int getQueryTimeout() throws SQLException {

        preProcess();
        return pstmt.getQueryTimeout();

    }

    public  void setQueryTimeout(int seconds) throws SQLException {

        preProcess();
        pstmt.setQueryTimeout(seconds);

    }

    public void cancel() throws SQLException {

        preProcess();
        pstmt.cancel();

    }

    public SQLWarning getWarnings() throws SQLException {

        preProcess();
        return pstmt.getWarnings();

    }

    public void clearWarnings() throws SQLException {

        preProcess();
        pstmt.clearWarnings();

    }

    public void setCursorName(String name) throws SQLException {

        preProcess();
        pstmt.setCursorName(name);

    }

    public boolean execute(String sql) throws SQLException {

        preProcess();
        return pstmt.execute(sql);

    }

    public ResultSet getResultSet() throws SQLException {

        preProcess();
        return pstmt.getResultSet();

    }

    public int getUpdateCount() throws SQLException {

        preProcess();
        return pstmt.getUpdateCount();


    }

    public boolean getMoreResults() throws SQLException {

        preProcess();
        return pstmt.getMoreResults();

    }

    public void setFetchDirection(int direction) throws SQLException {

        preProcess();
        pstmt.setFetchDirection(direction);

    }

    public int getFetchDirection() throws SQLException {

        preProcess();
        return pstmt.getFetchDirection();

    }

    public void setFetchSize(int rows) throws SQLException {

        preProcess();
        pstmt.setFetchSize(rows);

    }

    public  int getFetchSize() throws SQLException {

        preProcess();
        return pstmt.getFetchSize();

    }

    public int getResultSetConcurrency() throws SQLException {

        preProcess();
        return pstmt.getResultSetConcurrency();

    }

    public int getResultSetType()  throws SQLException {

        preProcess();
        return pstmt.getResultSetType();

    }

    public String toString() {

        return pstmt.toString();

    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
