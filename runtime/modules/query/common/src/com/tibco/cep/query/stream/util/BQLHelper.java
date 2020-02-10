package com.tibco.cep.query.stream.util;

import java.util.Properties;

import com.tibco.cep.query.service.QueryProperty;

/*
* Author: Karthikeyan Subramanian / Date: Apr 21, 2010 / Time: 5:08:42 PM
*/
public class BQLHelper {

    public static boolean isBQLConsoleEnabled(Properties properties, String agentName) {
        String showConsole = properties.getProperty(QueryProperty.SHOW_BQL_CONSOLE.getPropName());
        if(showConsole == null || showConsole.trim().length() == 0) {
            return false;
        }
        String[] consoleNames = showConsole.split(",");
        for (String consoleName : consoleNames) {
            if(consoleName.equals(agentName)) {
                return true;
            }
        }
        return false;
    }
}
