/*
 * @(#) Close 1.0 02/08/01
 */

package com.experttag.util.pool.core;

import java.sql.SQLException;


/**
 * This interface is implemented by SmartStatement, SmartPreparedStatement
 * and SmartCallableStatement. This allows the SmartStament, 
 * SmartPreparedStatement, and SmartCallableStatement to be closed through 
 * this generic interface when auto-close is enabled.
 * 
 *
 * @author	Sachin Shekar Shetty
 * @version 1.0, 02/08/01
 *
 */

public interface Close {

	/**
	 * This method closes the Object. Any further invoking of this object would
	 * result in an exception.
	 *
	 * @exception SQLException
	 */
	public void close() throws SQLException;

	/**
	 * This method returns true if the Object is already closed
	 * 
	 * @return true if the Object is already closed
	 *
	 * @exception SQLException
	 */
	public boolean isClosed() throws SQLException;

}
