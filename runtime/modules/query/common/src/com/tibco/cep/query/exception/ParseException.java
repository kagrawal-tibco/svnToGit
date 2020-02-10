package com.tibco.cep.query.exception;

import java.util.Locale;

import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jun 12, 2007
 * Time: 11:50:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends Exception {
    public static final String MSG_PROPERTIES = "com.tibco.cep.query.messages";
    private static String typekey="parse.exception";
    static ResourceManager rm = ResourceManager.getInstance();
    static {
        rm.addResourceBundle(MSG_PROPERTIES, Locale.getDefault());
    }

    public ParseException(String msg,int line ,int  column) {
       super(rm.formatMessage(ParseException.typekey, new Object[]{line,column,msg}));
    }

}
