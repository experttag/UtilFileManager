/*
 * @(#) SmartCallableStatement 1.0 02/08/01
 */

package com.experttag.util.pool.core;


import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.*;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * This  class encapsulates  a CallableStatement.
 * Dont expect me to document this class. If you want refer Sun's Documentation.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */


public class SmartCallableStatement extends SmartPreparedStatement
            implements Close , CallableStatement {

    private CallableStatement cstmt;
    private SmartConnection sConn;
    private boolean isClosed = false;
    Debugger debug =
                new Debugger("org.smartlib.pool.core.SmartCallableStatement", true);

    SmartCallableStatement(CallableStatement cstmt
            , SmartConnection sConn) {

        super(cstmt,sConn);
        this.cstmt = cstmt;
        this.sConn = sConn;

    }

    private void preProcess() throws SQLException {

        if (isClosed())
            throw new SQLException("Prepared Statement already closed");
        sConn.setLastAccessedTime();

    }

    public void registerOutParameter(int p0, int p1) throws SQLException {

        preProcess();
        cstmt.registerOutParameter(p0,p1);

    }

    public void registerOutParameter(int p0, int p1, int p2)
            throws SQLException {

        preProcess();
        cstmt.registerOutParameter(p0,p1,p2);

    }

    public boolean wasNull() throws SQLException {

        preProcess();
        return cstmt.wasNull();

    }

    public String getString(int p0) throws SQLException {

        preProcess();
        return cstmt.getString(p0);

    }

    public boolean getBoolean(int p0) throws SQLException {

        preProcess();
        return cstmt.getBoolean(p0);

    }

    public byte getByte(int p0) throws SQLException {

        preProcess();
        return cstmt.getByte(p0);

    }

    public short getShort(int p0) throws SQLException {

        preProcess();
        return cstmt.getShort(p0);

    }

    public int getInt(int p0) throws SQLException {

        preProcess();
        return cstmt.getInt(p0);

    }

    public long getLong(int p0) throws SQLException {

        preProcess();
        return cstmt.getLong(p0);

    }

    public float getFloat(int p0) throws SQLException {

        preProcess();
        return cstmt.getFloat(p0);

    }

    public double getDouble(int p0) throws SQLException {

        preProcess();
        return getDouble(p0);

    }

    public BigDecimal getBigDecimal(int p0, int p1) throws SQLException {

        preProcess();
        return getBigDecimal(p0,p1);

    }

    public byte[] getBytes(int p0) throws SQLException {

        preProcess();
        return getBytes(p0);

    }

    public java.sql.Date getDate(int p0) throws SQLException {

        preProcess();
        return cstmt.getDate(p0);

    }

    public Time getTime(int p0) throws SQLException {

        preProcess();
        return cstmt.getTime(p0);

    }

    public Timestamp getTimestamp(int p0) throws SQLException {

        preProcess();
        return cstmt.getTimestamp(p0);

    }

    public Object getObject(int p0) throws SQLException {

        preProcess();
        return cstmt.getObject(p0);

    }

    public BigDecimal getBigDecimal(int p0) throws SQLException {

        preProcess();
        return cstmt.getBigDecimal(p0);

    }

    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public Ref getRef(int p0) throws SQLException {

        preProcess();
        return cstmt.getRef(p0);

    }

    public Blob getBlob(int p0) throws SQLException {

        preProcess();
        return cstmt.getBlob(p0);

    }

    public Clob getClob(int p0) throws SQLException {

        preProcess();
        return cstmt.getClob(p0);

    }

    public Array getArray(int p0) throws SQLException {

        preProcess();
        return cstmt.getArray(p0);

    }

    public java.sql.Date getDate(int p0, Calendar p1) throws SQLException {

        preProcess();
        return cstmt.getDate(p0,p1);

    }

    public Time getTime(int p0, Calendar p1) throws SQLException {

        preProcess();
        return cstmt.getTime(p0,p1);

    }

    public Timestamp getTimestamp(int p0, Calendar p1) throws SQLException {

        preProcess();
        return cstmt.getTimestamp(p0,p1);

    }

    public void registerOutParameter(int p0, int p1, String p2)
        throws SQLException {

        preProcess();
        cstmt.registerOutParameter(p0,p1,p2);

    }

    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setURL(String parameterName, URL val) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(String parameterName, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBoolean(String parameterName, boolean x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setByte(String parameterName, byte x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setShort(String parameterName, short x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setInt(String parameterName, int x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLong(String parameterName, long x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFloat(String parameterName, float x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDouble(String parameterName, double x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setString(String parameterName, String x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBytes(String parameterName, byte x[]) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(String parameterName, Date x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(String parameterName, Time x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getBoolean(String parameterName) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte getByte(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getShort(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getInt(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getLong(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float getFloat(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getDouble(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getBytes(String parameterName) throws SQLException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Ref getRef(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob getBlob(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob getClob(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array getArray(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean  isClosed() {

        return isClosed;

    }

    public String toString() {

        return cstmt.toString();

    }

    public void close() throws SQLException {

        if (isClosed)
            throw new SQLException("CallableStatement already closed");
        cstmt.close();
        debug.print("CallableStatement Closed for:" + sConn.getOwner());
        isClosed=true;

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

    public RowId getRowId(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowId getRowId(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNString(String parameterName, String value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NClob getNClob(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getNString(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getNString(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
