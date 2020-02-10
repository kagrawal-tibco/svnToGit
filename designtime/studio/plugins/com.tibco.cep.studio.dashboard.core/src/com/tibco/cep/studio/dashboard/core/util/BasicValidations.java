package com.tibco.cep.studio.dashboard.core.util;


/**
 * @author apatil
 *
 */
public final class BasicValidations {

	public final static String REG_EX_PATTERN_IDENTIFIER = "^[a-zA-Z0-9_]*(([\\.]?[_]*[a-zA-Z0-9])+)*";

    public final static String REG_EX_PATTERN_NAME = REG_EX_PATTERN_IDENTIFIER;

    public final static String REG_EX_PATTERN_JAVA_IDENTIFIER = REG_EX_PATTERN_IDENTIFIER;

    public final static String REG_EX_PATTERN_COLOR = "([A-Fa-f0-9]){6}";

    public final static int MAX_NAME_LENGTH = 128;

    public final static int MAX_STRNG_LENGTH = Integer.MAX_VALUE;

    public final static long DEAULT_DATASIZE = 64;

    public static String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss.S";

    public static String getNewRandomID() {

        return (Math.random() * Integer.MAX_VALUE) + "";
    }

    /**
     * @param string
     * @return
     */
    public final static boolean isValidInteger(String string) {

        try {
            new Integer(string);
        } catch (NumberFormatException numFormatEx) {
            return false;
        }
        return true;
    }

    /**
     * @param string
     * @return
     */
    public final static boolean isValidLong(String string) {

        try {
            new Long(string);
        } catch (NumberFormatException numFormatEx) {
            return false;
        }
        return true;
    }

}