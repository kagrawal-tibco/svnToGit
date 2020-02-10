package com.tibco.rta.log;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/6/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LogConfiguratorMBean {

    /**
     * Return all logger categories in this log manager.
     * @see com.tibco.rta.log.impl.LoggerCategory
     * @return
     */
    public String[] getLoggers();

    /**
     * Get log level for category.
     * @return
     */
    public String getLogLevel(String category);

    /**
     * Set log level for this category.
     * @param category
     * @param level
     */
    public void setLogLevel(String category, String level);
}
