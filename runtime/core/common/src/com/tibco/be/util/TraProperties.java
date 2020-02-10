package com.tibco.be.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/*
* Copyright 2003 TIBCO Software Inc.
* All Rights Reserved.
*
* This software is the confidential and proprietary information of
* TIBCO Software Inc.
*
* $Id$
*
* Author: wweng
* Date  : Jan 27, 2004
*
* Change History:
*
*/
/**
 * Class for parse properties in tra file. <br>
 * On top of the special behavior of {@link TProperties}, this class also does variable expansion in value. e.g.:  <br>
 * tibco.env.TIB_HOME=C:/tibco <br>
 * tibco.env.BW_HOME=%TIB_HOME%/bw <br>
 * will expend to  <br>
 * tibco.env.BW_HOME=C:/tibco/bw <br>
 */
public class TraProperties extends TProperties{
    static Logger logger = Logger.getLogger(TraProperties.class);

    /**
     * A few predefined key in tra file that used by t-tests.
     */
    public static final String CLASS_PATH_EXTENDED= "tibco.class.path.extended";
    public static final String JAVA_PROPERTY_PALETTEPATH= "java.property.palettePath";
    public static final String APP_ARGS = "application.args";

    public static final String STANDARD_TIBCO_CP = "tibco.env.STD_EXT_CP";
    public static final String JAVA_START_CLASS = "java.start.class";
    public static final String JAVA_EXTENDED_PROPERTIES = "java.extended.properties";

    public TraProperties(TProperties properties){
        this.defaults = properties;
    }

    public TraProperties(){
        super();
    }
    public String resolveSubsitution(String value){

        StringBuffer newValue = new StringBuffer();
        Pattern tibEnv = Pattern.compile("%\\w+%");
        Matcher matcher = tibEnv.matcher(value);

        while (matcher.find()){
            String var = matcher.group();

            // first check if var can be subsituted
            String varValue = getSubstitutedVar(var);

            // if not check system properties
            if (varValue == null ){
                varValue = getSystemEnv(var);
            }
            if (varValue != null) {
                matcher.appendReplacement(newValue, varValue);
            }

            //System.out.println("var=" + var);
            //System.out.println("varValue=" + var);
            //System.out.println("newValue=" + newValue);
        }
        matcher.appendTail(newValue);
        return newValue.toString();

    }

    private String getSubstitutedVar(String var) {
        String key = var.replaceAll("%", "");
        key = TIB_ENV_PREFIX + key;
        return getProperty(key);
    }

    private String getSystemEnv(String var) {
        String key = var.replaceAll("%", "");
        return System.getProperty(key);
    }

    public synchronized Object put(Object keyObj, Object valueObj) {
        // Make sure the value is not null
        if (valueObj == null) {
            throw new NullPointerException();
        }
        String key = keyObj.toString();
        String value = valueObj.toString();
        if (value.indexOf('%') >= 0 ) {
            value = resolveSubsitution(value);
        }
        return super.put(key, value);
    }

//    public static void main(String[] args) {
//        TraProperties tProperties = new TraProperties();
//        String traFileName = "C:/tmp/bwengine.tra";
//                try {
//            tProperties.load(new FileInputStream(traFileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String value = tProperties.getProperty(CLASS_PATH_EXTENDED);
//        System.out.println(CLASS_PATH_EXTENDED + "=" + value);
//    }
}
