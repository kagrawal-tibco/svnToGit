
package com.tibco.cep.mapper.xml.xdata;

/**
 * This class should really be part of the engine.
 */
public class ValidationUtils {
    /**
     * Returns true if we are using the old validator, false otherwise.
     */
    public static boolean isUsingTheOldValidator() {
        String value = System.getProperty(USE_OLD_VALIDATOR, DEFAULT_USE_OLD_VALIDATOR);
        boolean retValue = toBoolean(value);

        return retValue;
    }

    private static boolean toBoolean(String name) {
        return ((name != null) && name.equalsIgnoreCase("true"));
    }

    // we use the old validator, by default
    private static final String DEFAULT_USE_OLD_VALIDATOR = "false";
    // the name of the system property
    private static final String USE_OLD_VALIDATOR = "com.tibco.bw.use.old.validator";
}
