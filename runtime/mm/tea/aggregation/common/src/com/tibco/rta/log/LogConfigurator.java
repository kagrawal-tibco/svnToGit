package com.tibco.rta.log;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/6/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogConfigurator implements LogConfiguratorMBean {

    @Override
    public String[] getLoggers() {
        List<String> loggers = LogManagerFactory.getLogManager().getLoggers();
        return loggers.toArray(new String[loggers.size()]);
    }

    @Override
    public String getLogLevel(String category) {
        Logger logger = LogManagerFactory.getLogManager().getLogger(category);
        return logger.getLevel().name;
    }

    @Override
    public void setLogLevel(String category, String level) {
        if (!level.isEmpty()) {
            if (category == null || category.isEmpty()) {
                category = "root";
            }
            Logger logger = LogManagerFactory.getLogManager().getLogger(category);
            logger.setLevel(Level.valueOf(level));
        }
    }
}
