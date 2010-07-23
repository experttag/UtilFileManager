package com.experttag.util.pool.core;

/**
 * This class will store the information related to the thread context
 */
public class ThreadContext {

    private String poolName;
    private long createdTimeStamp;

    public ThreadContext() {
        createdTimeStamp = System.currentTimeMillis();
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

}
