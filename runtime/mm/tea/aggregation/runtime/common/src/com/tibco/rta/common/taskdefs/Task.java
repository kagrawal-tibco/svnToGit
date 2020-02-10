package com.tibco.rta.common.taskdefs;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/6/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Task {

    /**
     * @return
     * @throws Exception
     */
    public Object perform() throws Throwable;

    /**
     * @return
     */
    public String getTaskName();
}
