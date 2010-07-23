package com.experttag.util.pool.core;

/**
 * Created by IntelliJ IDEA.
 * User: kerneldebugger
 * Date: Oct 1, 2005
 * Time: 10:34:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MultiPoolMonitor  {

    /**
     * This method returns the pool monitors that this MultiPool is associated with
     */
    public PoolMonitor[] getPoolMonitors();

    /**
     * This method returns the name of the pool
     */
    public String getName();

    

}
