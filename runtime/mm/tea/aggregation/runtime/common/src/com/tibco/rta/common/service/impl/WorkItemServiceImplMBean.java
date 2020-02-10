package com.tibco.rta.common.service.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 31/5/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WorkItemServiceImplMBean {

    /**
     * Return active number of threads in the pool.
     *
     * @return number of threads.
     */
    public int getLiveThreadCount();

    /**
     * Get current queue size of work items queue used by thread pool.
     *
     * @return queue size.
     */
    public int getCurrentQueueSize();

    /**
     * Get number of tasks completed at a certain point in time.
     *
     * @return number of completed tasks.
     */
    public int getCompletedTasks();
        
    /**
     * Get number of min. thread count in threadpool
     * 
     * @return min number of thread count in threadpool
     */
    public int getMinThreads();
    

    /**
     * Get number of max. thread count in threadpool
     * 
     * @return max thread count in threadpool
     */
    public int getMaxThreads();
}
