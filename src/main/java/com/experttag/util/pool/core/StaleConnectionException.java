/*
 * @(#) TimeOutException.java 1.0 02/08/01
 */
package com.experttag.util.pool.core;


/**
 * When a consumer is kept waiting for more than time specified in
 * connection-wait-time-out in the configuration file, a
 * TimeOutException is thrown. This class extends
 * ConnectionPoolException.
 * 
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 */

public class StaleConnectionException extends ConnectionPoolException {
    
  public StaleConnectionException() {
      
      super();            

  }

  public StaleConnectionException(String msg) {
      
      super(msg);

  }

  public String toString()  {

    return ("StaleConnectionException: " + message);
      
  }
      
}
