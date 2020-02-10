package com.tibco.cep.bpmn.runtime.utils;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

/*
* Author: Ashwin Jayaprakash / Date: 6/14/12 / Time: 11:07 AM
*/
public abstract class Constants {
    public static final boolean DEBUG = false;
    

    static {
        if (DEBUG) {
            LogManagerFactory.getLogManager().getLogger(Constants.class).log(Level.WARN, "\n" +
                    "+=================================================================+\n" +
                    "|       Warning: Bpmn module is running in 'full debug' mode      |\n" +
                    "+=================================================================+\n"
            );
        }
    }

    private Constants() {
    }
}
