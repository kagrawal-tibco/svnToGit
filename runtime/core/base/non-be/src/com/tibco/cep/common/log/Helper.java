package com.tibco.cep.common.log;

import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Dec 17, 2009 / Time: 3:45:40 PM
*/

public class Helper {
    /**
     * {@value}.
     */
    public static final String ROOT_LOGGER_NAME = "";

    /**
     * * @param clazz
     *
     * @return Returns the value specified by the {@link LogCategory} annotation on the given class
     *         or its package. Otherwise the class' FQN.
     */
    public static String $logCategory(Class clazz) {
        LogCategory logCategory = (LogCategory) clazz.getAnnotation(LogCategory.class);

        if (logCategory == null) {
            Package pkg = clazz.getPackage();
            if (pkg != null) {
                logCategory = pkg.getAnnotation(LogCategory.class);
            }
        }

        return (logCategory == null) ? clazz.getName() : logCategory.value();
    }
}
