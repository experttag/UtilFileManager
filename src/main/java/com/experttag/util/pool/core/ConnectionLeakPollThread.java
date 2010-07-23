/*
 * @(#) ConnectionLeakPollThread.java 1.0 02/08/01
 */

package com.experttag.util.pool.core;


import java.util.*;

/**
 * The responsibility of this class is to poll the pools, detect leaks 
 * and release excessive free connections if required.
 *
 * Note: I know this class can be optimised, will do it later.
 *
 * @author	Sachin Shekar Shetty
 * @version 1.0, 02/08/01
 */


class ConnectionLeakPollThread implements Runnable {

    private Vector connectionsInUse ;
    private Vector connectionListenerList;
    private String poolName;
    private long sleepTime;
    private boolean keepGoing ;
    private long leakTimeOut ;
    private static Debugger debug;
    private Pool pool;

    /**
     * @param connectionsInUse Vector containing the connections that
     *							are in use.
     * @param connectionListenerList Vector of registered listenetrs.
     * @param poolName Name of the pool.
     * @param sleepTime Sleep time for the poll thread.
     * @param leakTimeOut time interval after which a leak is said to
     * have occurred.
     * @param pool A reference to Pool implementation.
     *
     */
    ConnectionLeakPollThread(Vector connectionsInUse
                , Vector connectionListenerList , String poolName
                , long sleepTime , long leakTimeOut , Pool pool) {

        this.connectionsInUse = connectionsInUse;
        this.connectionListenerList = connectionListenerList;
        this.poolName = poolName;
        this.sleepTime = sleepTime;
        this.leakTimeOut = leakTimeOut;
        keepGoing = true;
        this.pool = pool;
        debug = new Debugger("ConnectionLeakPollThread-" + poolName ,
                             true );

    }

    /**
     * This methods notifies the registered listeners.
     * The logic seem to contain redundent iterations, will optimize
     * it later.
     * @param  sConn Connection for which to notify the listener.
     */

    private void notifyAll(SmartConnection sConn) {

        for (int i=0 ; i<connectionListenerList.size() ; i++ ) {
            //debug.print("Found Leak notifying");
            ConnectionLeakEvent cle = new ConnectionLeakEventImpl (
                    sConn , sConn.getOwner() , sConn.getLastAccessedTime() ,
                    sConn.getConnectionObtainedTime() , poolName);
            //debug.print("Found Leak notifying" + cle);
            ConnectionLeakListener c =
                (ConnectionLeakListener)connectionListenerList.get(i);
            c.connectionTimeOut(cle);
        }

    }

    // invokes the pool to release excessive connections
    private void checkAndRelease() {

        pool.releaseConnections();

    }

    // checks and notifies the listeners
    private void checkAndNotify() {

        for (int i=0 ; i<connectionsInUse.size() ; i++ ) {
            SmartConnection sConn = (SmartConnection)connectionsInUse.get(i);
            // notify all the listeners
            if (System.currentTimeMillis() - sConn.getConnectionObtainedTime()
                         >= leakTimeOut)
                    notifyAll(sConn);
            // checks and accordingly returns connections back to the
            // pool, one which have been idle for more than specified
            // time            
            if (System.currentTimeMillis() - sConn.getLastAccessedTime() > ((PoolMonitor)pool).getConfigMonitor().getMaxConnectionIdleTime()) {
                debug.print("Found a Idle Connection ...");
                sConn.forcedClose();
            }
        }

    }

    // Poor method, once starts running never stops untill killed,
    // will implement stoping of polling thread at runtime in later versions.
    public void run() {
        debug.print("Starting Thread for detecting connection leaks");
        while (keepGoing) {
            debug.print("Polling .............");
            checkAndNotify();
            checkAndRelease();
            try {
                Thread.sleep(sleepTime);
            }
            catch (Exception e) {
            }
        }

    }

    // To stop the thread, FORWARD COMPATIBILITY YOU SEE.
    private void stop() {

        keepGoing = false;

    }

}
