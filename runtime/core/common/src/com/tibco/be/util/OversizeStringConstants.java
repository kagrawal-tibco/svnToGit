package com.tibco.be.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 17, 2005
 * Time: 5:55:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class OversizeStringConstants {
    public static final String PROPERTY_FILE_NAME = "OversizeStringConstants.OversizeStringConstants";
    public static final String JAVA_FILE_NAME = "OversizeStringConstants.java";
    public static final String JAVA_FILE_BODY = "package be.gen;\nimport java.util.Properties;\npublic class OversizeStringConstants {\n    private static Properties props = null;\n    public static void init(Properties constants, boolean reset) {\n        if(reset) {\n            props = constants;\n        } else {\n            if(props == null) props = new Properties();\n            props.putAll(constants);\n        }\n        \n    }\n     public static String getConstant(String key) {\n        if(props == null) return null;\n        return props.getProperty(key);\n    }\n    public static void setConstant(String key, String value) {\n        if(props == null) props = new Properties();\n        props.setProperty(key, value);\n    }\n}";

    public static void init(Properties props, ClassLoader loader) {
        if(props == null) return;
        Class oscClazz = null;
        try {
            oscClazz = loader.loadClass(ModelNameUtil.GENERATED_PACKAGE_PREFIX + '.' + "OversizeStringConstants");
        } catch (ClassNotFoundException cnfe) {
            assert false;
            return;
        }
        
        Method initMethod = null;
        try {
            initMethod = oscClazz.getMethod("init", new Class[]{Properties.class,  boolean.class});
        } catch (NoSuchMethodException nsme) {
            assert false;
            return;
        }
        
        try{
            initMethod.invoke(null, new Object[]{props, new Boolean(true)});
        } catch (IllegalAccessException iae) {
            assert false;
            return;
        } catch (InvocationTargetException ite) {
            assert false;
            return;
        }
    }

}