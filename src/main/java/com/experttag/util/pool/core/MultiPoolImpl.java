package com.experttag.util.pool.core;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: kerneldebugger
 * Date: Oct 1, 2005
 * Time: 11:42:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultiPoolImpl implements MultiPool, MultiPoolMonitor {

    private static Logger logger = Logger.getLogger(MultiPoolImpl.class);

    private Map poolHash = new HashMap();
    private PoolConfig config;
    private String name;
    private String[] poolNames;
    private int lastPoolServed = -1;

    // Thread Local Object to store the context information
    private static ThreadLocal threadLocal = new ThreadLocal();

    MultiPoolImpl(PoolConfig config) throws ConnectionPoolException {
        this.config = config;
        this.name = config.getMultiPoolName();
        initialize();
    }

    /**
     * This method loads all the pools
     * @throws ConnectionPoolException
     */
    private void initialize() throws ConnectionPoolException {
        logger.debug("Loading Multi Pool: " + name);
        if (config.isExternalPooling()) {
            if (logger.isDebugEnabled()) {
                logger.debug("External pooling is switched ON");
            }
            PoolConfig.ConnectionLoaderClass[] connectLoaderClasses = config.getConnectionLoaderClass();

            if (connectLoaderClasses != null && connectLoaderClasses.length !=0) {
                for (int i=0; i<connectLoaderClasses.length; i++) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Loading Connection pool: " + connectLoaderClasses[i].getName());
                    }
                    Pool pool = new ConnectionPool(config, connectLoaderClasses[i].getName());
                    poolHash.put(connectLoaderClasses[i].getName(), pool);
                }
            }
        }
        else {
            PoolConfig.ConnectionString[] connectionStrings = config.getConnectionString();
            logger.debug("External pooling is switched OFF");
            for (int i=0; i<connectionStrings.length; i++) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Loading Connection pool: " + connectionStrings[i].getName());
                }
                Pool pool = new ConnectionPool(config, connectionStrings[i].getName());
                poolHash.put(connectionStrings[i].getName(), pool);
            }
        }
        Set set  = poolHash.keySet();
        poolNames = (String[])set.toArray(new String[set.size()]);

    }

    public Connection getConnection() throws ConnectionPoolException {
        return getPool().getConnection();
    }

    public Connection getConnection(String owner) throws ConnectionPoolException {
        return getPool().getConnection(owner);
    }

    public void addConnectionLeakListener(ConnectionLeakListener cle) throws ConnectionPoolException {
        for (int i=0; i<poolNames.length; i++) {
            Pool pool = (Pool)poolHash.get(poolNames[i]);
            pool.addConnectionLeakListener(cle);
        }
    }

    public void removeConnectionLeakListener(ConnectionLeakListener cle) throws ConnectionPoolException {
         for (int i=0; i<poolNames.length; i++) {
            Pool pool = (Pool)poolHash.get(poolNames[i]);
            pool.removeConnectionLeakListener(cle);
        }
    }

    public void shutDown() {
        for (int i=0; i<poolNames.length; i++) {
            Pool pool = (Pool)poolHash.get(poolNames[i]);
            pool.shutDown();
        }
    }

    public PoolMonitor[] getPoolMonitors() {
        PoolMonitor[] monitors = new PoolMonitor[poolNames.length];
        for (int i=0; i<poolNames.length; i++) {
            Pool pool = (Pool)poolHash.get(poolNames[i]);
            monitors[i] = (PoolMonitor)pool;
        }
        return monitors;
    }

    public String getName() {
        return name;
    }

    /**
     * This method checks if there is a thread context associated, if yes, it reuses the same pool, else picks a new pool
     */
    private Pool getPool() {
        if (config.isThreadStickiness()) {
            if (logger.isDebugEnabled()){
                logger.debug("Thread stickiness is configured");
            }
            ThreadContext context = (ThreadContext)threadLocal.get();
            if (context != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Thread is already associated with pool: " + context.getPoolName());
                }
                return (Pool)poolHash.get(context.getPoolName());
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Thread is not associated with a pool");
                }
                Pool p = getRandomPool();
                context = new ThreadContext();
                context.setPoolName(((PoolMonitor)p).getName());
                threadLocal.set(context);
                if (logger.isDebugEnabled()) {
                    logger.debug("Thread now associated with a pool:" + ((PoolMonitor)p).getName());
                }
                return p;
            }

        }
        else {
            if (logger.isDebugEnabled()){
                logger.debug("Thread stickiness is not configured");
            }
            return getRandomPool();
        }
    }

    private Pool getRandomPool() {
//        Double number = Math.random();
//        int i = ((int)((Math.abs(number)*100)%(poolNames.length)));
        if (lastPoolServed == poolNames.length - 1)
            lastPoolServed = 0;
        else
            lastPoolServed++;
        Pool p =  (Pool)poolHash.get(poolNames[lastPoolServed]);
        if (logger.isDebugEnabled()) {
            logger.debug("Randomely Returning pool:" + ((PoolMonitor)p).getName());
        }
        return p;
    }

    public static void main (String args[]) {
        Double number = Math.random();
        int i = ((int)((Math.abs(number)*100)%(2)));
        System.out.println("The number is: " + i);
    }


}
