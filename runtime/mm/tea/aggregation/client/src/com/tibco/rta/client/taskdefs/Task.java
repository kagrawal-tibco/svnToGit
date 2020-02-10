package com.tibco.rta.client.taskdefs;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Task {

    /**
     *
     * @return
     * @throws Exception
     */
    public Object perform() throws Throwable;

    /**
     *
     * @return
     */
    public String getTaskName();
}
